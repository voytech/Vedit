package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.languages.definition.LangDef;

/**
 * Created by USER on 2016-11-06.
 */

public class LangSeparatorAction extends CursorFirstArgAction{
    private final LangDef langDef;
    public LangSeparatorAction(LangDef language){
        super();
        langDef = language;
    }
    private void tryEnd(Cursor cursor,EditorBuffer buffer){
        Token currentToken = buffer.currentToken();
        if (currentToken!=null) {
            if (currentToken.rightBefore(cursor)) {
                buffer.end(currentToken);
            }
        }
    }

    @Override
    public void executeWithCursor(final EditorBuffer buffer, Cursor cursor, Token currentToken, Object... args) {
        char ch = (char)args[0];
        if (buffer.split()) {
            buffer.insert(ch);
            buffer.split();
            buffer.allTokensInRowAfter(cursor, new EditorBuffer.TokenVisitor() {
                @Override
                public void visit(Token token,int index) {
                    buffer.move(token,1,0);
                }
            },cursor.getRow(),false);
        }else {
            tryEnd(cursor, buffer);
            buffer.newToken();
            buffer.insert(ch);
            tryEnd(cursor, buffer);
            buffer.allTokensInRowAfter(cursor, new EditorBuffer.TokenVisitor() {
                @Override
                public void visit(Token token,int index) {
                    buffer.move(token,1,0);
                }
            },cursor.getRow(),true);
        }

    }
}
