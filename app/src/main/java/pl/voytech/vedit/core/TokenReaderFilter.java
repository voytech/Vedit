package pl.voytech.vedit.core;

import android.view.KeyEvent;

/**
 * Created by USER on 2016-10-26.
 */

public interface TokenReaderFilter {
    //boolean shouldTerminate(KeyEvent ch, Cursor cursor, Token token);
    void afterCharacterApply(KeyEvent ch, Cursor cursor, Token token,  EditorBuffer buffer);
    void tokenReady(Cursor c, Token t, EditorBuffer buffer);
   // boolean preventNewToken(KeyEvent key);
}
