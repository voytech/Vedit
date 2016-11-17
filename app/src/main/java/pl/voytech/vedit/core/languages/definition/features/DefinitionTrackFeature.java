package pl.voytech.vedit.core.languages.definition.features;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.features.AbstractFeature;

/**
 * Created by USER on 2016-11-04.
 */

public class DefinitionTrackFeature extends AbstractFeature<Token>{
    private final String defId;
    public DefinitionTrackFeature(String defId){
        this.defId = defId;
    }
    @Override
    public void onFeatureAttach(Token parent, EditorBuffer buffer) {}


    @Override
    public void onFeatureDetach(Token parent, EditorBuffer buffer) {

    }

    public String getDefId() {
        return defId;
    }

}
