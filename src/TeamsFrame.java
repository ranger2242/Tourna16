import javax.swing.*;
import java.awt.*;

public class TeamsFrame extends JFrame {
    public TeamsFrame(){
        this.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        TeamsView view = new TeamsView();

        int screenHeight= (int) dimension.getHeight();
        int frameWidth= 400;
        int frameHeight= 800;
        this.setSize(new Dimension(frameWidth, frameHeight));
        this.getContentPane().add(view, BorderLayout.CENTER);
        this.setMinimumSize(new Dimension(170, 300));
        this.setMaximumSize(new Dimension(400, screenHeight));
        this.pack();
        this.setVisible(true);
        Global.centreWindow(this);


    }
}
