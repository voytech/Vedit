package pl.voytech.vedit.core;

import android.graphics.Color;

/**
 * Created by USER on 2016-10-26.
 */

public class EditorState {
    private float textHeight = 40;
    private int baseColor = Color.WHITE;
    private final Cursor cursor;
    private int indentLevel;

    public EditorState(EditorConfig config){
        this.cursor = new Cursor(config);
    }
    public float getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(float textHeight) {
        this.textHeight = textHeight;
    }

    public int getBaseColor() {
        return baseColor;
    }

    public void setBaseColor(int baseColor) {
        this.baseColor = baseColor;
    }

    public Cursor getCursor(){
        return cursor;
    }

    public int getIndentLevel() {
        return indentLevel;
    }

    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }
}
