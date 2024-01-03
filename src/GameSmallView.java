import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSmallView extends JPanel {
    final JLabel team1;
    final JLabel team2;
    final JLabel score1;
    final JLabel score2;
    final Game game;

    GameSmallView(Game g1) {
        game = g1;
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        team1 = new JLabel(game.teamName(1));
        team2 = new JLabel(game.teamName(2));
        score1 = new JLabel(game.score1);
        score2 = new JLabel(game.score2);
        team1.setOpaque(true);
        team2.setOpaque(true);
        score1.setOpaque(true);
        score2.setOpaque(true);

        JButton gameNumberButton = new JButton("" + game.index);

        gameNumberButton.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                new GameDetailFrame(game);
            }
        }));

        this.setLayout(new MigLayout());
        this.setBorder(border);
        this.add(team1, "cell 1 0, w 100px");
        this.add(team2, "cell 1 1, w 100px");
        this.add(score1, "cell 2 0, w 10px");
        this.add(score2, "cell 2 1, w 10px");
        this.add(gameNumberButton, "dock west");
    }

    public void changeValues() {
        team1.setText(game.teamName(1));
        team2.setText(game.teamName(2));
        score1.setText(game.score1);
        score2.setText(game.score2);


    }
}
