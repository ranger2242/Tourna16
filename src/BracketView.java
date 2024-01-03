
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BracketView extends JPanel {
    final BinaryTree tree;

    BracketView(String title, BinaryTree t) {
        tree = t;
        Dimension size = new Dimension(700, 700);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setBackground(Global.mainColor);
        this.setForeground(Global.textColor);
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEFT,
                TitledBorder.TOP, new Font("consolas", Font.PLAIN, 14), Global.textColor));
        this.repaint();
        this.revalidate();
        placeGames();
        refreshGameButtonLinks();
        this.invalidate();
        this.setVisible(true);
    }
    void refresh(){
        ArrayList<ArrayList<Game>> list = tree.getListsByLevel();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                Game g = list.get(i).get(j);
                g.getSmallGameModule().changeValues();
            }
        }
    }

    void placeGames() {
        ArrayList<ArrayList<Game>> list = tree.getListsByLevel();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                Game g = list.get(i).get(j);
                JPanel panel = g.getSmallGameModule();
                Insets insets = this.getInsets();
                Dimension size = panel.getPreferredSize();
                int yOffset = (this.getPreferredSize().height / (list.get(i).size() + 1));
                panel.setBounds(20 + (i * 200) + (insets.left), ((j + 1) * (yOffset)) + (insets.top) - size.height, size.width, size.height);
                this.add(panel);
            }
        }
    }

    void refreshGameButtonLinks() {//send in only JPanels containing Game modules
        for (Component c : this.getComponents()) {
            if (c instanceof JPanel) {
                for (Component a : ((JPanel) c).getComponents()) {
                    if (a instanceof JButton) {
                        a.addMouseListener(new MouseAdapter() {
                            public void mousePressed(MouseEvent e) {
                                System.out.println("!@!@");
                            }
                        });
                    }
                }
            }
        }
    }
}
