package pl.voytech.vedit.core.filters;

import android.view.KeyEvent;

import pl.voytech.vedit.core.AbstractKeyEventActions;
import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorActions;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.actions.LangSeparatorAction;
import pl.voytech.vedit.core.languages.definition.LangDef;
import pl.voytech.vedit.core.languages.definition.LangTokenDef;

/**
 * Created by USER on 2016-11-06.
 */

public class LanguageKeyEventActions extends AbstractKeyEventActions {
    private LangDef langDef;
    public LanguageKeyEventActions(LangDef langDef){
        this.langDef = langDef;
    }
    @Override
    public void onNotConsumedKeyEvent(KeyEvent event, EditorBuffer buffer, Cursor cursor) {
        for (LangTokenDef tdef : langDef.byGroup(LangTokenDef.TokenGroup.SEPARATOR)){
            if (tdef.getId().equals(""+((char)event.getUnicodeChar()))){
                EditorActions.i().execute(LangSeparatorAction.class,buffer,new Object[]{((char)event.getUnicodeChar())});
                setConsumed();
            }
        }
    }
}
