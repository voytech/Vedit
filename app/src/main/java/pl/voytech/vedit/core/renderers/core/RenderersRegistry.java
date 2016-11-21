package pl.voytech.vedit.core.renderers.core;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import pl.voytech.vedit.core.EditorConfig;
import pl.voytech.vedit.core.ObjectCache;
import pl.voytech.vedit.core.Token;

/**
 * Created by USER on 2016-11-05.
 */

public class RenderersRegistry {
    private class Priority{
        public Integer priority;
        public Class<? extends Renderable> clazz;
        public Priority(Integer priority,Class<? extends  Renderable> clazz){
            this.priority = priority;
            this.clazz = clazz;
        }
    }
    private static RenderersRegistry instance;
    private final Map<Class<? extends Renderable>,Renderer> registry = new HashMap<>();
    private final List<Priority> priorities = new ArrayList<>();
    private final List<Renderable> rendered = new ArrayList<>();
    private final Comparator<Priority> PRIORITY_COMP = new Comparator<Priority>() {
        @Override
        public int compare(Priority p1, Priority p2) {
            return p2.priority - p1.priority;
        }
    };
    private RenderersRegistry(){}

    public static RenderersRegistry i(){
        if (instance == null){
            instance = new RenderersRegistry();
        }
        return  instance;
    }

    public <T extends Renderable> void register(Class<T> renderableClass,Renderer<T> renderer,int priority){
        this.registry.put(renderableClass,renderer);
        this.priorities.add(new Priority(priority,renderableClass));
        Collections.sort(priorities,PRIORITY_COMP);
    }
    public <T extends Renderable> void register(Class<T> renderableClass,Renderer<T> renderer){
        this.registry.put(renderableClass,renderer);
        this.priorities.add(new Priority(0,renderableClass));
        Collections.sort(priorities,PRIORITY_COMP);
    }


    public <T extends  Renderable> Renderer<T> get(Class<T> renderableClass){
        return registry.get(renderableClass);
    }

    public Renderer<? extends Renderable> get(Renderable renderable){
        return registry.get(renderable.getClass());
    }

    public void prioritize(){

    }

    public <T extends Renderable> void render(T renderable, Canvas canvas, EditorConfig config){
        Renderer<T> renderer = registry.get(renderable.getClass());
        if (renderer!=null && !rendered.contains(renderable)){
            renderer.render(renderable,canvas,config);
            rendered.add(renderable);
        }
    }

    /**
     * Entry point of rendering process.
     * @param canvas
     * @param config
     */
    public void renderAll(final Canvas canvas, final EditorConfig config){
        rendered.clear();
        for (Priority priority : priorities){
            Class<? extends Renderable> renderableClass = priority.clazz;
            Renderer<? extends Renderable> renderer = registry.get(renderableClass);
            if (renderer != null){
                ObjectCache.i().all(renderableClass, new ObjectCache.CacheIterator() {
                    @Override
                    public void iterate(Object obj) {
                        if (Renderable.class.isAssignableFrom(obj.getClass())) {
                            Renderable renderable = (Renderable) obj;
                            if (!rendered.contains(renderable)) {
                                render(renderable, canvas, config);
                                rendered.add(renderable);
                            }
                        }
                    }
                });
            }
        }
    }
}
