/**
 * A BinaryTree consists of "nodes"--each "node" is itself a BinaryTree.
 * Each node has a parent (unless it is the root), may have a left child,
 * and may have a right child. This class implements loop-free binary trees,
 * allowing shared subtrees.
 *
 * @author David Matuszek
 * @version Jan 25, 2004
 */
@SuppressWarnings("WeakerAccess")
public class BinaryNode {
    private Game value;
    private BinaryNode leftChild;
    private BinaryNode rightChild;

    public BinaryNode(Game value) {
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }
    public BinaryNode(){
        this(null);
    }
    public boolean isFull() {
        return leftChild != null && rightChild != null;
    }
    public Game getValue() {
        return value;
    }
    public BinaryNode getLeftChild() {
        return leftChild;
    }
    public BinaryNode getRightChild() {
        return rightChild;
    }
    public void setLeftChild(BinaryNode subtree) throws IllegalArgumentException {
        if (BinaryTree.contains(subtree, this)) {
            throw new IllegalArgumentException(
                    "Subtree " + this + " already contains " + subtree);
        }
        leftChild = subtree;
    }
    public void setRightChild(BinaryNode subtree) throws IllegalArgumentException {
        if (BinaryTree.contains(subtree, this)) {
            throw new IllegalArgumentException(
                    "Subtree " + this + " already contains " + subtree);
        }
        rightChild = subtree;
    }
    public void setValue(Game value) {
        this.value = value;
    }
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }
    public boolean equals(Object o) {
        if (o == null || !(o instanceof BinaryNode)) {
            return false;
        }
        BinaryNode otherTree = (BinaryNode) o;
        return equals(value, otherTree.value)
                && equals(leftChild, otherTree.getLeftChild())
                && equals(rightChild, otherTree.getRightChild());
    }
    private boolean equals(Object x, Object y) {
        if (x == null) return y == null;
        return x.equals(y);
    }
    public String toString() {
        if (isLeaf()) {
            return value.toString();
        } else {
            String root, left = "null", right = "null";
            root = value.toString();
            if (getLeftChild() != null) {
                left = getLeftChild().toString();
            }
            if (getRightChild() != null) {
                right = getRightChild().toString();
            }
            return root + " (" + left + ", " + right + ")";
        }
    }
    public int hashCode() {
        int result = value.hashCode();
        if (leftChild != null) {
            result += 3 * leftChild.hashCode();
        }
        if (rightChild != null) {
            result += 7 * leftChild.hashCode();
        }
        return result;
    }
}