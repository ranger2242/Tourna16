import java.util.ArrayList;

/**
 * A BinaryTree consists of "nodes"--each "node" is itself a BinaryTree.
 * Each node has a parent (unless it is the root), may have a left child,
 * and may have a right child. This class implements loop-free binary trees,
 * allowing shared subtrees.
 *
 * @author David Matuszek
 * @version Jan 25, 2004
 */
public class BinaryNode {
    /**
     * The value (data) in this node of the binary tree; may be of
     * any object type.
     */
    static boolean flipper = false;
    public Game value;
   // private BinaryNode root;

    private BinaryNode leftChild;
    private BinaryNode rightChild;

    /**
     * Constructor for BinaryTree.
     *
     * @param value      The value to be placed in the root.
     * @param leftChild  The left child of the root (may be null).
     * @param rightChild The right child of the root (may be null).
     */
    public BinaryNode(Game value, BinaryNode leftChild, BinaryNode rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Constructor for a BinaryTree leaf node (that is, with no children).
     *
     * @param value The value to be placed in the root.
     */
    public BinaryNode(Game value) {
        this(value, null, null);
    }
    public int getSize(BinaryNode root){
        if(root==null){
            return 0;
        }
        return 1 + getSize(root.getLeftChild()) + getSize(root.getRightChild());
    }

    public boolean isFull() {
        return leftChild != null && rightChild != null;
    }

    /**
     * Getter method for the value in this BinaryTree node.
     *
     * @return The value in this node.
     */
    public Game getValue() {
        return value;
    }

    /**
     * Getter method for left child of this BinaryTree node.
     *
     * @return The left child (<code>null</code> if no left child).
     */
    public BinaryNode getLeftChild() {
        return leftChild;
    }

    /**
     * Getter method for right child of this BinaryTree node.
     *
     * @return The right child (<code>null</code> if no right child).
     */
    public BinaryNode getRightChild() {
        return rightChild;
    }

    /**
     * Sets the left child of this BinaryTree node to be the
     * given subtree. If the node previously had a left child,
     * it is discarded. Throws an <code>IllegalArgumentException</code>
     * if the operation would cause a loop in the binary tree.
     *
     * @param subtree The node to be added as the new left child.
     * @throws IllegalArgumentException If the operation would cause
     *                                  a loop in the binary tree.
     */
    public void setLeftChild(BinaryNode subtree) throws IllegalArgumentException {
        if (contains(subtree, this)) {
            throw new IllegalArgumentException(
                    "Subtree " + this + " already contains " + subtree);
        }
        leftChild = subtree;
    }

    /**
     * Sets the right child of this BinaryTree node to be the
     * given subtree. If the node previously had a right child,
     * it is discarded. Throws an <code>IllegalArgumentException</code>
     * if the operation would cause a loop in the binary tree.
     *
     * @param subtree The node to be added as the new right child.
     * @throws IllegalArgumentException If the operation would cause
     *                                  a loop in the binary tree.
     */
    public void setRightChild(BinaryNode subtree) throws IllegalArgumentException {
        if (contains(subtree, this)) {
            throw new IllegalArgumentException(
                    "Subtree " + this + " already contains " + subtree);
        }
        rightChild = subtree;
    }

    public void add(BinaryNode bt, Game g) {
        int a=getSize(bt.leftChild);
        int b=getSize(bt.rightChild);
        if(a+b==0){
            bt.setLeftChild(new BinaryNode(g));
        }else if(a+b==1){
            bt.setRightChild(new BinaryNode(g));
        }else{
            if(b<a)add(bt.getRightChild(),g);
            else add(bt.getLeftChild(),g);
        }
    }

    /**
     * Sets the value in this BinaryTree node.
     *
     * @param value The new value.
     */
    public void setValue(Game value) {
        this.value = value;
    }

    /**
     * Tests whether this node is a leaf node.
     *
     * @return <code>true</code> if this BinaryTree node has no children.
     */
    public boolean isLeaf() {
        return leftChild == null && rightChild == null;
    }

    /**
     * Tests whether this BinaryTree is equal to the given object.
     * To be considered equal, the object must be a BinaryTree,
     * and the two binary trees must have equal values in their
     * roots, equal left subtrees, and equal right subtrees.
     *
     * @return <code>true</code> if the binary trees are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o == null || !(o instanceof BinaryNode)) {
            return false;
        }
        BinaryNode otherTree = (BinaryNode) o;
        return equals(value, otherTree.value)
                && equals(leftChild, otherTree.getLeftChild())
                && equals(rightChild, otherTree.getRightChild());
    }

    /**
     * Tests whether its two arguments are equal.
     * This method simply checks for <code>null</code> before
     * calling <code>equals(Object)</code> so as to avoid possible
     * <code>NullPointerException</code>s.
     *
     * @param x The first object to be tested.
     * @param y The second object to be tested.
     * @return <code>true</code> if the two objects are equal.
     */
    private boolean equals(Object x, Object y) {
        if (x == null) return y == null;
        return x.equals(y);
    }

    /**
     * Tests whether the <code>tree</code> argument contains within
     * itself the <code>targetNode</code> argument.
     *
     * @param tree       The root of the binary tree to search.
     * @param targetNode The node to be searched for.
     * @return <code>true</code> if the <code>targetNode</code> argument can
     * be found within the binary tree rooted at <code>tree</code>.
     */
    protected boolean contains(BinaryNode tree, BinaryNode targetNode) {
        if (tree == null)
            return false;
        if (tree == targetNode)
            return true;
        return contains(targetNode, tree.getLeftChild())
                || contains(targetNode, tree.getRightChild());
    }

    /**
     * Returns a String representation of this BinaryTree.
     *
     * @return A String representation of this BinaryTree.
     * @see java.lang.Object#toString()
     */
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

    /**
     * Computes a hash code for the complete binary tree rooted
     * at this BinaryTree node.
     *
     * @return A hash code for the binary tree with this root.
     * @see java.lang.Object#hashCode()
     */
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

    /**
     * Prints the binary tree rooted at this BinaryTree node.
     */
    public void print() {
        print(this, 0);
    }

    private void print(BinaryNode root, int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("   ");
        }
        if (root == null) {
            System.out.println("null");
            return;
        }
        System.out.println(root.value);
        if (root.isLeaf()) return;
        print(root.leftChild, indent + 1);
        print(root.rightChild, indent + 1);
    }

    ArrayList<String> lay = new ArrayList<>();

    void store() {
        lay.clear();
        for (int i = 0; i < 10; i++) {
            lay.add("");
        }
    }

    void printLay() {
        System.out.println("-----------------");
        for (String s : lay)
            System.out.println(s);
        System.out.println("-----------------");

    }
    public void breadth(BinaryNode root){
        if (root == null)
            return;

        root.getValue().printGame();
        breadth(root.getLeftChild());
        breadth(root.getRightChild());
    }

    public void printByLevel(BinaryNode b, int i) {
        if (b.getLeftChild() != null) {
            printByLevel(b.getLeftChild(), i + 1);
        }
        if (b.getRightChild() != null) {
            printByLevel(b.getRightChild(), i + 1);
        }
        try {
            String s = lay.get(i - 1);
            s += "o";
            lay.set(i - 1, s);
        } catch (IndexOutOfBoundsException e) {
        }
        b.getValue().depth=i;
        //System.out.println("Depth:" + i);
       // b.getValue().printGame();

    }
}