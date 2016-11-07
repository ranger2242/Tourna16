package tree;

/**
 * Created by Chris Cavazos on 11/6/2016.
 */
public abstract class AbstractTree<T> implements Tree<T> {
    public boolean isInternal(Position<T> p){return numChildren(p)>0;}
    public boolean isExternal(Position<T> p){return numChildren(p)==0;}
    public boolean isRoot(Position<T> p){return p==root();}
    public boolean isEmpty(){return size()==0;}
    public int height(Position<T> p){
        int h = 0;
        for(Position<T> c: children(p)){
            h= Math.max(h,1+height(c));
        }
        return h;
    }
}
