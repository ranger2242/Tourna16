import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TeamsView extends JPanel {
    ArrayList<JTextField> fieldList = new ArrayList<>();

    public TeamsView(){
        JLabel titleLabel= new JLabel("");
        JButton confirmButton = new JButton("Confirm");

        this.setLayout( new MigLayout("fill"));
        confirmButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                for(int i=0; i<fieldList.size();i++)
                {
                    Global.teamList.add(i, fieldList.get(i).getText());
                }
            }
        });
        this.add(titleLabel, "wrap");
        for (int i=0; i<Global.teamCount; i++)
        {
            JLabel teamLabel = new JLabel("Team "+(i+1));
            fieldList.add(i, new JTextField("Team "+(i+1)));
            teamLabel.setFont(Global.robotoThin);
            this.add(teamLabel);
            this.add(fieldList.get(i), "spanx, w 150, wrap");
        }
        this.add(confirmButton, "");

    }
}
