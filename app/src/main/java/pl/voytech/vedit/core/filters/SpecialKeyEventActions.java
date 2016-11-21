package pl.voytech.vedit.core.filters;

import android.view.KeyEvent;

import pl.voytech.vedit.core.AbstractKeyEventActions;
import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorActions;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.actions.BackspaceAction;
import pl.voytech.vedit.core.actions.CursorMoveAction;
import pl.voytech.vedit.core.actions.EnterAction;
import pl.voytech.vedit.core.actions.SpaceAction;

/**
 * Created by USER on 2016-11-05.
 */

public class SpecialKeyEventActions extends AbstractKeyEventActions{
    @Override
    public void onNotConsumedKeyEvent(KeyEvent k,EditorBuffer buffer,Cursor cursor) {
        char ch = (char)k.getUnicodeChar();
        System.out.println(k.getKeyCode());
        switch (k.getKeyCode()){
            case KeyEvent.KEYCODE_ENTER:
                EditorActions.i().execute(EnterAction.class,buffer);
                setConsumed();
                break;
            case KeyEvent.KEYCODE_DEL:
                EditorActions.i().execute(BackspaceAction.class,buffer);
                setConsumed();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                EditorActions.i().execute(CursorMoveAction.class,buffer, Cursor.Movements.NEXT_ROW);
                setConsumed();
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                EditorActions.i().execute(CursorMoveAction.class,buffer,Cursor.Movements.PREV_ROW);
                setConsumed();
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                EditorActions.i().execute(CursorMoveAction.class,buffer,Cursor.Movements.NEXT_COLUMN);
                setConsumed();
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                EditorActions.i().execute(CursorMoveAction.class,buffer,Cursor.Movements.PREV_COLUMN);
                setConsumed();
                break;
            case KeyEvent.KEYCODE_SHIFT_LEFT:
                setConsumed();
                break;
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                setConsumed();
                break;
            case KeyEvent.KEYCODE_SPACE:
                EditorActions.i().execute(SpaceAction.class,buffer);
                setConsumed();
                break;
        }
    }
}
