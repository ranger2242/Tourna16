import java.util.ArrayList;

/**
 * Created by Chris Cavazos on 11/15/2016.
 */
public class Bracket {//why isnt this working
    public static BinaryTree createLoserBracket(int games){
        BinaryTree losersBracket = new BinaryTree();
        ArrayList<BinaryNode> nodes = new ArrayList<>();
        for(int i=0;i<30;i++){
            nodes.add(new BinaryNode(new Game(""+(i+1),"","")));
        }
        nodes.get(21).setLeftChild(nodes.get(29));
        nodes.get(15).setLeftChild(nodes.get(22));
        nodes.get(18).setLeftChild(nodes.get(26));
        nodes.get(17).setLeftChild(nodes.get(25));
        nodes.get(20).setLeftChild(nodes.get(27));
        nodes.get(14).setLeftChild(nodes.get(23));
        nodes.get(19).setLeftChild(nodes.get(28));
        nodes.get(16).setLeftChild(nodes.get(24));
        nodes.get(13).setLeftChild(nodes.get(21));
        nodes.get(13).setRightChild(nodes.get(15));
        nodes.get(10).setLeftChild(nodes.get(18));
        nodes.get(10).setRightChild(nodes.get(17));
        nodes.get(12).setLeftChild(nodes.get(20));
        nodes.get(12).setRightChild(nodes.get(14));
        nodes.get(11).setLeftChild(nodes.get(19));
        nodes.get(11).setRightChild(nodes.get(16));
        nodes.get(9).setLeftChild(nodes.get(13));
        nodes.get(6).setLeftChild(nodes.get(10));
        nodes.get(8).setLeftChild(nodes.get(12));
        nodes.get(7).setLeftChild(nodes.get(11));
        nodes.get(5).setLeftChild(nodes.get(9));
        nodes.get(5).setRightChild(nodes.get(6));
        nodes.get(4).setLeftChild(nodes.get(8));
        nodes.get(4).setRightChild(nodes.get(7));
        nodes.get(2).setLeftChild(nodes.get(5));
        nodes.get(3).setLeftChild(nodes.get(4));
        nodes.get(1).setLeftChild(nodes.get(2));
        nodes.get(1).setRightChild(nodes.get(3));
        nodes.get(0).setLeftChild(nodes.get(1));
        losersBracket.setRoot(nodes.get(0));
        losersBracket=trim(losersBracket.getRoot(),games);
        return losersBracket;
    }
    public static BinaryTree trim(BinaryNode root,int n){
        if (root == null)
            return new BinaryTree();

        root.setLeftChild(deleteGreaterThan(root.getLeftChild(),n));
        trim(root.getLeftChild(),n);
        root.setRightChild(deleteGreaterThan(root.getRightChild(),n));
        trim(root.getRightChild(),n);
        return new BinaryTree(root);
    }
    public static BinaryTree trimLosersBracketGames(BinaryTree b, int n){
        if (b.getRoot() == null)
            return new BinaryTree();
        b.getRoot().setLeftChild(deleteGreaterThan(b.getRoot().getLeftChild(),n));
        b.getRoot().setRightChild(deleteGreaterThan(b.getRoot().getRightChild(),n));
        return b;
    }
     static BinaryNode deleteGreaterThan(BinaryNode root, int n){
        if (root == null)
            return null;
        if( root.getLeftChild() !=null &&Integer.parseInt(root.getLeftChild().getValue().getGameNumber())>n ){
            root.setLeftChild(null);
        }if(root.getRightChild() !=null && Integer.parseInt(root.getRightChild().getValue().getGameNumber())>n){
             root.setRightChild(null);
         }
        return root;
    }

}
