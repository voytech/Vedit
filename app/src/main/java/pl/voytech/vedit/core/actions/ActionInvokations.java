package pl.voytech.vedit.core.actions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 2016-11-22.
 */

public class ActionInvokations {
    private final List<ActionInvokation> invokations = new ArrayList<ActionInvokation>();
    public ActionInvokations add(ActionInvokation invokation){
        invokations.add(invokation);
        return this;
    }
    public List<ActionInvokation> get(){
        return this.invokations;
    }
}
