package pl.voytech.vedit.core.languages.definition.features.renderers;

import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.TextPaint;

import java.util.Map;

import pl.voytech.vedit.core.EditorConfig;
import pl.voytech.vedit.core.languages.definition.features.SyntaxHighlightFeature;
import pl.voytech.vedit.core.renderers.core.CachingRenderer;

/**
 * Created by USER on 2016-11-05.
 */

public class SyntaxHighlightFeatureRenderer extends CachingRenderer<SyntaxHighlightFeature> {
    public SyntaxHighlightFeatureRenderer(){
    }

    private TextPaint managePaintProps(SyntaxHighlightFeature keywordFeature, EditorConfig config){
        Map<String,Object> props = getCache(keywordFeature);
        if (!props.containsKey("paint")){
            TextPaint paint = new TextPaint();
            paint.setDither(true);
            paint.setTextSize(config.getGlyphHeight());
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.MONOSPACE);
            paint.setColor(keywordFeature.getColor());
            props.put("paint",paint);
        }
        return (TextPaint)props.get("paint");
    }

    @Override
    public void render(SyntaxHighlightFeature renderable, Canvas canvas, EditorConfig config) {
        String text = renderable.getHolder().getValue();
        if (text.trim().isEmpty()) return;
        TextPaint paint = managePaintProps(renderable,config);
        float x = renderable.getHolder().getStartColumn()*config.getGlyphWidth();
        float y = renderable.getHolder().getStartRow()*(config.getGlyphHeight()+config.getTopMargin());
        canvas.drawText(text,x,y+config.getTopMargin(),paint);
    }
}
