package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.languages.definition.LangDef;
import pl.voytech.vedit.core.languages.rt.LangAnalyzer;

/**
 * Created by USER on 2016-11-04.
 */

public class LangAnalyzerAction extends CursorFirstArgAction {
    private LangAnalyzer analyzer = new LangAnalyzer();
    @Override
    public void executeWithCursor(EditorBuffer buffer, Cursor cursor, Token currentToken, Object... rest) {
        analyzer.analyze(currentToken,buffer);
    }

    public void setLanguage(LangDef language) {
        this.analyzer.setLanguage(language);
    }
}
