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
    Game game;
    public BinaryNode left;
    public BinaryNode right;
    BinaryNode next;
    BinaryNode nextLoss;
    public boolean isLeft = false;
    final int index;

    public BinaryNode(int index) {
        this.left = null;
        this.right = null;
        this.index = index;
        this.game = new Game();
        this.game.node = this;

    }


    public void setLeft(BinaryNode n) {
        this.left = n;
        if (n == null) return;
        this.left.next = this;
        this.left.isLeft = true;
    }

    public void setRight(BinaryNode n) {
        this.right = n;
        if (n == null) return;
        this.right.next = this;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BinaryNode)) {
            return false;
        }
        BinaryNode otherTree = (BinaryNode) o;
        return equals(game, otherTree.game) && equals(left, otherTree.left) && equals(right, otherTree.right);
    }

    private boolean equals(Object node1, Object node2) {
        if (node1 == null) return node2 == null;
        return node1.equals(node2);
    }

    public String toString() {
        if (isLeaf()) {
            return game.toString();
        } else {
            String root,
                    left = "null",
                    right = "null";
            root = game.toString();
            if (this.left != null) {
                left = this.left.toString();
            }
            if (this.right != null) {
                right = this.right.toString();
            }
            return root + " (" + left + ", " + right + ")";
        }
    }

    public boolean hassLossNode() {
        return nextLoss != null;
    }
}