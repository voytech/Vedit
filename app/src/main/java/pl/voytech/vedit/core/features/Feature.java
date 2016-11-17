package pl.voytech.vedit.core.features;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.renderers.core.Renderable;

/**
 * Created by USER on 2016-10-27.
 */

public interface Feature<T extends  FeatureAware> extends Renderable {
    void onFeatureAttach(T parent, EditorBuffer buffer);
    void onFeatureDetach(T parent, EditorBuffer buffer);
    T getHolder();
    void setHolder(T holder);
}
