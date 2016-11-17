package pl.voytech.vedit.core;

import android.view.KeyEvent;

/**
 * Created by USER on 2016-11-05.
 */

public interface KeyEventActions {
    void onKeyEvent(KeyEvent event, EditorBuffer buffer, Cursor cursor, EditorActions actions);
}
