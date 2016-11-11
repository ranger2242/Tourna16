import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Tom on 8/12/2015.
 */
public class Popups {
    static MainWindow window= null;
    JFrame frame = new JFrame();
    JLabel titleLabel= new JLabel("TEST POPUP");
    JButton confirmButton = new JButton("Confirm");
    MigLayout layout = new MigLayout("debug , fill");
    static JPanel panel = new JPanel();
    int frameWidth= 400;
    int frameHeight= 400;

    void onStart() {
        frame.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        int screenHeight= (int) dimension.getHeight();
       // int screenWidth= (int) dimension.getWidth();


        // This is an empty content area in the frame
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.getContentPane().add(panel, BorderLayout.CENTER);


        panel.setLayout(layout);
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setMaximumSize(new Dimension(400,screenHeight));
        frame.setVisible(true);

        confirmButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                onPopupComplete();

                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        panel.add(titleLabel, "");
        panel.add(confirmButton, "pushx");
        Main.centreWindow(frame);
    }

    public static void onPopupComplete()
    {
        window =Main.getWindow();
        Main.setWindow(window);
    }
}
