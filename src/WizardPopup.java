import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * Created by Tom on 6/4/2015.
 */
public class WizardPopup implements Runnable, MouseListener, AdjustmentListener, ActionListener {


    protected JScrollBar teamCountSelector = new JScrollBar(JScrollBar.HORIZONTAL, 8, 1, 6, 33);
    protected static JFrame frame = new JFrame();
    protected JLabel titleLabel = new JLabel("Tourna 16");
    protected JLabel textOutput1 = new JLabel("Enter number of teams: " + teamCount);
    protected JButton confirmButton = new JButton("Confirm");
    protected MigLayout layout = new MigLayout("fill");
    protected static boolean pressed = false;
    protected static int teamCount = 8;

    public WizardPopup() {
    }

    void onStart() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        frame.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        CC componentConstraints = new CC();
        JMenuBar wizardMenuBar = new JMenuBar();
        JMenu mnFile = new JMenu("File");
        JMenuItem mntmOpen = new JMenuItem("Open");
        JMenuItem mntmRecent = new JMenuItem("Recent");
        JPanel panel = new JPanel();
        int screenHeight = (int) dimension.getHeight();
        //int screenWidth= (int) dimension.getWidth();
        int frameWidth = 200;
        int frameHeight = 200;

        panel.setLayout(layout);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        //frame.pack();
        //frame.setMinimumSize(new Dimension(400, 300));
        frame.setMaximumSize(new Dimension(400, screenHeight));
        frame.setVisible(true);
        componentConstraints.alignX("center").spanX();
        wizardMenuBar.add(mnFile);
        mnFile.add(mntmOpen);
        mnFile.add(mntmRecent);
        frame.setJMenuBar(wizardMenuBar);
        panel.add(titleLabel, componentConstraints);
        panel.add(new JSeparator(JSeparator.HORIZONTAL), "growx, wrap");
        panel.add(textOutput1, "growx,wrap");
        panel.add(teamCountSelector, "growx,wrap");
        panel.add(new JSeparator(JSeparator.HORIZONTAL), "growx, wrap");
        panel.add(confirmButton, "pushx,alignx right ");

        titleLabel.setFont(new Font("robotoThin", Font.PLAIN, 40));

        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //new java.awt.FileDialog((java.awt.Frame) null ).setVisible(true);
                JFileChooser fc = new JFileChooser();
                Action details = fc.getActionMap().get("viewTypeDetails");            //set the default view of fc to detailed view
                details.actionPerformed(null);
                fc.showOpenDialog(frame);
                File file = fc.getSelectedFile();
                FileHandler.load(file);
                onWizardComplete(false);
            }
        });
        teamCountSelector.addAdjustmentListener(this);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onWizardComplete(false);
                //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                pressed = true;
            }
        });

        Main.centreWindow(frame);
        //frame.getContentPane().repaint();
        //frame.getContentPane().revalidate();
    }

    public static void onWizardComplete(boolean loaded) {
        frame.setVisible(false);
        Main.window = new MainWindow();
        Main.window.setTeamCount(teamCount);
        Main.window.onStart(loaded);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        int type = e.getAdjustmentType();
        switch (type) {
            case AdjustmentEvent.UNIT_INCREMENT:
                break;
            case AdjustmentEvent.UNIT_DECREMENT:
                break;
            case AdjustmentEvent.BLOCK_INCREMENT:
                break;
            case AdjustmentEvent.BLOCK_DECREMENT:
                break;
            case AdjustmentEvent.TRACK:
                break;
        }
        teamCount = e.getValue();
        textOutput1.setText("Enter number of teams: " + teamCount);
        frame.invalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void run() {

    }
}
