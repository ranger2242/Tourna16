import javax.swing.*;
import java.awt.*;

/**
 * Created by Tom on 3/12/2016.
 */
public class StartPopup extends Popups {
    public void onStart2(){
        frameWidth=600;
        frame.setSize(new Dimension(frameWidth,frameHeight));
        JLabel label = new JLabel("Penis");
        JLabel label2 = new JLabel("Penis");

        panel.add(label);
        panel.add(label2,"newline");
    }
}
