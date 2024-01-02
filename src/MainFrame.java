import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        //define some properties of the frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setSize(new Dimension(800, 600));
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        //define the main view
        MainView view = new MainView();
        JScrollPane scrollPane = new JScrollPane(view, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);

        //force redraw to recalculate new borders after adding component
        this.repaint();
        this.revalidate();

        //center window to the screen
        Global.centreWindow(this);
        setHotkeys();
        MainMenuBar menuBar = new MainMenuBar();
        this.setJMenuBar(menuBar);
        this.invalidate();
        this.setVisible(true);

    }
    private void setHotkeys() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(e -> false);
    }
}