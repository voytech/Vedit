package pl.voytech.vedit.core;

/**
 * Created by USER on 2016-11-22.
 */

public abstract class CachedObject {
    public CachedObject(){
        ObjectCache.i().add(this);
    }
}
