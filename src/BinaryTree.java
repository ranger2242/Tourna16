import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chris Cavazos on 11/10/2016.
 */
class BinaryTree {
    private static char letter='A';
    private BinaryNode root;
    private static ArrayList<ArrayList<Game>> list = new ArrayList<>();

    public BinaryTree(){
        root=new BinaryNode();
    }
    public BinaryTree(BinaryNode b){
        root=b;
    }
    BinaryNode getRoot(){
        return root;
    }
    public void setRoot(BinaryNode b){
        root=b;
    }
    public static int getSize(BinaryNode root){
        if(root==null){
            return 0;
        }
        return 1 + getSize(root.getLeftChild()) + getSize(root.getRightChild());
    }
    public static void addBalancedLeaf(BinaryNode bt) {
        int a=getSize(bt.getLeftChild());
        int b=getSize(bt.getRightChild());
        if(a+b==0){
            bt.setLeftChild(new BinaryNode());
        }else if(a+b==1){
            bt.setRightChild(new BinaryNode());
        }else{
            if(b<a) addBalancedLeaf(bt.getRightChild());
            else addBalancedLeaf(bt.getLeftChild());
        }
    }
    static boolean contains(BinaryNode tree, BinaryNode targetNode) {
        if (tree == null)
            return false;
        if (tree == targetNode)
            return true;
        return contains(targetNode, tree.getLeftChild())
                || contains(targetNode, tree.getRightChild());
    }
    private ArrayList<String> lay = new ArrayList<>();
    void clearLay() {
        list.clear();
        for(int i=0;i<10;i++){
            list.add(new ArrayList<>());
        }
        lay.clear();
        for (int i = 0; i < 10; i++) {
            lay.add("");
        }
    }
    static void printReverseLevelOrder(BinaryNode b){
        int h = height(b);
        int i;
        for(i=h;i>=1;i--)
            printLevel(b,i);
    }
    static void printLevel(BinaryNode b, int l){
        if(b==null){
            return;
        }if(l==1)
            Main.outa(b.getValue().getGameNumber());
        else if(l >1){
            printLevel(b.getLeftChild(), l-1);
            printLevel(b.getRightChild(), l-1);
        }
    }
    static void labelByLevel(BinaryNode b, int l){
        if(b==null){
            return;
        }if(l==1) {
            b.setValue(new Game(letter + "", "", ""));
            letter++;
        }
        else if(l >1){
            labelByLevel(b.getLeftChild(), l-1);
            labelByLevel(b.getRightChild(), l-1);
        }
    }
    static int height(BinaryNode b){
        if(b==null){
            return 0;
        }else{
            int hr= height(b.getLeftChild());
            int hl=height(b.getRightChild());
            if(hl>hr) return hl+1;
            else return hr+1;
        }
    }
    void printLay() {
        System.out.println("-----------------");
        for (String s : lay)
            System.out.println(s);
        System.out.println("-----------------");

    }
    void labelWinnerBracket(BinaryNode b){//
        int h = height(b);
        int i;
        for(i=h;i>=1;i--)
            labelByLevel(b,i);
    }
    static void printPostOrder(BinaryNode b){
        printPostOrder(b.getLeftChild());
        printPostOrder(b.getRightChild());
        b.setValue(new Game(letter+"","",""));
        letter++;
    }
    public static void breadthTraverse(BinaryNode root){
        if (root == null)
            return;

        root.getValue().printGame();
        breadthTraverse(root.getLeftChild());
        breadthTraverse(root.getRightChild());
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
            s += b.getValue().getGameNumber();
            list.get(i-1).add(b.getValue());
            lay.set(i - 1, s);
        } catch (IndexOutOfBoundsException e) {
        }
        b.getValue().depth=i;
        //System.out.println("Depth:" + i);
        // b.getValue().printGame();

    }
    public void insertRootRight(BinaryNode b){
        BinaryNode b1= root;
        root=b;
        root.setRightChild(b1);
    }
    public void insertRootLeft(BinaryNode b){
        BinaryNode b1= root;
        root=b;
        root.setLeftChild(b1);
    }
    public static ArrayList<ArrayList<Game>> split(){
        for(int i=list.size()-1;i>0;i--){
            if(list.get(i).isEmpty()){
                list.remove(i);
            }
        }
        Collections.reverse(list);
        return list;
    }
    static BinaryTree makeBracket(int n){
        BinaryTree tree= new BinaryTree();
        for(int i=0;i<n;i++)
            BinaryTree.addBalancedLeaf(tree.getRoot());
        return tree;
    }
    static void printBT(BinaryTree tree){
        tree.clearLay();
        BinaryTree.breadthTraverse(tree.getRoot());
        tree.printByLevel(tree.getRoot(),1);
        tree.printLay();
        Main.out("");
        ArrayList<ArrayList<Game>> list=BinaryTree.split();
        for(int i=0;i<list.size();i++){
            Main.outa((list.size()-i)+":");
            for(int j=0;j<list.get(i).size();j++){
                Main.outa(list.get(i).get(j).getGameNumber());
            }
            Main.out("");
        }
    }
}
