package pl.voytech.vedit.core.renderers.core;

import android.graphics.Canvas;

import java.util.HashMap;
import java.util.Map;

import pl.voytech.vedit.core.EditorConfig;

/**
 * Created by USER on 2016-11-05.
 */

public class RenderersRegistry {
    private static RenderersRegistry instance;
    private final Map<Class<? extends Renderable>,Renderer> registry = new HashMap<>();
    private RenderersRegistry(){}

    public static RenderersRegistry i(){
        if (instance == null){
            instance = new RenderersRegistry();
        }
        return  instance;
    }

    public <T extends Renderable> void register(Class<T> renderableClass,Renderer<T> renderer){
        this.registry.put(renderableClass,renderer);
    }

    public <T extends  Renderable> Renderer<T> get(Class<T> renderableClass){
        return registry.get(renderableClass);
    }

    public Renderer<? extends Renderable> get(Renderable renderable){
        return registry.get(renderable.getClass());
    }
    
    public <T extends Renderable> void render(T renderable, Canvas canvas, EditorConfig config){
        Renderer<T> renderer = registry.get(renderable.getClass());
        if (renderer!=null){
            renderer.render(renderable,canvas,config);
        }
    }
}
