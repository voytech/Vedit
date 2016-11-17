package pl.voytech.vedit.core;

import pl.voytech.vedit.core.renderers.core.Renderable;

/**
 * Created by USER on 2016-10-26.
 */

public class Cursor implements Renderable {

    public enum Movements{
        NEXT_COLUMN,
        PREV_COLUMN,
        NEXT_ROW,
        PREV_ROW,
        START_ROW,
        END_ROW,
        START,
        END,
        NEXT_ROW_START,
        NEXT_ROW_END,
        PREV_ROW_END,
        PREV_ROW_START
    }
    private int row;
    private int column;
    private final EditorConfig config;

    public Cursor(EditorConfig config){
        this.row = 1;
        this.column = 1;
        this.config = config;
    }
    public Cursor(int row, int column,EditorConfig config){
        this.row = row;
        this.column = column;
        this.config = config;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void nextPos(Movements direction){
        switch(direction){
            case PREV_ROW:    row--;
                              break;
            case NEXT_ROW:
                              row++;
                              break;
            case PREV_COLUMN: if (column == 1){
                                column = config.getRowLength();
                                row --;
                              } else {
                                column--;
                              }
                              break;
            case NEXT_COLUMN: if (column == config.getRowLength()){
                                column = 1;
                                row++;
                              } else {
                                column++;
                              }
                              break;
            case NEXT_ROW_START: column = 1;
                                 row++;
                                 break;
            case PREV_ROW_START: column = 1;
                                 row--;
                                 break;
        }
    }



}
