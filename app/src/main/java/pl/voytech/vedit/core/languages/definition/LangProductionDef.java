package pl.voytech.vedit.core.languages.definition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.TokenProduction;

/**
 * Created by USER on 2016-11-03.
 */

public class LangProductionDef extends LangPartDef<TokenProduction,TokenProduction>{

    public enum ExpansionMode{
        FULL,
        FIRST_LEVEL,
        ENSURE_STARTING_TOKEN
    }
    private final Map<String,LangPartDef> partsMap = new HashMap<>();
    private final List<LangPartDef[]> parts = new ArrayList<>();
    public Map<String, LangPartDef> getPartsMap() {
        return partsMap;
    }

    public LangPartDef[][] getParts(){
        return this.parts.toArray(new LangPartDef[][]{});
    }

    public LangProductionDef add(LangPartDef[] prod){
        for (LangPartDef def : prod) {
            this.getPartsMap().put(def.getId(), def);
        }
        this.parts.add(prod);
        return this;
    }

    public LangTokenDef[] startingTokens() {
        List<LangTokenDef> startingTokens = new ArrayList<>();
        for (LangPartDef[] subProds : parts){
            if (subProds.length>0){
                if (LangTokenDef.class.isAssignableFrom(subProds[0].getClass())){
                    startingTokens.add((LangTokenDef)subProds[0]);
                }
            }
        }
        return startingTokens.toArray(new LangTokenDef[]{});
    }


    public LangPartDef[][] expand(ExpansionMode expansionMode){
        List<List<LangPartDef>> expansion = new ArrayList<>();
        int idx = 0;
        boolean dontExpand = false;
        for (LangPartDef[] subProduction : getParts()){
            List<LangPartDef> sub = new ArrayList<>();
            for (LangPartDef lpd : subProduction) {
                if (LangProductionDef.class.isAssignableFrom(lpd.getClass())) {
                    if (!(expansionMode == ExpansionMode.FULL) || dontExpand) {
                        sub.add(lpd);
                        if (!expansion.contains(sub)){
                            expansion.add(sub);
                        }
                    } else {
                        LangProductionDef lpdg = (LangProductionDef) lpd;
                        LangPartDef[][] expanded = lpdg.expand(expansionMode);
                        for (LangPartDef[] s : expanded){
                            List<LangPartDef> copied = new ArrayList<>();
                            Collections.copy(copied,sub);
                            for (LangPartDef t :s){
                                copied.add(t);
                            }
                            if (!expansion.contains(copied)) {
                                expansion.add(copied);
                            }
                        }
                    }
                } else if (LangTokenDef.class.isAssignableFrom(lpd.getClass())) {
                    sub.add(lpd);
                    if (!expansion.contains(sub)){
                        expansion.add(sub);
                    }

                }
            }
        }
        return expansion.toArray(new LangPartDef[][]{});
    }

    @Override
    public TokenProduction instance(EditorBuffer buffer) {
        return null;
    }

}
