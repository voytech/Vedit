package pl.voytech.vedit.core;

/**
 * Created by USER on 2016-11-06.
 */

public interface GenericReader<T,O>{
    interface ResultListener<O>{
        void hasResult(O result);
    }
    void read(T obj);
    void resultListener(ResultListener<O> resultListener);
}
