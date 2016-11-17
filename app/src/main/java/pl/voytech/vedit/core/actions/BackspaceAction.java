package pl.voytech.vedit.core.actions;


import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-01.
 */

public class BackspaceAction extends CursorFirstArgAction {
    @Override
    public void executeWithCursor(final EditorBuffer buffer, Cursor cursor, Token currentToken, Object... obj) {
        if (currentToken==null){
            cursor.nextPos(Cursor.Movements.PREV_COLUMN);
        } else {
            buffer.removeLeft();
        }
        buffer.allTokensInRowAfter(cursor, new EditorBuffer.TokenVisitor() {
            @Override
            public void visit(Token token,int index) {
                buffer.move(token,-1,0);
            }
        },cursor.getRow(),false);
        if (currentToken!=null){
            buffer.merge();
        }
    }

}
