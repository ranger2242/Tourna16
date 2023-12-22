import javax.swing.*;
import java.awt.*;

public class WizardFrame extends JFrame {
    public WizardFrame(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        WizardView view = new WizardView();
        WizardMenu menu = new WizardMenu();
        this.setJMenuBar(menu);
        this.revalidate();

        int screenHeight = (int) dimension.getHeight();
        int frameWidth = 200;
        int frameHeight = 200;

        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(new Dimension(frameWidth, frameHeight));
        this.getContentPane().add(view, BorderLayout.CENTER);
        this.setMaximumSize(new Dimension(400, screenHeight));
        this.setVisible(true);
        Global.centreWindow(this);

    }
}
