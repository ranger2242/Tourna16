import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Chris Cavazos on 11/10/2016.
 */
@SuppressWarnings("WeakerAccess")
class BinaryTree {
    private static char letter='A';
    private BinaryNode root;
    protected ArrayList<String> lay = new ArrayList<>();
    ArrayList<ArrayList<Game>> list = new ArrayList<>();

    public BinaryTree(){
        root=new BinaryNode();
    }
    public BinaryTree(BinaryNode b){
        root=b;
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
    public static void printReverseLevelOrder(BinaryNode b){
        int h = height(b);
        int i;
        for(i=h;i>=1;i--)
            printLevel(b,i);
    }
    public static void printLevel(BinaryNode b, int l){
        if(b==null){
            return;
        }if(l==1)
            Main.outa(b.getValue().getGameNumber());
        else if(l >1){
            printLevel(b.getLeftChild(), l-1);
            printLevel(b.getRightChild(), l-1);
        }
    }
    public static void labelByLevel(BinaryNode b, int l){
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
    public void printBT(BinaryTree tree){
        tree.clearLay();
        tree.printByLevel(tree.getRoot(),1);
         BinaryTree.breadthTraverse(tree.getRoot());

        tree.printLay();

        Main.out("");
        for(int i=0;i<list.size();i++){
            Main.outa((list.size()-i)+":");
            for(int j=0;j<list.get(i).size();j++){
                Main.outa(list.get(i).get(j).getGameNumber());
            }
            Main.out("");
        }

    }
    public static void printPostOrder(BinaryNode b){
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
    public static boolean contains(BinaryNode testNode, BinaryNode targetNode) {
        if (testNode == null)
            return false;
        if (testNode == targetNode)
            return true;
        return contains(targetNode, testNode.getLeftChild())
                || contains(targetNode, testNode.getRightChild());
    }
    public static int height(BinaryNode b){
        if(b==null){
            return 0;
        }else{
            int hr= height(b.getLeftChild());
            int hl=height(b.getRightChild());
            if(hl>hr) return hl+1;
            else return hr+1;
        }
    }
    public static int getSize(BinaryNode root){
        if(root==null){
            return 0;
        }
        return 1 + getSize(root.getLeftChild()) + getSize(root.getRightChild());
    }
    public ArrayList<ArrayList<Game>> split(){
        list.clear();
        for(int i=0;i<10;i++){
            list.add(new ArrayList<Game>());
        }
        prepSplitByLevel(root,1);
        for(int i=list.size()-1;i>0;i--){
            if(list.get(i).isEmpty()){
                list.remove(i);
            }
        }
        Collections.reverse(list);
        return list;//
    }
    public static BinaryTree createWinnerBracket(int n){
        BinaryTree tree= new BinaryTree();
        for(int i=0;i<n;i++)
            BinaryTree.addBalancedLeaf(tree.getRoot());
        tree.insertRootLeft(new BinaryNode());
        tree.insertRootLeft(new BinaryNode());
        return tree;
    }
    void clearLay() {
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
    void labelWinnerBracket(BinaryNode b){//
        int h = height(b);
        int i;
        for(i=h;i>=1;i--)
            labelByLevel(b,i);
    }
    public void setRoot(BinaryNode b){
        root=b;
    }
    public void printByLevel(BinaryNode b, int i) {
        String s="";
        if (b.getLeftChild() != null) {
            printByLevel(b.getLeftChild(), i + 1);
        }else if(height(root)-1==i && b.getLeftChild()==null){
            s="-";
        }
        if (b.getRightChild() != null) {
            printByLevel(b.getRightChild(), i + 1);
        }else if(height(root)-1==i && b.getRightChild()==null){
            s="-";
        }
        try {
            String s2 = lay.get(i - 1);
            s2 += b.getValue().getGameNumber();
            list.get(i-1).add(b.getValue());
            lay.set(i - 1, s2);
        } catch (IndexOutOfBoundsException e) {
        }
        b.getValue().depth=i;
        //System.out.println("Depth:" + i);
        // b.getValue().printGame();

    }

    public void prepSplitByLevel(BinaryNode b, int i) {
        if (b.getLeftChild() != null) {
            prepSplitByLevel(b.getLeftChild(), i + 1);
        }
        if (b.getRightChild() != null) {
            prepSplitByLevel(b.getRightChild(), i + 1);
        }
        try {
            list.get(i - 1).add(b.getValue());
        } catch (IndexOutOfBoundsException e) {
        }
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
    public BinaryNode getRoot(){
        return root;
    }
}
