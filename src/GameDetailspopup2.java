import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by range_000 on 11/26/2016.
 */
public class GameDetailsPopup2 {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    Game game ;
    JLabel home = new JLabel("Home");
    JLabel away = new JLabel("Away");
    JLabel team1 = new JLabel();
    JLabel team2 = new JLabel();
    JLabel gameNumber = new JLabel();
    JLabel datetime = new JLabel();
    JLabel score1 = new JLabel();
    JLabel score2 = new JLabel();
    JLabel location = new JLabel();
    JCheckBox homecheck = new JCheckBox();
    JCheckBox awaycheck = new JCheckBox();

    public GameDetailsPopup2(Game game){
        this.game = game.getDummyGame();
        onStart();
    }
     void onStart(){
         initalizeFeilds();
         buildPannel();
    }
    void initalizeFeilds(){
        gameNumber.setText(game.game);
        team1.setText(game.team1);
        team2.setText(game.team2);
        score1.setText(game.score1);
        score2.setText(game.score2);
        location.setText(game.location);
        datetime.setText(game.date.toString()+" "+game.time);
    }
    void buildPannel(){
        panel.setLayout(new MigLayout("debug"));
        panel.add(gameNumber, "span");
        panel.add(homecheck,"cell 0 2");
        panel.add(home, "cell 0 3");
        panel.add(awaycheck);
        panel.add(away);
        panel.add(team1);
        panel.add(team2);
        panel.add(score1);
        panel.add(score2);
        panel.add(datetime);
        panel.add(location);
        frame.add(panel);
        frame.setPreferredSize(new Dimension(300,300));
        frame.setSize(new Dimension(300,300));

        frame.setResizable(false);
        frame.setVisible(true);


    }



}
