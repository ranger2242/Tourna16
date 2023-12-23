import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 11/10/2016.
 */
@SuppressWarnings("WeakerAccess")
class BinaryTree implements Serializable {
    private static char letter = 'A';
    static int teamc = 1;
    private BinaryNode root;

    public BinaryTree() {
        root = new BinaryNode();
    }

    public BinaryTree(BinaryNode b) {
        root = b;
    }

    public static boolean contains(BinaryNode testNode, BinaryNode targetNode) {
        if (testNode == null)
            return false;
        if (testNode == targetNode)
            return true;
        return contains(targetNode, testNode.left)
                || contains(targetNode, testNode.right);
    }

    public static void addBalancedLeaf(BinaryNode bt) {
        int a = getSize(bt.left);
        int b = getSize(bt.right);
        if (a + b == 0) {
            bt.setLeft(new BinaryNode());
        } else if (a + b == 1) {
            bt.setRight(new BinaryNode());
        } else {
            if (b < a) addBalancedLeaf(bt.right);
            else addBalancedLeaf(bt.left);
        }
    }

    public static void printLevel(BinaryNode b, int l) {
        if (b == null) {
            return;
        }
        if (l == 1)
            System.out.print(b.game.game);
        else if (l > 1) {
            printLevel(b.left, l - 1);
            printLevel(b.right, l - 1);
        }
    }

    public static void labelByLevel(BinaryNode b, int l) {
        if (b == null) {
            return;
        }
        if (l == 1) {
            if (teamc < Global.teamCount){
                String _a = "Team " + (teamc++);
                String _b= "Team " + (teamc++);
                Global.teamList.add(_a);
                Global.teamList.add(_b);
                b.game = (new Game(letter + "", _a,_b));
            }
            else{
                b.game = (new Game(letter + "", "", ""));

            }
            b.game.node=b;

            letter++;
        } else if (l > 1) {
            labelByLevel(b.left, l - 1);
            labelByLevel(b.right, l - 1);
        }
    }

    public static void breadthTraverse(BinaryNode root) {
        if (root == null)
            return;

        breadthTraverse(root.left);
        breadthTraverse(root.right);
    }

    public static int height(BinaryNode b) {
        if (b == null) {
            return 0;
        } else {
            int hr = height(b.left);
            int hl = height(b.right);
            if (hl > hr) return hl + 1;
            else return hr + 1;
        }
    }

    public static int getSize(BinaryNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + getSize(root.left) + getSize(root.right);
    }

    public static BinaryTree createWinnerBracket(int n) {
        BinaryTree tree = new BinaryTree();
        for (int i = 0; i < n; i++)
            BinaryTree.addBalancedLeaf(tree.getRoot());
        tree.insertRootLeft(new BinaryNode());
        tree.insertRootLeft(new BinaryNode());
        return tree;
    }
    public static BinaryTree createLoserBracket(int games){
        BinaryTree losersBracket = new BinaryTree();
        ArrayList<BinaryNode> nodes = new ArrayList<>();
        for(int i=0;i<30;i++){
            Game g = new Game(""+(i+1),"","");
            BinaryNode b = new BinaryNode(g);
            g.node=b;
            nodes.add(b);
        }
        nodes.get(21).setLeft(nodes.get(29));
        nodes.get(15).setLeft(nodes.get(22));
        nodes.get(18).setLeft(nodes.get(26));
        nodes.get(17).setLeft(nodes.get(25));
        nodes.get(20).setLeft(nodes.get(27));
        nodes.get(14).setLeft(nodes.get(23));
        nodes.get(19).setLeft(nodes.get(28));
        nodes.get(16).setLeft(nodes.get(24));
        nodes.get(13).setLeft(nodes.get(21));
        nodes.get(13).setRight(nodes.get(15));
        nodes.get(10).setLeft(nodes.get(18));
        nodes.get(10).setRight(nodes.get(17));
        nodes.get(12).setLeft(nodes.get(20));
        nodes.get(12).setRight(nodes.get(14));
        nodes.get(11).setLeft(nodes.get(19));
        nodes.get(11).setRight(nodes.get(16));
        nodes.get(9).setLeft(nodes.get(13));
        nodes.get(6).setLeft(nodes.get(10));
        nodes.get(8).setLeft(nodes.get(12));
        nodes.get(7).setLeft(nodes.get(11));
        nodes.get(5).setLeft(nodes.get(9));
        nodes.get(5).setRight(nodes.get(6));
        nodes.get(4).setLeft(nodes.get(8));
        nodes.get(4).setRight(nodes.get(7));
        nodes.get(2).setLeft(nodes.get(5));
        nodes.get(3).setLeft(nodes.get(4));
        nodes.get(1).setLeft(nodes.get(2));
        nodes.get(1).setRight(nodes.get(3));
        nodes.get(0).setLeft(nodes.get(1));
        losersBracket.setRoot(nodes.get(0));
        losersBracket=trim(losersBracket.getRoot(),games);
        return losersBracket;
    }
    static BinaryNode deleteGreaterThan(BinaryNode root, int n){
        if (root == null)
            return null;
        if( root.left !=null &&Integer.parseInt(root.left.game.game)>n ){
            root.setLeft(null);
        }if(root.right !=null && Integer.parseInt(root.right.game.game)>n){
            root.setRight(null);
        }
        return root;
    }
    public static BinaryTree trim(BinaryNode root,int n){
        if (root == null)
            return new BinaryTree();

        root.setLeft(deleteGreaterThan(root.left,n));
        trim(root.left,n);
        root.setRight(deleteGreaterThan(root.right,n));
        trim(root.right,n);
        return new BinaryTree(root);
    }
    public void setRoot(BinaryNode b) {
        root = b;
    }

    ArrayList<ArrayList<Game>> listByLevel(ArrayList<ArrayList<Game>> list, BinaryNode b, int i) {
        if (b.left != null) {
            listByLevel(list, b.left, i - 1);
        }
        if (b.right != null) {
            listByLevel(list, b.right, i - 1);
        }
        try {
            list.get(i - 1).add(b.game);
        } catch (IndexOutOfBoundsException e) {
        }
        b.game.round =i ;
        return list;
    }

    public void insertRootLeft(BinaryNode b) {
        BinaryNode b1 = root;
        root = b;
        root.setLeft(b1);
    }

    public BinaryNode getRoot() {
        return root;
    }

    public ArrayList<ArrayList<Game>> getListsByLevel() {
        /*
                L0   L1   L2    L3
                o |     |     |
                o |  o  |     |
                  |     |  o  | o
                o |  o  |     |
                o |     |     |
         */
        ArrayList<ArrayList<Game>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            list.add(new ArrayList<>());
        list = listByLevel(list, root, height(root));
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i).isEmpty()) {
                list.remove(i);
            }
        }
        return list;//
    }

    public ArrayList<Game> getGameList() {
        ArrayList<Game> out = new ArrayList<>();
        for (ArrayList<Game> arr : getListsByLevel()) {
            for (Game g : arr) {
                out.add(g);
            }
        }
        return out;
    }

    public BinaryNode contains(Game g, BinaryNode node) {
        if (node == null)
            return null;
        else if (node.game.equals(g))
            return node;
        else {
            contains(g, node.left);
            contains(g, node.right);
        }
        return null;
    }

    void labelWinnerBracket(BinaryNode b) {//
        int h = height(b);
        int i;
        for (i = h; i >= 1; i--)
            labelByLevel(b, i);
    }
}
