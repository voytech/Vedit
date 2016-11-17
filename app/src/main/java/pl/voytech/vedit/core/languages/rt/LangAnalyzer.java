package pl.voytech.vedit.core.languages.rt;

import java.util.List;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.features.FeatureFactory;
import pl.voytech.vedit.core.languages.definition.LangDef;
import pl.voytech.vedit.core.languages.definition.LangPartDef;
import pl.voytech.vedit.core.languages.definition.LanguageFeatures;
import pl.voytech.vedit.core.languages.definition.LanguageFeaturesProvider;

/**
 * Created by USER on 2016-11-03.
 */

public class LangAnalyzer {
    private LangDef language;
    private LanguageFeatures features;

    public void setLanguage(LangDef language){
        this.language = language;
        this.features = LanguageFeaturesProvider.i().getFeaturesByLang(language.getLanguageName());
    }

    public void analyze(Token token, EditorBuffer buffer){
        if (token==null) return;
        String val = token.getValue();
        LangPartDef tokenDef = language.find(val);
        if (tokenDef!=null){
            List<String> featureKeys = features.getFeaturesByLangPart(tokenDef);
            for (String key : featureKeys) {
                FeatureFactory.i().bind(key,token,buffer);
            }
        }else{
            token.detachFeatures(buffer);
        }
    }
}
