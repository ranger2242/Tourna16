import javax.swing.*;
import java.io.Serializable;
import java.util.Date;


class Game implements Serializable {
    String time = "12:00:00";
    String location = "--";
    Team team1;
    Team team2;
    int index;
    Date date = new Date();
    String score1 = "0";
    String score2 = "0";
    BinaryNode node;
    GameSmallView view;

    Game() {
    }

    @Override
    public String toString() {
        return "Game" + index + " " + team1 + " " + team2;
    }


    GameSmallView getSmallGameModule() {
        if (view == null) {
            view = new GameSmallView(this);
        }
        return view;
    }

    public void applyResult() {
        int s1 = 0;
        int s2 = 0;
        try {
            s1 = Integer.parseInt(score1);
            s2 = Integer.parseInt(score2);
        } catch (Exception e) {
            Global.error("Invalid Scores", "Scores must be integer");
        }
        boolean t1Wins = s1 > s2;
        if (node.next != null) {
            Game nextGame = node.next.game;

            if (t1Wins) {
                if (node.isLeft) {
                    nextGame.team1 = team1;
                } else {
                    nextGame.team2 = team1;
                }
            } else {
                if (node.isLeft) {
                    nextGame.team1 = team2;
                } else {
                    nextGame.team2 = team2;
                }
            }
            nextGame.view.changeValues();

        }
        if (node.hassLossNode()) {
            Game loss = node.nextLoss.game;
            String l1 = loss.teamName(1);
            if (l1.equals("L" + index)) {
                if (t1Wins) {
                    loss.team1 = team2;
                } else {
                    loss.team1 = team1;
                }
            } else {
                if (t1Wins) {
                    loss.team2 = team2;
                } else {
                    loss.team2 = team1;
                }
            }

            loss.getSmallGameModule();
            loss.view.changeValues();

        }
        Global.print(s1, s2);


        view.changeValues();
        Global.mainFrame.invalidate();
    }

    public boolean hasTeam1() {
        return team1 != null;
    }

    public boolean hasTeam2() {
        return team2 != null;

    }

    public boolean teamSpotOpen(int t) {
        if (t == 1) return !this.hasTeam1() && this.node.right == null;
        return !this.hasTeam2() && this.node.left == null;

    }

    public String teamName(int t) {
        if (t == 1 && hasTeam1()) {
            return team1.name;
        } else if (t == 2 && hasTeam2()) {
            return team2.name;

        }
        return "";

    }


}