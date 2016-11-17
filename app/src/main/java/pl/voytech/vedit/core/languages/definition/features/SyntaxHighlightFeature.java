package pl.voytech.vedit.core.languages.definition.features;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.features.AbstractFeature;

/**
 * Created by USER on 2016-11-03.
 */

public class SyntaxHighlightFeature extends AbstractFeature<Token>  {
    private int color;

    public SyntaxHighlightFeature(Integer color){
        this.color = color;
        this.setOverrideDefaultTokenRenderer(true);
    }

    @Override
    public void onFeatureAttach(Token token, EditorBuffer buffer) {

    }

    @Override
    public void onFeatureDetach(Token parent, EditorBuffer buffer) {

    }

    public int getColor() {
        return color;
    }

}
