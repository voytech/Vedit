package pl.voytech.vedit.core.languages.rt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    private Map<UUID,String> analyzed = new HashMap<>();

    public void setLanguage(LangDef language){
        this.language = language;
        this.features = LanguageFeaturesProvider.i().getFeaturesByLang(language.getLanguageName());
    }
    public boolean alreadyAnalyzed(Token token){
        if (!analyzed.containsKey(token.getId())){
            return false;
        }
        String content = analyzed.get(token.getId());
        return content.equals(token.getValue());
    }

    public void analyze(Token token, EditorBuffer buffer){
        if (token==null) return;
        //if (alreadyAnalyzed(token)) return;
        if (token.getState() != Token.State.FINISHED){
            token.detachFeatures(buffer);
            return;
        }
        String val = token.getValue();
        LangPartDef tokenDef = language.find(val);
        if (tokenDef!=null){
            List<String> featureKeys = features.getFeaturesByLangPart(tokenDef);
            for (String key : featureKeys) {
                FeatureFactory.i().bind(key,token,buffer);
            }
            //analyzed.put(token.getId(),token.getValue());
        }else{
            token.detachFeatures(buffer);
        }
    }
}
