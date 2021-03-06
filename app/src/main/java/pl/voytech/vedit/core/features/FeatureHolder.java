package pl.voytech.vedit.core.features;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.voytech.vedit.core.EditorBuffer;
import pl.voytech.vedit.core.ObjectCache;

/**
 * Created by USER on 2016-11-08.
 */

public abstract class FeatureHolder implements FeatureAware{
    private final List<Feature> features = new ArrayList<>();

    public FeatureHolder(){
        ObjectCache.i().add(this);
    }

    @Override
    public void attachFeature(Feature feature, EditorBuffer buffer) {
        this.features.add(feature);
        //ObjectCache.i().add(feature);
        feature.setHolder(this);
        feature.onFeatureAttach(this,buffer);
    }

    @Override
    public void detachFeature(Feature feature, EditorBuffer buffer) {
        this.features.remove(feature);
        feature.setHolder(null);
        feature.onFeatureDetach(this,buffer);
        ObjectCache.i().remove(feature); //this should not be required. WeakReference.
    }
    @Override
    public void detachFeatures(EditorBuffer buffer){
       Iterator<Feature> iter = this.features.iterator();
       while (iter.hasNext()){
           Feature feature = iter.next();
           feature.onFeatureDetach(this,buffer);
           feature.setHolder(null);
           iter.remove();
           ObjectCache.i().remove(feature); // this should not be required.
       }
    }
    public List<Feature> getFeatures(){
        return this.features;
    }
}
