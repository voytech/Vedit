package pl.voytech.vedit.core.languages.definition;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 2016-11-08.
 */

public class LanguageFeaturesProvider {
    private static LanguageFeaturesProvider instance;
    private final Map<String,LanguageFeatures> featuresByLang = new HashMap<String,LanguageFeatures>();
    private LanguageFeaturesProvider(){}
    public static LanguageFeaturesProvider i(){
        if (instance == null){
            instance = new LanguageFeaturesProvider();
        }
        return instance;
    }
    public void defineLangFeatures(String lang,LanguageFeatures features){
        this.featuresByLang.put(lang,features);
    }
    public LanguageFeatures getFeaturesByLang(String lang){
        return this.featuresByLang.get(lang);
    }

}
