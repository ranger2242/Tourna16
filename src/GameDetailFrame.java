import javax.swing.*;
import java.awt.*;

public class GameDetailFrame extends JFrame {
    GameDetailFrame(Game g){
    GameDetailView view = new GameDetailView(g);
    this.setResizable(false);
    // This is an empty content area in the frame
    this.getContentPane().add(view, BorderLayout.CENTER);

    this.pack();
    this.setVisible(true);
    Global.centreWindow(this);

}
}
