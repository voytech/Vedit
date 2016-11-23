package pl.voytech.vedit.core.languages.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.TokenProduction;

/**
 * Created by USER on 2016-11-03.
 */

public class LangProductionDef extends LangPartDef<TokenProduction,TokenProduction>{
    private final Map<String,LangPartDef> partsMap = new HashMap<>();
    private final List<LangPartDef[]> parts = new ArrayList<>();
    public Map<String, LangPartDef> getPartsMap() {
        return partsMap;
    }

    public LangPartDef[] getParts(){
        return this.parts.toArray(new LangPartDef[]{});
    }

    public LangProductionDef add(LangPartDef[] prod){
        for (LangPartDef def : prod) {
            this.getPartsMap().put(def.getId(), def);
        }
        this.parts.add(prod);
        return this;
    }

    @Override
    public TokenProduction instance(EditorBuffer buffer) {
        return null;
    }

}
