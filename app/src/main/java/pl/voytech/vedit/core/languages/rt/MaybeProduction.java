package pl.voytech.vedit.core.languages.rt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.languages.definition.LangPartDef;
import pl.voytech.vedit.core.languages.definition.LangProductionDef;
import pl.voytech.vedit.core.languages.definition.LangTokenDef;

/**
 * Created by USER on 2016-11-23.
 */

public class MaybeProduction {
    interface ProductionResultListener{
        void onResult(MaybeProduction production);
    }
    enum Status{
        INCORRECT,
        PENDING,
        CORRECT
    }
    private LangProductionDef def;
    private Status status = Status.PENDING;
    private int lastIdx = 0;
    private final List<UUID> consumedTokens = new ArrayList<>();
    private final PendingProductions analysis;
    private final Map<Integer,MaybeProduction> subLocks = new HashMap<>();
    private final Map<Integer,Status> subStates = new HashMap<>();
    private final Map<Integer,Integer> subIndex = new HashMap<>();
    private List<ProductionResultListener> listeners = new ArrayList<>();
    public MaybeProduction(LangProductionDef def,PendingProductions analysis){
        this.def = def;
        this.analysis = analysis;
    }
    public LangProductionDef def(){
        return def;
    }
    private void setStatus(Status status){
        if (listeners!=null){
            this.status = status;
            if (status == Status.CORRECT || status == Status.INCORRECT) {
                for (ProductionResultListener listener : listeners) {
                    listener.onResult(this);
                }
            }
        }
    }

    public Status getStatus(){
        return this.status;
    }
    private Status subStatus(int i){
        return this.subStates.get(i);
    }

    private void setSubStatus(int i,Status status){
        this.subStates.put(i,status);
        if (status == Status.CORRECT){
            setStatus(status);
        }else if (status == Status.INCORRECT){
            int subsLen = def.getParts().length;
            if (subStates.values().size() == subsLen) {
                boolean incorrect = true;
                for (Status s : subStates.values()) {
                    if (s!=Status.INCORRECT){
                        incorrect = false;
                        break;
                    }
                }
                if (incorrect){
                    setStatus(Status.INCORRECT);
                }
            }
        }

    }
    private boolean skipSub(int i){
        boolean skip = false;
        if (subStates.containsKey(i)){
            skip = (subStates.get(i) == Status.INCORRECT);
        }
        return skip;
    }

    private boolean subAnalysisLock(int i){
        if (subLocks.containsKey(i)){
            MaybeProduction lockedBy = subLocks.get(i);
            if (lockedBy.getStatus() != Status.PENDING) {
                if (lockedBy.getStatus() == Status.INCORRECT) {
                    setSubStatus(i, Status.INCORRECT);
                }
                subLocks.remove(i);
                return false;
            } else {
                return true;
            }
        }else {
            return false;
        }
    }


    public void consume(Token token, LangTokenDef langTokenDef){
        if (!consumedTokens.contains(token.getId())){
            consumedTokens.add(token.getId());
        }else {
            return;
        }
        LangPartDef[][] productions = def.getParts();
        for (int i=0;i<productions.length;i++){
            if (!subAnalysisLock(i)) {
                if (skipSub(i)){
                    continue;
                }
                LangPartDef[] sub = productions[i];
                LangPartDef toComp = sub[subIndex.get(i)];
                if (!LangTokenDef.class.isAssignableFrom(toComp.getClass())) {
                    LangProductionDef productionDef = (LangProductionDef) toComp;
                    if (langTokenDef.getStartsProductions().contains(productionDef)) {
                        MaybeProduction mb = analysis.getMaybeProduction(token,langTokenDef,productionDef);
                        mb.consume(token,langTokenDef);
                        subLocks.put(i, mb);
                        if (sub.length - 1 == subIndex.get(i)){
                            final int ii = i;
                            mb.addResultListener(new ProductionResultListener() {
                                @Override
                                public void onResult(MaybeProduction production) {
                                    setSubStatus(ii,production.getStatus());
                                }
                            });
                        }
                    }
                } else {
                    LangTokenDef tokenDef = (LangTokenDef) toComp;
                    if (tokenDef.getId().equals(langTokenDef.getId())) {
                        if (subIndex.get(i) == sub.length - 1) {
                            setSubStatus(i,Status.CORRECT);
                        }
                    } else {
                        setSubStatus(i,Status.INCORRECT);
                    }
                }
                subIndex.put(i,subIndex.get(i)+1);
            }
        }
    }

    private void addResultListener(ProductionResultListener listener) {
        this.listeners.add(listener);
    }


}
