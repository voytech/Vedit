package pl.voytech.vedit.core;

/**
 * Created by USER on 2016-11-01.
 */

public interface TokenApi {
    void insert(char character, Cursor c);
    boolean removeLeft(Cursor c);
    boolean removeRight(Cursor c);

    Token split(Cursor c);

    void update(String value);
}
