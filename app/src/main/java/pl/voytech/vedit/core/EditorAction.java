package pl.voytech.vedit.core;

/**
 * Created by USER on 2016-11-01.
 */

public interface EditorAction {
    void execute(EditorBuffer buffer, Object... args);
}
