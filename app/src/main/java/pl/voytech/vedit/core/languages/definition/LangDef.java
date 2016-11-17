package pl.voytech.vedit.core.languages.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by USER on 2016-11-03.
 */

public class LangDef {
    private String languageName;
    private final Map<String,LangPartDef> grammar = new HashMap<>();
    public LangDef(String languageName){
        this.languageName = languageName;
    }
    public static LangDef fromLanguageFile(){
        return null;
    }

    public static LangDef fromJSON(String definition){
        return null;
    }

    public Map<String, LangPartDef> getGrammar() {
        return grammar;
    }
    public void addLangPart(LangPartDef def){
        grammar.put(def.getId(),def);
    }

    public List<LangTokenDef> byGroup(LangTokenDef.TokenGroup group){
        List<LangTokenDef> byG = new ArrayList<>();
        for (LangPartDef part : grammar.values()){
            if (part instanceof  LangTokenDef){
                LangTokenDef def = (LangTokenDef)part;
                if (def.getGroup() == group){
                    byG.add(def);
                }
            }
        }
        return byG;
    }

    public LangPartDef find(String token){
        for (LangPartDef part : grammar.values()){
            if (part instanceof LangTokenDef) {
                LangTokenDef tpart = (LangTokenDef)part;
                Pattern pattern = tpart.getCompiledPattern();
                Matcher matcher = pattern.matcher(token);
                if (matcher.find()) {
                    return part;
                }
            }
        }
        return null;
    }

    public String getLanguageName() {
        return languageName;
    }
}
