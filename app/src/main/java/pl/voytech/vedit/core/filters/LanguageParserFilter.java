package pl.voytech.vedit.core.filters;

import android.view.KeyEvent;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorActions;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.TokenReaderFilter;
import pl.voytech.vedit.core.actions.LangAnalyzerAction;
import pl.voytech.vedit.core.languages.definition.LangDef;

/**
 * Created by USER on 2016-11-05.
 */

public class LanguageParserFilter implements TokenReaderFilter {
    private LangDef languageDefinition;

    public LanguageParserFilter(LangDef langDef) {
        this.languageDefinition = langDef;
    }


    @Override
    public void afterCharacterApply(KeyEvent ch, Cursor cursor, Token token, EditorBuffer buffer) {
        EditorActions.i().execute(LangAnalyzerAction.class,buffer,cursor); //maybe expensive ?
    }

    @Override
    public void tokenReady(Cursor c, Token t,  EditorBuffer buffer) {
        //actions.execute(LangAnalyzerAction.class,buffer,c);
    }


}
