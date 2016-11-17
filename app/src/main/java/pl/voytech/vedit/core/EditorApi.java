package pl.voytech.vedit.core;

/**
 * Created by USER on 2016-11-03.
 */

public interface EditorApi {
    //Current cursor context actions.
    void insert(char character);
    boolean removeLeft();
    boolean removeRight();
    boolean merge();
    boolean split();

    void end();
    //explicit token actions.
    void end(Token token);
    void update(Token token, String value);
    void move(Token token, int colOffs, int rowOffs);
    boolean merge(Token t1, Token t2);
    boolean split(Token token, char on);
    boolean split(Token token, int index);

}
