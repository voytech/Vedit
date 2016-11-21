package pl.voytech.vedit.core;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;


/**
 * Created by USER on 2016-11-01.
 */

public class EditorConfig {
    private final TextPaint textPaint = new TextPaint();
    private float glyphHeight = 45;
    private float glyphWidth = -1; //needs to be measured on canvas.
    private String font = "aA";
    private int rowLength = 300;
    private int rowCount = 1000;
    private int indentLength = 2;
    private int baseColor = Color.WHITE;
    private int backgroundColor = Color.WHITE;
    private int topMargin = 0;

    public EditorConfig(){
        Typeface tf = Typeface.create(Typeface.MONOSPACE,Typeface.BOLD);
        textPaint.setTypeface(tf);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextSize(glyphHeight);
    }
    public float getGlyphHeight() {
        return textPaint.getTextSize();
    }

    public void setGlyphHeight(float glyphHeight) {
        this.glyphHeight = glyphHeight;
        this.textPaint.setTextSize(glyphHeight);
    }

    public float getGlyphWidth() {
        if (glyphWidth == -1){
            this.glyphWidth = this.textPaint.measureText("Q");
        }
        return this.glyphWidth;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;

    }

    public int getRowLength() {
        return rowLength;
    }

    public void setRowLength(int rowLength) {
        this.rowLength = rowLength;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(int baseColor) {
        this.textPaint.setColor(baseColor);
        this.baseColor = baseColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public Paint getPaint() {
        return textPaint;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public int getIndentLength() {
        return indentLength;
    }

    public void setIndentLength(int indentLength) {
        this.indentLength = indentLength;
    }
}
