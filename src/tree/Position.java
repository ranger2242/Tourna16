package tree;

/**
 * Created by Chris Cavazos on 11/6/2016.
 */
public abstract class Position<T>{
    private T value=null;

    public Position(T v) {
        value=v;
    }

    public Position() {

    }

    public T getElement(){
        return value;
    }

}
