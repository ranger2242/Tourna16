import java.io.Serializable;

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
public class BinaryNode implements Serializable {
    public Game value;
    public BinaryNode left;
    public BinaryNode right;

    public BinaryNode(Game value) {
        this.left = null;
        this.right = null;
        this.value =(value);
    }

    public BinaryNode() {
        this(null);
    }



    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof BinaryNode)) {
            return false;
        }
        BinaryNode otherTree = (BinaryNode) o;
        return equals(value, otherTree.value)
                && equals(left, otherTree.left)
                && equals(right, otherTree.right);
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
            if (left != null) {
                left = left.toString();
            }
            if (right != null) {
                right = right.toString();
            }
            return root + " (" + left + ", " + right + ")";
        }
    }
}