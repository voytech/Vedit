package pl.voytech.vedit.core.languages.definition;

import java.util.HashMap;
import java.util.Map;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.TokenProduction;

/**
 * Created by USER on 2016-11-03.
 */

public class LangProductionDef extends LangPartDef<TokenProduction,TokenProduction>{
    private Map<String,LangPartDef> parts = new HashMap<>();

    public Map<String, LangPartDef> getParts() {
        return parts;
    }

    public LangProductionDef add(LangPartDef def){
        this.getParts().put(def.getId(),def);
        return this;
    }

    @Override
    public TokenProduction instance(EditorBuffer buffer) {
        return null;
    }


}
