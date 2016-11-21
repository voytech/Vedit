package pl.voytech.vedit.core;

import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2016-10-26.
 */

public class TokenReader extends AbstractGenericReader<KeyEvent,Token> implements EditorBuffer.TokenListener {
    private Token current;
    private boolean terminating = false;
    private char terminator;
    private final EditorState state;
    private final EditorBuffer buffer;
    private final List<TokenReaderFilter> filters = new ArrayList<TokenReaderFilter>();
    private final List<KeyEventActions> keyEventActions = new ArrayList<>();

    public TokenReader(EditorBuffer buffer,EditorState state,KeyEventActions... keyEventActions){
        this.state = state;
        this.buffer = buffer;
        this.buffer.setTokenListener(this);
        for (KeyEventActions kea : keyEventActions) {
            this.keyEventActions.add(kea);
        }
    }


    private boolean after(KeyEvent character, Cursor cursor, Token token){
        boolean applied = false;
        for (TokenReaderFilter tokenReaderFilter : filters){
            tokenReaderFilter.afterCharacterApply(character,cursor,token,buffer);
            applied = true;
        }
        return applied;
    }


    private void tokenReady(Cursor cursor, Token token){
        outputReady(token);
        for (TokenReaderFilter tokenReaderFilter : filters){
            tokenReaderFilter.tokenReady(cursor,token,buffer);
        }
    }
    private void keyActions(KeyEvent event,EditorBuffer buffer,Cursor cursor){
        for (KeyEventActions kea : keyEventActions){
            kea.onKeyEvent(event,buffer,cursor);
            if (kea instanceof AbstractKeyEventActions){
                if (((AbstractKeyEventActions) kea).isConsumed())
                    break;
            }
        }
    }

    public void read(KeyEvent character){
        Cursor cursor = this.state.getCursor();
        keyActions(character,buffer,cursor);
        after(character,cursor,current);
    }



    public void addFilter(TokenReaderFilter tokenBuilderFilter) {
        this.filters.add(tokenBuilderFilter);
    }
    public void addKeyEventActions(KeyEventActions actions) {
        this.keyEventActions.add(actions);
    }

    @Override
    public void ready(Token token) {
        tokenReady(this.state.getCursor(),token);
    }
}
