package pl.voytech.vedit.core.features;

import pl.voytech.vedit.core.EditorBuffer;

/**
 * Created by USER on 2016-11-08.
 */

public interface FeatureAware<T extends Feature> {
    void attachFeature(T feature, EditorBuffer buffer);
    void detachFeature(T feature, EditorBuffer buffer);
    void detachFeatures(EditorBuffer buffer);
}
