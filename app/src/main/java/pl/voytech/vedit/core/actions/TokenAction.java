package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.EditorAction;
import pl.voytech.vedit.core.EditorBuffer;

/**
 * Created by USER on 2016-11-21.
 */

public class TokenAction implements EditorAction{
    @Override
    public void execute(EditorBuffer buffer, Object... args) {
        if (args.length > 0) {
            if (args[0] instanceof String) {
                buffer.newToken((String)args[0]);
            }
        }
    }
}
