package pl.voytech.vedit.core;

import java.util.UUID;

import pl.voytech.vedit.core.features.FeatureHolder;
import pl.voytech.vedit.core.renderers.core.Renderable;

/**
 * Created by USER on 2016-10-26.
 */

public class Token extends FeatureHolder implements Renderable,TokenApi{

    public enum State{
        EDITING,
        FINISHED
    }
    private String value = "";
    private int startColumn;
    private int startRow;
    private int endColumn;
    private int endRow;
    private final UUID id;
    private State state = State.EDITING;

    public Token(){
        id = UUID.randomUUID();
    }
    public Token(Cursor c){
        super();
        id = UUID.randomUUID();
        assertStart(c);
    }

    private void assertStart(Cursor c){
        if (value.isEmpty()){
            startColumn = c.getColumn();
            endColumn = c.getColumn();
            startRow = c.getRow();
            endRow = c.getRow();
        }
    }

    @Override
    public void insert(char character,Cursor c){
        assertStart(c);
        int offset = c.getColumn() - startColumn;
        if (offset == 0 && value.length() == 0){
            value+=character;
        }else {
            value = value.substring(0, offset)
                    .concat(character + "")
                    .concat(value.substring(offset));
        }
        state = State.EDITING;
        endColumn = startColumn + value.length()-1;
    }

    @Override
    public boolean removeLeft(Cursor c){
        int offset = c.getColumn() - startColumn;
        boolean result = true;
        boolean remove = true;
        if (offset <= 0){
            remove = false;
        } else if (offset == 1 && value.length() <= 1){
            result = false;
        }
        if (remove) {
            value = value.substring(0, offset - 1).concat(value.substring(offset));
            state = State.EDITING;
            endColumn = startColumn + value.length() - 1;
        }
        return result;
    }

    @Override
    public boolean removeRight(Cursor c){
        int offset = c.getColumn() - startColumn;
        if (c.getColumn() == endColumn){
            return false;
        }
        value = value.substring(0,offset).concat(value.substring(offset+1));
        state = State.EDITING;
        endColumn = startColumn + value.length()-1;
        return true;
    }

    @Override
    public Token split(Cursor c){
        int offset = c.getColumn() - startColumn;
        if (offset == 0){
            return null;
        }
        String leftSideVal = value.substring(0,offset);
        String rightSideVal = value.substring(offset);
        state = State.FINISHED;
        this.value = leftSideVal;
        endColumn = startColumn + value.length()-1;
        Token right = new Token(c);
        right.setState(State.FINISHED);
        right.update(rightSideVal);
        return  right;
    }

    protected void move(int x, int y) {
        startColumn+=x;
        endColumn+=x;
        startRow+=y;
        endRow+=y;
    }
    @Override
    public void update(String value) {
        this.value = value;
        endColumn = startColumn + value.length()-1;
    }

    public boolean pointedBy(Cursor c){
        return  startColumn <= c.getColumn() &&
                endColumn >= c.getColumn() &&
                startRow <= c.getRow() &&
                endRow >= c.getRow();
    }

    public boolean rightBefore(Cursor c){
        return startRow == c.getRow() && endColumn == c.getColumn()-1;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getEndColumn() {
        return endColumn;
    }

    public int getEndRow() {
        return endRow;
    }

    public String getValue(){ return value; }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public UUID getId() {
        return id;
    }





}
