package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-06.
 */

public class SpaceAction extends CursorFirstArgAction{
    @Override
    public void executeWithCursor(final EditorBuffer buffer, Cursor cursor, Token currentToken, Object... rest) {
        if (currentToken!=null) {
            if (currentToken.pointedBy(cursor)){
                buffer.split();
            }
            else if (currentToken.rightBefore(cursor)) {
                buffer.end(currentToken);
            }
        }
        buffer.allTokensInRowAfter(cursor, new EditorBuffer.TokenVisitor() {
            @Override
            public void visit(Token token,int index) {
                buffer.move(token,1,0);
            }
        },cursor.getRow(),true);
        cursor.nextPos(Cursor.Movements.NEXT_COLUMN);
    }
}
