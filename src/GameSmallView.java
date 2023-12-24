import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSmallView extends JPanel {
    JLabel team1;
    JLabel team2;
    JLabel score1;
    JLabel score2;
    Game game;

    public GameSmallView(Game g1) {
        game = g1;
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        team1 = new JLabel(game.team1);
        team2 = new JLabel(game.team2);
        score1 = new JLabel(game.score1);
        score2 = new JLabel(game.score2);
        JButton gameNumberButton = new JButton("" + game.index);
        JPopupMenu gameMenuPopup = new JPopupMenu();
        JMenuItem mntmGameOptions = new JMenuItem("Game Options");
        mntmGameOptions.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                new GameDetailFrame(game);
            }
        });
        gameNumberButton.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                gameMenuPopup.show(e.getComponent(), gameNumberButton.getX(),
                        gameNumberButton.getY() + gameNumberButton.getHeight());
            }
        }));

        this.setLayout(new MigLayout());
        this.setBorder(border);
        gameMenuPopup.add(mntmGameOptions);
        this.add(team1, "cell 1 0, w 100px");
        this.add(team2, "cell 1 1, w 100px");
        this.add(score1, "cell 2 0, w 10px");
        this.add(score2, "cell 2 1, w 10px");
        this.add(gameNumberButton, "dock west");
    }

    public void changeValues() {
        team1.setText(game.team1);
        team2.setText(game.team2);
        score1.setText(game.score1);
        score2.setText(game.score2);
    }
}
