package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-02.
 */

public class CursorMoveAction extends CursorFirstArgAction{
    @Override
    public void executeWithCursor(EditorBuffer buffer, Cursor cursor, Token currentToken, Object... rest) {
        Cursor.Movements direction = (Cursor.Movements) rest[0];
        cursor.nextPos(direction,buffer);
    }
}
