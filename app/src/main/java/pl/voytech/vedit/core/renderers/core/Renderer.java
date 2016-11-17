package pl.voytech.vedit.core.renderers.core;

import android.graphics.Canvas;

import pl.voytech.vedit.core.EditorConfig;

/**
 * Created by USER on 2016-10-30.
 */

public interface Renderer<T extends Renderable> {
    void render(T renderable, Canvas canvas, EditorConfig config);
}
