import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
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

        //force redraw to recaluclate new borders after adding component
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
        //Handle keyboard input on the main window
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        //define all hotkeys here
                        if (e.getKeyCode() == KeyEvent.VK_F10) {
                           // WizardPopup.frame.setVisible(true);
                        }
                        return false;
                    }
                });
    }
}