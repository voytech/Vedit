package pl.voytech.vedit.core.languages.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2016-11-08.
 */

public class LanguageFeatures {
    private final Map<LangPartDef,List<String>> languageFeatureBindings = new HashMap<LangPartDef,List<String>>();

    public void add(LangPartDef langPart,String feature){
        if (!languageFeatureBindings.containsKey(langPart)){
            languageFeatureBindings.put(langPart,new ArrayList<String>());
        }
        languageFeatureBindings.get(langPart).add(feature);
    }
    public List<String> getFeaturesByLangPart(LangPartDef def){
        return this.languageFeatureBindings.get(def);
    }
}
