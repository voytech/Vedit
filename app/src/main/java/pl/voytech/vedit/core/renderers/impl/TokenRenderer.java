package pl.voytech.vedit.core.renderers.impl;

import android.graphics.Canvas;

import pl.voytech.vedit.core.EditorConfig;
import pl.voytech.vedit.core.Token;
import pl.voytech.vedit.core.features.Feature;
import pl.voytech.vedit.core.renderers.core.Renderer;
import pl.voytech.vedit.core.renderers.core.RenderersRegistry;

/**
 * Created by USER on 2016-11-01.
 */

public class TokenRenderer implements Renderer<Token>{

    @Override
    public void render(Token renderable, Canvas canvas, EditorConfig config) {
        String text = renderable.getValue();
        if (text.trim().isEmpty()) return;
        float x = renderable.getStartColumn()*config.getGlyphWidth();
        float y = renderable.getStartRow()*(config.getGlyphHeight()+config.getTopMargin());
        canvas.drawText(text,x,y+config.getTopMargin(),config.getPaint());
        for (Feature feature : renderable.getFeatures()){
            if (RenderersRegistry.i().get(feature) != null) {
                RenderersRegistry.i().render(feature, canvas, config);
            }
        }
    }
}
