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
    private final Map<Integer,MaybeProduction> candidatesLocks = new HashMap<>();
    private final Map<Integer,Status> candidatesStates = new HashMap<>();
    private final Map<Integer,Integer> candidatesPointer = new HashMap<>();
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

    private void ensureCandidate(int i){
        if (!candidatesPointer.containsKey(i)) {
            candidatesPointer.put(i, 0);
        }
        if (!candidatesStates.containsKey(i)) {
            candidatesStates.put(i, Status.PENDING);
        }
    }

    public Status getStatus(){
        return this.status;
    }
    private Status candidateState(int i){
        return this.candidatesStates.get(i);
    }

    private void setCandidateState(int i,Status status){
        this.candidatesStates.put(i,status);
        if (status == Status.CORRECT){
            setStatus(status);
        }else if (status == Status.INCORRECT){
            int subsLen = def.getParts().length;
            if (candidatesStates.values().size() == subsLen) {
                boolean incorrect = true;
                for (Status s : candidatesStates.values()) {
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
    private boolean skipCandidate(int i){
        boolean skip = false;
        if (candidatesStates.containsKey(i)){
            skip = (candidatesStates.get(i) == Status.INCORRECT);
        }
        return skip;
    }

    private boolean isCandidateLocked(int i){
        if (candidatesLocks.containsKey(i)){
            MaybeProduction lockedBy = candidatesLocks.get(i);
            if (lockedBy.getStatus() != Status.PENDING) {
                if (lockedBy.getStatus() == Status.INCORRECT) {
                    setCandidateState(i, Status.INCORRECT);
                }
                candidatesLocks.remove(i);
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
            if (!isCandidateLocked(i)) {
                if (skipCandidate(i)){
                    continue;
                }
                LangPartDef[] sub = productions[i];
                ensureCandidate(i);
                LangPartDef toComp = sub[candidatesPointer.get(i)];
                if (!LangTokenDef.class.isAssignableFrom(toComp.getClass())) {
                    LangProductionDef productionDef = (LangProductionDef) toComp;
                    if (langTokenDef.getStartsProductions().contains(productionDef)) {
                        MaybeProduction mb = analysis.getMaybeProduction(token,langTokenDef,productionDef);
                        mb.consume(token,langTokenDef);
                        candidatesLocks.put(i, mb);
                        if (sub.length - 1 == candidatesPointer.get(i)){
                            final int ii = i;
                            mb.addResultListener(new ProductionResultListener() {
                                @Override
                                public void onResult(MaybeProduction production) {
                                    setCandidateState(ii,production.getStatus());
                                }
                            });
                        }
                    }
                } else {
                    LangTokenDef tokenDef = (LangTokenDef) toComp;
                    if (tokenDef.getId().equals(langTokenDef.getId())) {
                        if (candidatesPointer.get(i) == sub.length - 1) {
                            setCandidateState(i,Status.CORRECT);
                        }
                    } else {
                        setCandidateState(i,Status.INCORRECT);
                    }
                }
                candidatesPointer.put(i,candidatesPointer.get(i)+1);
            }
        }
    }

    private void addResultListener(ProductionResultListener listener) {
        this.listeners.add(listener);
    }


}
