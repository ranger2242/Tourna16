package tree;

/**
 * Created by Chris Cavazos on 11/6/2016.
 */
public interface BinaryTree<T> extends Tree<T> {
    Position<T> left(Position<T> p) throws IllegalArgumentException;
    Position<T> right(Position<T> p) throws IllegalArgumentException;
    Position<T> sibling(Position<T> p) throws IllegalArgumentException;
}
