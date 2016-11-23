package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.languages.definition.LangDef;
import pl.voytech.vedit.core.languages.definition.LangTokenDef;

/**
 * Created by USER on 2016-11-01.
 */

/**
 * Build Token action should focus only on building of particular tokens.
 * Should create separate action called SeparateTokenAction which would take \s and lang separators and
 * separate existing tokens.
 */
public class BuildTokenAction extends CursorFirstArgAction {
    private final LangDef langDef;

    public BuildTokenAction(LangDef langDef) {
        this.langDef = langDef;
    }

    /**
     * As long as buffer.insert() advances cursor by one position to right direction,
     * we need to move cursor back so that cursor points to inserted character.
     * Thanks to that merge logic will handle inserted character as new token,
     * and token which ends right before cursor position thus allowing mering.
     * This case covers situation when we have completed token like:
     * 'thisistoken' then finished editing by delimiting using space e.g:
     * 'thisistoken'_'newtoken' then moved back to position right after 'thisistoken' and started writing like:
     * 'thisistoken'n'_' 'newtoken' - in this particular case 'n' should be merged with 'thisistoken' but cursor is in next position
     * and it resolves 'right before token' to be 'n' and current token to be null.
     */

    private void tryMerge(EditorBuffer buffer,Cursor cursor){
        cursor.nextPos(Cursor.Movements.PREV_COLUMN,buffer);
        if (!isSeparator(buffer,cursor)) {
            buffer.merge();
        }
        cursor.nextPos(Cursor.Movements.NEXT_COLUMN,buffer);
    }

    private boolean isSeparator(EditorBuffer buffer,Cursor cursor){
        Token mayBeSeparator = buffer.tokenBefore(cursor);
        if (mayBeSeparator!=null) {
            for (LangTokenDef tdef : langDef.byGroup(LangTokenDef.TokenGroup.SEPARATOR)) {
                if (tdef.getId().equals(mayBeSeparator.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public void executeWithCursor(final EditorBuffer buffer, Cursor cursor, Token currentToken, Object... args) {
        char ch = (char)args[0];
        if (!(ch == ' ' && currentToken.getStartColumn() <= cursor.getColumn() && currentToken.getEndColumn() > cursor.getColumn())) {
            if (isSeparator(buffer,cursor)){
                buffer.newToken();
            }
            buffer.insert(ch);
            tryMerge(buffer,cursor);
        } else {
            cursor.nextPos(Cursor.Movements.NEXT_COLUMN,buffer);
        }
        buffer.allTokensInRowAfter(cursor, new EditorBuffer.TokenVisitor() {
            @Override
            public void visit(Token token,int index) {
                buffer.move(token,1,0);
            }
        },cursor.getRow(),true);
    }
}
