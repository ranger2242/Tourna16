import javax.swing.*;
import java.awt.*;

public class GameDetailFrame extends JFrame {
public GameDetailFrame(Game g){
    GameDetailView view = new GameDetailView(g);

    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

    int screenHeight= (int) dimension.getHeight();
    int frameWidth= 400;
    int frameHeight= 800;
    this.setResizable(false);
    // This is an empty content area in the frame
    this.setSize(new Dimension(frameWidth, frameHeight));
    this.getContentPane().add(view, BorderLayout.CENTER);

    this.pack();
    this.setMinimumSize(new Dimension(400, 500));
    this.setMaximumSize(new Dimension(400, screenHeight));
    this.setVisible(true);
    Global.centreWindow(this);

}
}
