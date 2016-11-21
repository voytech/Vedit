package pl.voytech.vedit.core.actions;

import pl.voytech.vedit.core.EditorAction;
import pl.voytech.vedit.core.EditorActions;
import pl.voytech.vedit.core.EditorBuffer;

/**
 * Created by USER on 2016-11-21.
 */

public class ActionInvokation {
    private final Class<? extends EditorAction> action;
    private final Object[] args;
    public ActionInvokation(Class<? extends EditorAction> action,Object... args){
        this.action = action;
        this.args = args;
    }
    public void invoke(EditorBuffer buffer){
        EditorActions.i().execute(action,buffer,args);
    }
}
