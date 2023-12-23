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
    public Game game;
    public BinaryNode left;
    public BinaryNode right;
    BinaryNode next;
    public boolean isLeft = false;

    public BinaryNode(Game value) {
        this.left = null;
        this.right = null;
        this.game = (value);
        if (this.game != null) {
            this.game.node = this;
        }
    }

    public BinaryNode() {
        this(null);
    }

    public void setLeft(BinaryNode n) {
        this.left = n;
        if (n == null)
            return;
        this.left.next = this;
        this.left.isLeft=true;
    }

    public void setRight(BinaryNode n) {
        this.right = n;
        if (n == null)
            return;
        this.right.next = this;
    }

    public boolean compareLeftGame(Game g) {
        if (next.left != null) {
            if (next.left.game != null) {
                return next.left.game == g;
            }
        }
        return false;
    }

    public boolean compareRightGame(Game g) {
        if (next.right != null) {
            if (next.right.game != null) {
                return next.right.game == g;
            }
        }
        return false;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean equals(Object o) {
        if (o == null || !(o instanceof BinaryNode)) {
            return false;
        }
        BinaryNode otherTree = (BinaryNode) o;
        return equals(game, otherTree.game)
                && equals(left, otherTree.left)
                && equals(right, otherTree.right);
    }

    private boolean equals(Object x, Object y) {
        if (x == null) return y == null;
        return x.equals(y);
    }

    public String toString() {
        if (isLeaf()) {
            return game.toString();
        } else {
            String root, left = "null", right = "null";
            root = game.toString();
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