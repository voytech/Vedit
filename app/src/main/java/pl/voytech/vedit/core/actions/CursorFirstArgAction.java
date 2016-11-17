package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorAction;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-01.
 */

public abstract class CursorFirstArgAction implements EditorAction {
    public abstract void executeWithCursor(EditorBuffer buffer,Cursor cursor,Token currentToken,Object... rest);
    @Override
    public void execute(EditorBuffer buffer, Object... args) {
        Cursor cursor = buffer.getState().getCursor();
        Token currentToken = buffer.currentToken();
        executeWithCursor(buffer,cursor,currentToken,args);
    }
}
