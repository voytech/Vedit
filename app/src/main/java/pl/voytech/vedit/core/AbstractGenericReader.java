package pl.voytech.vedit.core;

/**
 * Created by USER on 2016-11-06.
 */

public abstract class AbstractGenericReader<T,O> implements GenericReader<T,O> {
    private AbstractGenericReader<O,?> nextLevelReader;
    private ResultListener<O> listener;

    public AbstractGenericReader(){
        this.resultListener(new ResultListener<O>() {
            @Override
            public void hasResult(O result) {
                if (nextLevelReader!=null){
                    nextLevelReader.read(result);
                }
            }
        });
    }
    public void resultListener(ResultListener<O> resultListener){
        this.listener = resultListener;
    }
    public <N> void chain(AbstractGenericReader<O,N> next){
        nextLevelReader = next;
    }
    public void outputReady(O output){
        this.listener.hasResult(output);
    }
}
