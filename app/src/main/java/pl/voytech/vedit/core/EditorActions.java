package pl.voytech.vedit.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 2016-11-01.
 */

public class EditorActions {
    private final Map<Class<EditorAction>,EditorAction> actions = new HashMap<>();

    public void addMapping(EditorAction action){
        actions.put((Class<EditorAction>) action.getClass(),action);
    }
    public void execute(Class<? extends EditorAction> clazz,EditorBuffer buffer,Object... args){
        EditorAction action = actions.get(clazz);
        if (action != null){
            action.execute(buffer,args);
        }
    }
}
