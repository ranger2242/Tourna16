
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
        Dimension size = new Dimension(Global.scrWidth, Global.scrHeight);
        this.setSize(size);
        this.setPreferredSize(size);
        this.setBackground(Global.mainColor);
        this.setForeground(Global.textColor);
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEFT,
                TitledBorder.TOP, new Font("consolas", Font.PLAIN, 14), Global.textColor));
        this.repaint();
        this.revalidate();

        BinaryNode rt = tree.getRoot();
        GameSmallView md = rt.game.getSmallGameModule();
        int w = md.getPreferredSize().width;
        int x = 20 + (tree.getListsByLevel().size() * w);
        if (tree.equals(Global.bracket.losers))
            once = true;
        int y =  tree.equals(Global.bracket.losers) ?(int) (this.getHeight() *.66): this.getHeight()/2;
        place(rt, x - md.getWidth(),y, 1);
        refreshGameButtonLinks();
        drawLines();
        this.invalidate();
        this.setVisible(true);
    }

    void refresh() {
        ArrayList<ArrayList<Game>> list = tree.getListsByLevel();
        for (ArrayList<Game> games : list) {
            for (Game g : games) {
                g.getSmallGameModule().changeValues();
            }
        }
    }

    void drawLines() {
        for (ArrayList<Game> level : tree.getListsByLevel()) {
            for (Game g : level) {
                if (g.hasNext()) {
                    GameSmallView nv = g.next().getSmallGameModule();
                    GameSmallView v = g.getSmallGameModule();
                    int x1 = v.getX() + v.getWidth();
                    int y1 = v.getY() + v.getHeight() / 2;
                    int x2 = nv.getX();
                    int y2 = nv.getY() + nv.getHeight() / 2;

                    lines.add(new int[]{x1, y1, x2, y2});

                }
            }
        }
    }

    ArrayList<int[]> lines = new ArrayList<>();
    boolean once = false;

    void place(BinaryNode node, int x, int y, int level) {
        if(node.game==null)
            return;

        GameSmallView panel = node.game.getSmallGameModule();
        Insets insets = this.getInsets();
        Dimension size = panel.getPreferredSize();
        int x1 = x + (insets.left);
        int y1 = y + (insets.top) - size.height;
        Global.print(level, x1, y1);
        panel.setBounds(x1, y1, size.width, size.height);
        this.add(panel);
        double d = (this.getHeight() / Math.pow(2, level + 1));
        int x2=x - size.width-20;
        if (once && level == 1) {
            once = false;
            place(node.left, x2, y, level);

        } else {
            if (node.left != null) {
                place(node.left, x2,  (int) (y - d), level + 1);
            }
            if (node.right != null) {
                place(node.right, x2,  (int) (y + d), level + 1);
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

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int[] line : lines)
            g.drawLine(line[0], line[1], line[2], line[3]);
    }

    ;
}
