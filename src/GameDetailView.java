import com.toedter.calendar.JCalendar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.Date;

public class GameDetailView extends JPanel {

    Game game;
    JSpinner.DateEditor timeEditor;
    JTextField time ;
    JCalendar cal = new JCalendar();

    GameDetailView(Game g) {
        game = g;
        this.setLayout(new MigLayout());
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        JLabel titleLabel = new JLabel("Tourna 15");
        JButton confirmButton = new JButton("Confirm");
        timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();

        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        timeEditor.getTextField().setText(game.time);

        JTextField team1 = new JTextField(game.team1.name);
        JTextField team2 = new JTextField(game.team2.name);
        time = new JTextField(game.time);
        JTextField location = new JTextField(game.location);
        JTextField score1 = new JFormattedTextField(integerFormat);
        JTextField score2 = new JFormattedTextField(integerFormat);
        score1.setSize(50,score1.getHeight());
        score2.setSize(50,score2.getHeight());
        score1.setText(String.valueOf(game.score1));
        score2.setText(String.valueOf(game.score2));

        score1.setText(game.score1);
        score2.setText(game.score2);

        cal.setDate(game.date);

        confirmButton.addActionListener(e -> {
            game.team1.name = team1.getText();
            game.team2.name = team2.getText();
            game.time = time.getText() ;
            game.date = cal.getDate();

            game.score1 = score1.getText();
            game.score2 = score2.getText();
            game.location = location.getText();
            game.applyResult();
        });


        timeSpinner.addChangeListener(e->{
            formatDate();
        });
        timeSpinner.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                formatDate();

            }

            @Override
            public void keyPressed(KeyEvent e) {
                formatDate();

            }

            @Override
            public void keyReleased(KeyEvent e) {
                formatDate();
            }
        });
        cal.getYearChooser().addPropertyChangeListener(evt -> formatDate());
        cal.getDayChooser().addPropertyChangeListener(evt -> formatDate());
        cal.getMonthChooser().addPropertyChangeListener(evt -> formatDate());

        location.setSize(200, 14);

        titleLabel.setText("Game Details");

        this.setLayout(new MigLayout());
        this.add(titleLabel,"cell 0 0, growx");
        this.add(team1,"cell 0 1, w 200");
        this.add(team2,"cell 0 2, w 200");
        this.add(score1,"cell 1 1, w 50");
        this.add(score2,"cell 1 2, w 50");
        this.add(new JLabel("Location"),"cell 0 3");
        this.add(location,"cell 0 4, growx,spanx");
        this.add(new JLabel("Time"),"cell 0 5");

        this.add(time,"cell 0 6, growx,spanx");
        this.add(timeSpinner,"cell 0 7, growx,spanx");
        this.add(cal,"cell 0 8, growx,spanx");
        this.add(confirmButton,"cell 0 9, growx,spanx");
    }

    void formatDate(){
        String d = timeEditor.getTextField().getText();
        Date sdate = cal.getDate();
        d = String.format("%s %s", d, sdate);
        time.setText(d.substring(0, 20));
        game.time = d.substring(0, 8);
        game.date = sdate;
    }
}
