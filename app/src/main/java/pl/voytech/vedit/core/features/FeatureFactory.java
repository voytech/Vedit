package pl.voytech.vedit.core.features;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import pl.voytech.vedit.core.EditorBuffer;

/**
 * Created by USER on 2016-11-05.
 */

public class FeatureFactory {
    public class FeatureDef<T extends Feature>{
        private Class<T> featureClass;
        private Object[] featureParams;
        public FeatureDef(Class<T> featureClass,Object... params){
            this.featureClass = featureClass;
            this.featureParams = params;
        }
        public Object[] getFeatureParams() {
            return featureParams;
        }

        public Class<T> getFeatureClass() {
            return featureClass;
        }
    }
    private static FeatureFactory instance;
    private final Map<String,FeatureDef<? extends Feature>> featureMap = new HashMap<>();
    private FeatureFactory(){}
    public static FeatureFactory i(){
        if (instance == null){
            instance = new FeatureFactory();
        }
        return instance;
    }

    public <T extends Feature> void define(String name, Class<T> feature,Object... args){
        featureMap.put(name, new FeatureDef<T>(feature,args));
    }

    private <T extends AbstractFeature> T instance(Class<T> feature,Object... args){
        Class[] conArgs = new Class[args.length];
        for (int i=0;i<args.length;i++){
            conArgs[i] = args[i].getClass();
        }
        try {
            Constructor constructor = feature.getConstructor(conArgs);
            T featureInst = (T) constructor.newInstance(args);
            return  featureInst;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    public <T extends  AbstractFeature> void bind(String name, FeatureHolder featureAware, EditorBuffer editorBuffer){
        FeatureDef def = featureMap.get(name);
        if (def!=null){
            T feature = (T) instance(def.featureClass,def.getFeatureParams());
            if (feature!=null) {
                featureAware.attachFeature(feature, editorBuffer);
            }
        }
    }
}
