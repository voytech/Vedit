package pl.voytech.vedit.core.languages.rt;

import java.util.ArrayList;
import java.util.List;

import pl.voytech.vedit.core.languages.definition.LangPartDef;
import pl.voytech.vedit.core.languages.definition.LangProductionDef;
import pl.voytech.vedit.core.languages.definition.LangTokenDef;

/**
 * Created by USER on 2016-11-23.
 */

public class MaybeProduction {
    enum Status{
        CANCELED,
        PENDING,
        CORRECT
    }
    private LangProductionDef def;
    private Status status = Status.PENDING;
    private int lastIdx = 0;
    public MaybeProduction(LangProductionDef def){
        this.def = def;
    }

    public List<LangPartDef> expand(LangProductionDef productionDef,boolean toStartingToken){
        List<LangPartDef> expansion = new ArrayList<>();
        int idx = 0;
        boolean dontExpand = false;
        for (LangPartDef lpd : productionDef.getParts()){
            if (LangProductionDef.class.isAssignableFrom(lpd.getClass())){
                if (dontExpand){
                    expansion.add(lpd);
                }else {
                    expansion.addAll(expand((LangProductionDef) lpd, toStartingToken));
                }
            }else if (LangTokenDef.class.isAssignableFrom(lpd.getClass())){
                expansion.add(lpd);
                if (idx == 0 && toStartingToken){
                    dontExpand = true;
                }
            }
            idx++;
        }
        return expansion;
    }

    public boolean progress(LangTokenDef langTokenDef){
        boolean passed = false;
        LangPartDef toComp = def.getParts()[lastIdx];
        if (!LangTokenDef.class.isAssignableFrom(toComp.getClass())){
            expand((LangProductionDef)toComp,true);
        }else {
            LangTokenDef tokenDef = (LangTokenDef)toComp;
            if (tokenDef.getId().equals(langTokenDef.getId())){
                lastIdx++;
                if (lastIdx == def.getParts().length){
                    status = Status.CORRECT;
                }
                passed = true;
            }
        }
        return passed;
    }

}
