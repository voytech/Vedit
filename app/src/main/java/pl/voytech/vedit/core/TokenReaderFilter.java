package pl.voytech.vedit.core;

import android.view.KeyEvent;

/**
 * Created by USER on 2016-10-26.
 */

public interface TokenReaderFilter {
     void afterCharacterApply(KeyEvent ch, Cursor cursor, Token token,  EditorBuffer buffer);
    void tokenStateChanged(Cursor c, Token t, EditorBuffer buffer);
 }
