import javax.swing.*;
import java.io.Serializable;
import java.util.Date;


class Game implements Serializable {
    String time = "12:00:00";
    String location = "--";
    String team1;
    String team2;
    int index;
    Date date = new Date();
    String score1 = "0";
    String score2 = "0";
    BinaryNode node;
    GameSmallView view;

    Game() {
        team1 = "";
        team2 = "";
    }

    @Override
    public String toString() {
        return "Game" + index + " " + team1 + " " + team2;
    }


    JPanel getSmallGameModule() {
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
        // Global.print(node.next);
        if (node.next != null){
            Game nextGame = node.next.game;

            if (s1 > s2) {
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
        if(node.nextLoss != null){
            Game loss = node.nextLoss.game;
            if (s1 > s2) {
                if (loss != null)
                    if (loss.team1.isEmpty()) {
                        loss.team1 = team2;
                    } else {
                        loss.team2 = team2;
                    }
            } else {
                if (loss != null)

                    if (loss.team1.isEmpty()) {
                        loss.team1 = team1;
                    } else {
                        loss.team2 = team1;
                    }

            }

            if (loss != null) {
                loss.getSmallGameModule();
                loss.view.changeValues();
            }
        }
            Global.print(s1, s2);



        view.changeValues();
        Global.mainFrame.invalidate();
    }

}