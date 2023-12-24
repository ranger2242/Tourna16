import java.util.ArrayList;
import java.util.Collections;

public class Bracket {
    BinaryTree winners;
    BinaryTree losers;

    public Bracket(int nTeams) {
        winners = BinaryTree.createWinnerBracket(nTeams - 2);
        losers = BinaryTree.createLoserBracket(nTeams - 2);
        //winners.labelWinnerBracket(winners.getRoot());
        // losers.labelWinnerBracket(losers.getRoot());
        ArrayList<ArrayList<Game>> ws = winners.getListsByLevel();
        ArrayList<ArrayList<Game>> ls = losers.getListsByLevel();
        //for(int j=0;j<2;j++)

        ArrayList<Game> wl0 = ws.get(0);
        Global.print(ws);
        ArrayList<Game> ll0 = ls.get(0);
        for (int i = 0; i < 8; i++) {
            Game g = ll0.get(i);
            wl0.get(i).node.nextLoss = g.node;
            wl0.get(i + 8).node.nextLoss = g.node;
        }
        ArrayList<Game> wl1 = ws.get(1);
        Collections.reverse(wl1);
        ArrayList<Game> ll1 = ls.get(1);
        for (int i = 0; i < 8; i++) {
            Game g = ll1.get(i);
            wl1.get(i).node.nextLoss = g.node;
        }
        ArrayList<Game> wl2 = ws.get(2);
        ArrayList<Game> ll3 = ls.get(4);
        for (int i = 0; i < 2; i++) {
            Game g = ll3.get(i);
            wl2.get(i).node.nextLoss = g.node;
            wl2.get(i + 1).node.nextLoss = g.node;
        }
        ArrayList<Game> wl3 = ws.get(3);
        Collections.reverse(wl3);
        ArrayList<Game> ll5 = ls.get(5);
        for (int i = 0; i < 2; i++) {
            Game g = ll5.get(i);
            wl3.get(i).node.nextLoss = g.node;
        }
        ws.get(4).get(0).node.nextLoss = ls.get(7).get(0).node;
        winners = trim(winners.getRoot(), nTeams - 2);
        losers = trim(losers.getRoot(), nTeams - 2);
        ws = winners.getListsByLevel();
        int cnt = 1;
        for (int i = 0; i < ws.get(0).size(); i++) {
            Game g = ws.get(0).get(i);
            g.team1 = "Team " + (cnt++);
            g.team2 = "Team " + (cnt++);
            Global.print(g, g.node.nextLoss);
        }
        int q = 0;
        while (cnt <= nTeams) {
            Game g = ws.get(1).get(q++);
            if (g.node.left != null) {
                g.team1 = "Team " + (cnt++);
                continue;
            }
            if (g.node.right != null) {
                g.team2 = "Team " + (cnt++);
                continue;
            }
            g.team1 = "Team " + (cnt++);
            if (cnt <= nTeams) break;
            g.team2 = "Team " + (cnt++);
        }


    }

    public static BinaryTree trim(BinaryNode root, int n) {
        if (root == null) return new BinaryTree();

        root.setLeft(deleteGreaterThan(root.left, n));
        trim(root.left, n);
        root.setRight(deleteGreaterThan(root.right, n));
        trim(root.right, n);
        return new BinaryTree(root);
    }

    static BinaryNode deleteGreaterThan(BinaryNode root, int n) {
        if (root == null) return null;
        if (root.left != null && root.left.game.index > n) {
            root.setLeft(null);
        }
        if (root.right != null && root.right.game.index > n) {
            root.setRight(null);
        }
        return root;
    }
}
