package pl.voytech.vedit.core;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by USER on 2016-11-21.
 */

public class ObjectCache {
    public interface CacheIterator<T> {
        void iterate(T obj);
    }
    private final HashMap<Class<?>,List<WeakReference<?>>> cache = new HashMap<>();
    private static ObjectCache instance;
    private ObjectCache(){

    }
    public static ObjectCache i(){
        if (instance == null){
            instance = new ObjectCache();
        }
        return instance;
    }
    public <T> void add(T object){
        List list =  cache.get(object.getClass());
        if (list == null){
            list = new ArrayList<WeakReference<T>>();
            cache.put(object.getClass(),list);
        }
        cache.get(object.getClass()).add(new WeakReference<T>(object));
    }
    public <T> void remove(T object){
        List<WeakReference<?>> list =  cache.get(object.getClass());
        for (WeakReference element : list){
            if (object.equals(element.get()))
                element.clear();
        }
    }
    public <T> List<T> all(Class<T> clazz, CacheIterator<T>... iterables)  {
        List<WeakReference<?>> list =  cache.get(clazz);
        List<T> allByClass = new ArrayList<T>();
        if (list != null){
            for (WeakReference<?> element : list){
                T deref = (T) element.get();
                if (deref != null){
                    allByClass.add(deref);
                    for (CacheIterator iterable : iterables ){
                        iterable.iterate(deref);
                    }
                }
            }
        }
        return allByClass;
    }

}
