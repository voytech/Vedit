package pl.voytech.vedit.core.features;

import pl.voytech.vedit.core.CachedObject;

/**
 * Created by USER on 2016-11-03.
 */

public abstract class AbstractFeature<T extends FeatureAware> extends CachedObject implements Feature<T>{
    private boolean overrideHolderRenderer = false;
    protected T holder;
    public boolean isOverrideHolderRenderer() {
        return overrideHolderRenderer;
    }
    public AbstractFeature(){
        super();
    }
    public void setOverrideDefaultTokenRenderer(boolean overrideDefaultTokenRenderer) {
        this.overrideHolderRenderer = overrideDefaultTokenRenderer;
    }
    @Override
    public T getHolder(){
        return holder;
    }
    @Override
    public void setHolder(T h){
        this.holder = h;
    }

}
