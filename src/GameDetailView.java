import com.toedter.calendar.JCalendar;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Date;

public class GameDetailView extends JPanel {


    Game game ;

    public GameDetailView(Game game){
        this.game = game;
        int team1posx = ((this.getWidth())/4)-10;
        int team2posx = (((this.getWidth())/4)*2)+70;
        JSpinner timeSpinner = new JSpinner( new SpinnerDateModel() );
        JLabel titleLabel= new JLabel("Tourna 15");
        JButton confirmButton = new JButton("Confirm");
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();

        timeSpinner.setEditor(timeEditor);
        timeSpinner.setValue(new Date());
        timeEditor.getTextField().setText(game.time);

        JLabel team1 = new JLabel(game.team1);
        JLabel team2 = new JLabel(game.team2);
        JLabel time = new JLabel(game.time);
        JTextField location = new JTextField(game.location);
        JTextField score1 = new JFormattedTextField(integerFormat);
        JTextField score2 = new JFormattedTextField(integerFormat);
        score1.setText(String.valueOf(game.score1));
        score2.setText(String.valueOf(game.score2));

        score1.setText(game.score1);
        score2.setText(game.score2);

        JButton changeLocation = new JButton("..");
        JButton changeTime = new JButton("..");
        JCalendar cal = new JCalendar();
        cal.setDate(game.date);

        confirmButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                game.score1 = score1.getText();
                game.score2 = score2.getText();
                game.location = location.getText();
                game.applyResult();
            }
        });


        changeTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String d = timeEditor.getTextField().getText();
                Date sdate = cal.getDate();
                d = "" + d + " " + sdate;
                time.setText(d.substring(0, 20));
                game.time = d.substring(0, 8);
                game.date =sdate;
            }
        });

        location.setSize(200,14);

         titleLabel.setText("Game Details");

        this.setLayout(new MigLayout("fill"));
        this.add(titleLabel, "pos 150  20");
        this.add(team1,"pos "+team1posx+" "+(90));
        this.add(team2,"pos "+team2posx+" "+(90));
        this.add(score1,"pos "+team1posx+" 110, w 30");
        this.add(score2,"pos "+team2posx+" 110, w 30");
        this.add(changeLocation,"pos 90 160");
        this.add(location, "pos 150 160, w 150");
        this.add(changeTime, "pos 90 190");
        this.add(time, "pos 150 190");
        this.add(cal, "pos 90 240");
        this.add(timeSpinner, "pos 90 220");
        this.add(confirmButton, "pos 160 420");
    }
}
