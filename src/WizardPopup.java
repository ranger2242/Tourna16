import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Tom on 6/4/2015.
 */
public class WizardPopup implements Runnable, MouseListener, AdjustmentListener, ActionListener{

    static MainWindow window= null;


    protected JScrollBar teamCountSelector = new JScrollBar(JScrollBar.HORIZONTAL, 8,1,6,33);
    protected static JFrame frame = new JFrame();
    protected JLabel titleLabel= new JLabel("Tourna 16");
    protected JLabel textOutput1= new JLabel("Enter number of teams: "+teamCount);
    protected JButton confirmButton = new JButton("Confirm");
    protected MigLayout layout = new MigLayout("fill");
    protected static boolean pressed= false;
    protected static int teamCount=8;
    protected static Font systemFont;
    protected static Font systemFontSmall;
    public WizardPopup(){
    }

    void onStart() {
        frame.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        CC componentConstraints = new CC();
        JMenuBar wizardMenuBar = new JMenuBar();
        JMenu mnFile = new JMenu("File");
        JMenuItem mntmOpen = new JMenuItem("Open");
        JMenuItem mntmRecent = new JMenuItem("Recent");
        JPanel panel = new JPanel();
        int screenHeight= (int) dimension.getHeight();
        int screenWidth= (int) dimension.getWidth();
        int frameWidth= 200;
        int frameHeight= 200;

        panel.setLayout(layout);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        panel.add(teamCountSelector,"growx,wrap");
        panel.add( new  JSeparator(JSeparator.HORIZONTAL), "growx, wrap");
        panel.add(confirmButton, "pushx,alignx right ");

        titleLabel.setFont(new Font("robotoThin", Font.PLAIN, 40));

        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                Action details = fc.getActionMap().get("viewTypeDetails");			//set the default view of fc to detailed view
                details.actionPerformed(null);
                fc.showOpenDialog(frame);
                File file = fc.getSelectedFile();
            }
        });
        teamCountSelector.addAdjustmentListener(this);
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onWizardComplete();
                //frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                pressed = true;
            }
        });

        Main.centreWindow(frame);
        //frame.getContentPane().repaint();
        //frame.getContentPane().revalidate();
    }
    public static void loadFontFromFile()
    {
        Font robotoThin;
        try {
            robotoThin = Font.createFont(Font.TRUETYPE_FONT, new File("C:\\Users\\Tom\\Google Drive\\JAVA\\Tournament Builder\\fonts\\Roboto-Thin.ttf"));
            systemFont=robotoThin;
            systemFontSmall=new Font("robotoThin", Font.PLAIN, 3);
        } catch (IOException |FontFormatException e) {
            //Handle exception
        }
    }

    public static Font getFont()
    {
        return systemFont;
    }

    public static Font getFontSmall()
    {
        return systemFontSmall;
    }

    public static void onWizardComplete() {
        Main.wizard.frame.setVisible(false);
        Main.window=new MainWindow();
        Main.window.setTeamCount(teamCount);
        Main.window.onStart();
//        Main.window.refresh();
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
        Adjustable source = e.getAdjustable();
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
        textOutput1.setText("Enter number of teams: "+teamCount);
        frame.invalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source= (JButton) e.getSource();



    }

    @Override
    public void run() {

    }
}
