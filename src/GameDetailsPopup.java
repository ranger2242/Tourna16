import com.toedter.calendar.JCalendar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Date;

/**
* Created by Tom on 8/12/2015.
*/

public class GameDetailsPopup{

    static MainWindow window= null;
    JScrollBar teamCountSelector = new JScrollBar(JScrollBar.HORIZONTAL, 16,1,6,33);
    JFrame frame = new JFrame();
    JLabel titleLabel= new JLabel("Tourna 15");
    JButton confirmButton = new JButton("Confirm");
    MigLayout layout = new MigLayout("fill");
    static boolean pressed= false;
    static Font systemFont;
    Game game = new Game();
    static String date;
    static Date fullDate = new Date();
    JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
    JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
    static NumberFormat integerFormat = NumberFormat.getIntegerInstance();

    void onStart() {

        frame.setResizable(false);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        timeEditor.getTextField().setText(game.getTime());

        JPanel panel = new JPanel();
        JLabel team1 = new JLabel(game.getTeam1());
        JLabel team2 = new JLabel(game.getTeam2());
        JLabel time = new JLabel(game.getTime());
        JTextField location = new JTextField(game.getLocation());
        JTextField score1 = new JFormattedTextField(integerFormat);
        JTextField score2 = new JFormattedTextField(integerFormat);
        score1.setText(String.valueOf(game.getScore1()));
        score2.setText(String.valueOf(game.getScore2()));

        score1.setText(""+game.getScore1());
        score2.setText(""+game.getScore2());

        JButton changeLocation = new JButton("..");
        JButton changeTime = new JButton("..");
        JCalendar cal = new JCalendar();
        cal.setDate(game.getFulllDate());


        int screenHeight= (int) dimension.getHeight();
      //  int screenWidth= (int) dimension.getWidth();
        int frameWidth= 400;
        int frameHeight= 800;

        // This is an empty content area in the frame
        frame.setSize(new Dimension(frameWidth, frameHeight));
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        panel.setLayout(layout);
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 500));
        frame.setMaximumSize(new Dimension(400, screenHeight));
        frame.setVisible(true);


        confirmButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                onPopupComplete();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                pressed = true;
                try {
                    game.setScore1(Integer.parseInt(score1.getText()));
                    game.setScore2(Integer.parseInt(score2.getText()));
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    JPanel errPanel = new JPanel();
                    JLabel label= new JLabel();
                    label.setFont(Main.robotoThin);

                    errPanel.add(label);
                    JOptionPane jp = new JOptionPane();
                    UIManager.put("OptionPane.messageFont", Main.robotoThin);
                    JOptionPane.showMessageDialog(jp,"Score values must be numbers.\nChanges to score will revert.");

                }
                game.setLocation(location.getText());
                MainWindow.panel.removeAll();
                MainWindow.panel.updateUI();
                System.out.println(Integer.parseInt(score1.getText()));
            }
        });


        changeTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                date = timeEditor.getTextField().getText();
                Date sdate = cal.getDate();
                fullDate=sdate;
                date = "" + date + " " + sdate;
                time.setText(date.substring(0, 20));
                game.setTime(date.substring(0, 8));
                game.setDate(date.substring(9,20),fullDate);
            }
        });

        location.setSize(200,14);
        //location.
        titleLabel.setText("Game Details");
        titleLabel.setFont(systemFont);
        team1.setFont(systemFont);
        team2.setFont(systemFont);
        location.setFont(systemFont);
        time.setFont(systemFont);

        int team1posx = ((frame.getWidth())/4)-10;
        int team2posx = (((frame.getWidth())/4)*2)+70;

        panel.add(titleLabel, "pos 150  20");
        panel.add(team1,"pos "+team1posx+" "+(90));
        panel.add(team2,"pos "+team2posx+" "+(90));
        panel.add(score1,"pos "+team1posx+" 110, w 30");
        panel.add(score2,"pos "+team2posx+" 110, w 30");
        panel.add(changeLocation,"pos 90 160");
        panel.add(location, "pos 150 160, w 150");
        panel.add(changeTime, "pos 90 190");
        panel.add(time, "pos 150 190");
        panel.add(cal, "pos 90 240");
        panel.add(timeSpinner, "pos 90 220");
        panel.add(confirmButton, "pos 160 420");

        Main.centreWindow(frame);
    }

    public void setGame(Game g)
    {
        game=g;
    }

    public static void onPopupComplete()
    {
        //window =Main.getWindow();
        //window.panel.removeAll();

        //window.loadBracket();
        MainWindow.scrollPane.updateUI();
        MainWindow.frame.revalidate();

        //Main.setWindow(window);
    }
}

