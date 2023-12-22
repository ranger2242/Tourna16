import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class MainMenuBar extends JMenuBar {
    JMenu file = new JMenu("File");
    JMenu view = new JMenu("View");
    JCheckBoxMenuItem mntmSmallView = new JCheckBoxMenuItem("Small Games");
    JMenuItem fOpen = new JMenuItem("Open");
    JMenuItem fSave = new JMenuItem("Save");
    JMenuItem fSaveAs = new JMenuItem("Save As");
    JMenuItem fPrefrences = new JMenuItem("Preferences");
    JMenuItem fExit = new JMenuItem("Exit");
    JMenu edit = new JMenu("Edit");
    JMenu about = new JMenu("About");
    JMenuItem eTeamList = new JMenuItem("Team List Editior");

    public MainMenuBar() {
        eTeamList.setFont(Global.robotoThin);
        this.add(file);
        this.add(view);
        this.add(edit);
        this.add(about);
        view.add(mntmSmallView);
        file.add(fOpen);
        file.add(fSave);
        file.add(fSaveAs);
        file.add(fPrefrences);
        file.add(fExit);
        edit.add(eTeamList);

        fOpen.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            Action details = fc.getActionMap().get("viewTypeDetails");            //set the default view of fc to detailed view
            details.actionPerformed(null);
            File file = fc.getSelectedFile();
        });
        fSave.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

            }
        });
        eTeamList.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {//new java.awt.FileDialog((java.awt.Frame) null).setVisible(true);
                new TeamsFrame();
            }
        }));
    }
}
