import java.util.ArrayList;

public class Bracket {
    BinaryTree winners;
    BinaryTree losers;

    public Bracket(int n) {
        winners = BinaryTree.createWinnerBracket();
        losers = BinaryTree.createLoserBracket();
        winners = trim(winners.getRoot(), n - 2);
        losers = trim(losers.getRoot(), n - 2);
        labelGames();
        connectTrees();
        createTeams(n);
        placeTeams();
    }

    private void labelGames() {
        ArrayList<ArrayList<Game>> ws = winners.getListsByLevel();
        ArrayList<ArrayList<Game>> ls = losers.getListsByLevel();
        int cnt = 1;
        int n = Math.max(ws.size(), ls.size());
        for (int l = 0; l < n; l++) {
            ArrayList<Game> wLevel = new ArrayList<>();
            ArrayList<Game> lLevel = new ArrayList<>();

            if (l < ws.size())
                wLevel = ws.get(l);
            if (l < ls.size())
                lLevel = ls.get(l);
            for (Game game : wLevel) {
                game.index = cnt++;
            }
            for (Game game : lLevel) {
                game.index = cnt++;
            }
        }


    }

    void createTeams(int n) {
        for (int i = 1; i <= n; i++) {
            Team t = new Team("Team " + i);
            Global.teamList.add(t);
        }
    }

    void placeTeams() {
        ArrayList<ArrayList<Game>> ws = winners.getListsByLevel();

        int cnt = 0;
        for (ArrayList<Game> wLevel : ws) {
            for (Game g : wLevel) {
                if (g.teamSpotOpen(1) && cnt < Global.teamCount) {
                    g.team1 = Global.teamList.get(cnt++);
                }
                if (g.teamSpotOpen(2) && cnt < Global.teamCount) {
                    g.team2 = Global.teamList.get(cnt++);
                }
            }
        }
    }

    void connectTrees() {
        ArrayList<ArrayList<Game>> ls = losers.getListsByLevel();
        ArrayList<Game> wsl = winners.asList();
        for (int level = 0; level < ls.size(); level++) {
            ArrayList<Game> lLevel = ls.get(level);
            for (int gi = 0; gi < lLevel.size(); gi++) {
                boolean flip = level % 2 == 1;
                Game g;
                if (flip)
                    g = lLevel.get(lLevel.size() - 1 - gi);
                else
                    g = lLevel.get(gi);
                if (!g.hasTeam1() && !wsl.isEmpty() && g.node.right == null) {
                    Game wg = wsl.remove(0);
                    g.team1 = new Team("L" + wg.index);
                    wg.node.nextLoss = g.node;
                }
                if (!g.hasTeam2() && !wsl.isEmpty() && g.node.left == null) {
                    Game wg = wsl.remove(0);
                    g.team2 = new Team("L" + wg.index);
                    wg.node.nextLoss = g.node;
                }
            }
        }
    }

    static BinaryTree trim(BinaryNode root, int n) {
        if (root == null) return new BinaryTree();

        root.setLeft(deleteGreaterThan(root.left, n));
        trim(root.left, n);
        root.setRight(deleteGreaterThan(root.right, n));
        trim(root.right, n);
        return new BinaryTree(root);
    }

    static BinaryNode deleteGreaterThan(BinaryNode root, int n) {
        if (root == null) return null;
        if (root.left != null && root.left.index >= n) {
            root.setLeft(null);
        }
        if (root.right != null && root.right.index >= n) {
            root.setRight(null);
        }
        return root;
    }
}
