import javax.swing.*;
import java.io.Serializable;
import java.util.Date;

class Game implements Serializable {
    String time = "12:00:00";
    String location = "--";
    String team1;
    String team2;
    String game;
    Date date = new Date();
    String score1 = "0";
    String score2 = "0";
    int round = 0;

    Game(String g, String teamA, String teamB) {
        team1 = teamA;
        team2 = teamB;
        game = g;
    }

    @Override
    public String toString() {
        return "Game" + game + " " + team1 + " " + team2;
    }

    void printGame() {
        System.out.println("\nRound " + round);
        System.out.println(this);
    }

    public static Game getDummyGame() {
        Game dummy = new Game("A","team1","team2");
        dummy.location  = "6030 N FM 493";
        dummy.score1 = "1";
        dummy.score2 = "2";
        dummy.date =new Date();
        dummy.time = "12:00:00 am";
        return dummy;
    }

    JPanel getSmallGameModule() {
        return new GameSmallView(this);
    }

}