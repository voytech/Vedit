package pl.voytech.vedit.core.renderers.impl;

import android.graphics.Canvas;

import pl.voytech.vedit.core.Cursor;
import pl.voytech.vedit.core.EditorConfig;
import pl.voytech.vedit.core.renderers.core.Renderer;

/**
 * Created by USER on 2016-11-02.
 */

public class CursorRenderer implements Renderer<Cursor> {

    @Override
    public void render(Cursor renderable, Canvas canvas, EditorConfig config) {
        float x = renderable.getColumn()*config.getGlyphWidth();
        float y = renderable.getRow()*(config.getGlyphHeight()+config.getTopMargin()) - config.getGlyphHeight() + config.getTopMargin();
        float x1 = x + config.getGlyphWidth();
        float y1 = y + config.getGlyphHeight();
        canvas.drawRect(x,y,x1,y1,config.getPaint());
    }
}
