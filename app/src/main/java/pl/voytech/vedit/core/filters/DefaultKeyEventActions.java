package pl.voytech.vedit.core.filters;

import android.view.KeyEvent;

import pl.voytech.vedit.core.AbstractKeyEventActions;
import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorActions;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.actions.BuildTokenAction;

/**
 * Created by USER on 2016-11-06.
 */

public class DefaultKeyEventActions extends AbstractKeyEventActions{
    @Override
    public void onNotConsumedKeyEvent(KeyEvent k, EditorBuffer buffer, Cursor cursor) {
        char ch = (char)k.getUnicodeChar();
        EditorActions.i().execute(BuildTokenAction.class,buffer,new Object[]{ch});
        setConsumed();
    }
}
