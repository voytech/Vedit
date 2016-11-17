package pl.voytech.vedit.core.features;

/**
 * Created by USER on 2016-11-03.
 */

public abstract class AbstractFeature<T extends FeatureAware> implements Feature<T>{
    private boolean overrideHolderRenderer = false;
    protected T holder;
    public boolean isOverrideHolderRenderer() {
        return overrideHolderRenderer;
    }
    public AbstractFeature(){
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
