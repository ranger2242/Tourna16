import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Tom on 6/4/2015.
 */
public class TeamListImportPopup{
    static MainWindow window= null;
    static JFrame frame = new JFrame();
    JLabel titleLabel= new JLabel("");
    JButton confirmButton = new JButton("Confirm");
    MigLayout layout = new MigLayout("fill");
    static JPanel panel = new JPanel();
    static ArrayList teamList = new ArrayList();
    static ArrayList<JTextField> fieldList = new ArrayList<JTextField>();

    void onReopen(){
        frame.setVisible(true);
    }

    void onStart() {
        frame.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int screenHeight= (int) dimension.getHeight();
        int screenWidth= (int) dimension.getWidth();
        int frameWidth= 400;
        int frameHeight= 800;

        // This is an empty content area in the frame
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.getContentPane().add(panel, BorderLayout.CENTER);


        panel.setLayout(layout);
        frame.pack();
        frame.setMinimumSize(new Dimension(170, 300));
        frame.setMaximumSize(new Dimension(400, screenHeight));
        frame.setVisible(true);

        confirmButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<fieldList.size();i++)
                {
                    teamList.add(i, fieldList.get(i).getText());
                }
                onPopupComplete();
            }
        });
        panel.add(titleLabel, "wrap");
        drawTeamImportForm();
        panel.add(confirmButton, "");
        frame.pack();
        Main.centreWindow(frame);
    }

    public void onPopupComplete()
    {
        window =Main.getWindow();
        //window.setTeamList(teamList);
        window.panel.removeAll();
        window.makeMenuBar();
        //window.loadBracket();

        window.scrollPane.updateUI();
        window.frame.revalidate();
        //window.paint(window.panel.getGraphics());
        Main.setWindow(window);
        frame.setVisible(false);
        frame.dispose();
        //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

    }
    static void drawTeamImportForm()
    {
        for (int i=0; i<WizardPopup.teamCount; i++)
        {
            JLabel teamLabel = new JLabel("Team "+(i+1));
            fieldList.add(i, new JTextField("Team "+(i+1)));
            teamLabel.setFont(Main.robotoThin);
            panel.add(teamLabel, "spanx");
            panel.add(fieldList.get(i), "spanx, w 150, wrap");
        }
    }
    static ArrayList<String> getTeamList()
    {
        return teamList;
    }

}
