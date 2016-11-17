package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-01.
 */

public class EnterAction extends CursorFirstArgAction {

    @Override
    public void executeWithCursor(final EditorBuffer buffer, final Cursor cursor, final Token currentToken, Object... obj) {
       if (currentToken!=null) {
           if (currentToken.pointedBy(cursor)){
               buffer.split();
           }
           else if (currentToken.rightBefore(cursor)) {
               buffer.end(currentToken);
           }
       };
       buffer.allTokensAfter(cursor, new EditorBuffer.TokenVisitor() {
            @Override
            public void visit(Token token,int index) {
                buffer.move(token,0,1);
            }
       });
       cursor.nextPos(Cursor.Movements.NEXT_ROW_START);
       buffer.allTokensInRow(new EditorBuffer.TokenVisitor(){
           int offset = 0;
           @Override
           public void visit(Token token, int index) {
               if (index == 0){
                    offset = token.getStartColumn();
               }
               buffer.move(token,-offset,0);
           }
       },cursor.getRow());
    }
}
