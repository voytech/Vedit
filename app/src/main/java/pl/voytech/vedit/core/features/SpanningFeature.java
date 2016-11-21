package pl.voytech.vedit.core.features;

import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-21.
 */

public abstract class SpanningFeature<T extends FeatureAware> extends AbstractFeature<T>{
    private Token start;
    private Token end;

}
