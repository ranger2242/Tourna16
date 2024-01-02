import javax.swing.*;
import javax.swing.plaf.basic.BasicMenuBarUI;
import java.awt.*;

public class WizardMenu extends JMenuBar {
    public WizardMenu(){
        JMenu mnFile = new JMenu("File");
        JMenuItem itemOpen = new JMenuItem("Open");
        JMenuItem itemRecent = new JMenuItem("Recent");

        mnFile.setForeground(Global.textColor);
        mnFile.setBackground(Global.lightColor);

        mnFile.add(itemOpen);
        mnFile.add(itemRecent);

        this.add(mnFile);

        this.setBackground(Global.mainColor);
        this.setForeground(Global.mainColor);
        this.setUI(new BasicMenuBarUI() {
            public void paint(Graphics g, JComponent c) {
                g.setColor(Global.lightColor);
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });
        itemOpen.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            Action details = fc.getActionMap().get("viewTypeDetails");            //set the default view of fc to detailed view
            details.actionPerformed(null);
            fc.showOpenDialog(new JFrame());
        });
    }
}
