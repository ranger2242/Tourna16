import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 11/10/2016.
 */
@SuppressWarnings("WeakerAccess")
class BinaryTree implements Serializable{
    private static char letter='A';
    static int teamc=1;
    private BinaryNode root;

    public BinaryTree(){
        root=new BinaryNode();
    }
    public BinaryTree(BinaryNode b){
        root=b;
    }
    public static boolean contains(BinaryNode testNode, BinaryNode targetNode) {
        if (testNode == null)
            return false;
        if (testNode == targetNode)
            return true;
        return contains(targetNode, testNode.getLeftChild())
                || contains(targetNode, testNode.getRightChild());
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
            if(teamc<MainWindow.teamCount)
            b.setValue(new Game(letter + "", "t"+(teamc++), "t"+(teamc++)));
            else
                b.setValue(new Game(letter + "", "",""));

            letter++;
        }
        else if(l >1){
            labelByLevel(b.getLeftChild(), l-1);
            labelByLevel(b.getRightChild(), l-1);
        }
    }
    public static void breadthTraverse(BinaryNode root){
        if (root == null)
            return;

        //root.getValue().printGame();
        breadthTraverse(root.getLeftChild());
        breadthTraverse(root.getRightChild());
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
    public static BinaryTree createWinnerBracket(int n){
        BinaryTree tree= new BinaryTree();
        for(int i=0;i<n;i++)
            BinaryTree.addBalancedLeaf(tree.getRoot());
        tree.insertRootLeft(new BinaryNode());
        tree.insertRootLeft(new BinaryNode());
        return tree;
    }
    public void setRoot(BinaryNode b){
        root=b;
    }
    ArrayList<ArrayList<Game>> listByLevel(ArrayList<ArrayList<Game>> list, BinaryNode b, int i){
        if (b.getLeftChild() != null) {
            listByLevel(list,b.getLeftChild(), i - 1);
        }
        if (b.getRightChild() != null) {
            listByLevel(list,b.getRightChild(), i - 1);
        }
        try {
            list.get(i-1).add(b.getValue());
        } catch (IndexOutOfBoundsException e) {
        }
        b.getValue().setRound(i);
        return list;
    }
    public void printByLevel(BinaryNode b, int i) {
        if (b.getLeftChild() != null) {
            printByLevel(b.getLeftChild(), i + 1);
        }
        if (b.getRightChild() != null) {
            printByLevel(b.getRightChild(), i + 1);
        }
    }
    public void printBT(BinaryTree tree){
        tree.printByLevel(tree.getRoot(),1);
        BinaryTree.breadthTraverse(tree.getRoot());


        Main.out("");
        ArrayList<ArrayList<Game>> list = split();
        for(int i=0;i<list.size();i++){
            Main.outa((list.size()-i)+":");
            for(int j=0;j<list.get(i).size();j++){
                Main.outa(list.get(i).get(j).getGameNumber());
            }
            Main.out("");
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
    public ArrayList<ArrayList<Game>> split(){
        ArrayList<ArrayList<Game>> list = new ArrayList<>();
        for(int i=0;i<10;i++)
            list.add(new ArrayList<Game>());
        list=listByLevel(list,root,height(root));
        for(int i=list.size()-1;i>0;i--){
            if(list.get(i).isEmpty()){
                list.remove(i);
            }
        }
        //Collections.reverse(list);
        return list;//
    }
    public ArrayList<Game> getGameList(){
        ArrayList<Game> out=new ArrayList<>();
        for(ArrayList<Game> arr: split()){
            for(Game g: arr){
                out.add(g);
            }
        }
        return out;
    }
    public BinaryNode contains(Game g, BinaryNode node) {
        if (node == null)
            return null;
        else if (node.getValue().equals(g))
            return node;
        else {
            contains(g, node.getLeftChild());
            contains(g, node.getRightChild());
        }
        return null;
    }

    void labelWinnerBracket(BinaryNode b){//
        int h = height(b);
        int i;
        for(i=h;i>=1;i--)
            labelByLevel(b,i);
    }
}
