import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainMenuBar extends JMenuBar {
    final JMenu file = new JMenu("File");
    final JMenu view = new JMenu("View");
    final JCheckBoxMenuItem itemSmallView = new JCheckBoxMenuItem("Small Games");
    final JMenuItem fOpen = new JMenuItem("Open");
    final JMenuItem fSave = new JMenuItem("Save");
    final JMenuItem fSaveAs = new JMenuItem("Save As");
    final JMenuItem fPreferences = new JMenuItem("Preferences");
    final JMenuItem fExit = new JMenuItem("Exit");
    final JMenu edit = new JMenu("Edit");
    final JMenu about = new JMenu("About");
    final JMenuItem eTeamList = new JMenuItem("Team List Editor");

    public MainMenuBar() {
        this.add(file);
        this.add(view);
        this.add(edit);
        this.add(about);
        view.add(itemSmallView);
        file.add(fOpen);
        file.add(fSave);
        file.add(fSaveAs);
        file.add(fPreferences);
        file.add(fExit);
        edit.add(eTeamList);

        fOpen.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            Action details = fc.getActionMap().get("viewTypeDetails");            //set the default view of fc to detailed view
            details.actionPerformed(null);
        });
        fSave.addMouseListener(new MouseAdapter() {
        });
        eTeamList.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {//new java.awt.FileDialog((java.awt.Frame) null).setVisible(true);
                new TeamsFrame();
            }
        }));
    }
}
