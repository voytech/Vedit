package pl.voytech.vedit.core;

import android.view.KeyEvent;

/**
 * Created by USER on 2016-11-06.
 */

public abstract class AbstractKeyEventActions implements KeyEventActions{
    private boolean consumed = false;
    public void setConsumed(){
        this.consumed = true;
    }
    public boolean isConsumed(){
        return consumed;
    }

    @Override
    public void onKeyEvent(KeyEvent k, EditorBuffer buffer, Cursor cursor) {
        this.consumed = false;
        onNotConsumedKeyEvent(k,buffer,cursor);
    }
    public abstract void onNotConsumedKeyEvent(KeyEvent k, EditorBuffer buffer, Cursor cursor);
}
