package pl.voytech.vedit.core.languages.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-03.
 */

public class LangTokenDef extends LangPartDef<Token,Token>{

    public enum TokenGroup{
        KEYWORD,
        SEPARATOR,
        LITERAL,
        IDENTIFIER,
        OPERATOR
    };
    private String pattern;
    private Pattern patternc;
    private TokenGroup group;
    private List<LangProductionDef> startsProductions = new ArrayList<LangProductionDef>();

    public String getPattern() {
        return pattern;
    }
    public Pattern getCompiledPattern(){
        return patternc;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
        this.patternc = Pattern.compile(pattern);
    }
    public TokenGroup getGroup() {
        return group;
    }

    public void setGroup(TokenGroup group) {
        this.group = group;
    }

    public List<LangProductionDef> getStartsProductions() {
        return startsProductions;
    }

    @Override
    public Token instance(EditorBuffer buffer) {
        return buffer.newToken(this.getId());
    }


}
