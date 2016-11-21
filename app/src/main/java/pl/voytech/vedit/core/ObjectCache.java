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
        }
        list.add(new WeakReference<T>(object));
    }
    public <T> List<T> all(Class<T> clazz, Callable<T>... callables) throws Exception {
        List<WeakReference<?>> list =  cache.get(clazz);
        List<T> allByClass = new ArrayList<T>();
        if (list != null){
            for (WeakReference<?> element : list){
                T deref = (T) element.get();
                if (deref != null){
                    allByClass.add(deref);
                    for (Callable<T> callable : callables ){
                        callable.call();
                    }
                }
            }
        }
        return allByClass;
    }

}
