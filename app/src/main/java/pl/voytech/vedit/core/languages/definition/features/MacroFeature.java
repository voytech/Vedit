package pl.voytech.vedit.core.languages.definition.features;

import java.util.List;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.actions.ActionInvokation;
import pl.voytech.vedit.core.actions.ActionInvokations;
import pl.voytech.vedit.core.features.AbstractFeature;

/**
 * Created by USER on 2016-11-03.
 */

public class MacroFeature  extends AbstractFeature<Token> {
    private final List<ActionInvokation> invokations;
    public MacroFeature(ActionInvokations invocations){
        super();
        this.invokations = invocations.get();
    }
    @Override
    public void onFeatureAttach(Token parent, EditorBuffer buffer) {
        for (ActionInvokation invokation : invokations){
            invokation.invoke(buffer);
        }
    }

    @Override
    public void onFeatureDetach(Token parent, EditorBuffer buffer) {

    }
}
