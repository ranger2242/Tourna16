import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSmallView extends JPanel {
    public GameSmallView(Game g){
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        JLabel team1 = new JLabel(g.team1);
        JLabel team2 = new JLabel(g.team2);
        JButton gameNumberButton = new JButton(g.game);
        JPopupMenu gameMenuPopup = new JPopupMenu();
        JMenuItem mntmGameOptions = new JMenuItem("Game Options");
        mntmGameOptions.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                new GameDetailsPopup(g);
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
        this.add(gameNumberButton, "dock west");
    }
}
