package pl.voytech.vedit.core.renderers.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by USER on 2016-11-05.
 */

public abstract class CachingRenderer<T extends Renderable> implements Renderer<T> {
    private final Map<T,Map<String,Object>> cache = new HashMap<>();

    protected Map<String,Object> getCache(T obj){
        if (!cache.containsKey(obj)){
            cache.put(obj,new HashMap<String,Object>());
        }
        return cache.get(obj);
    }
    public void clearCache(){
        cache.clear();
    }

}
