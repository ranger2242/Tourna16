import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.util.ArrayList;

public class TeamsView extends JPanel {
    final ArrayList<JTextField> fieldList = new ArrayList<>();

    public TeamsView(){
        JLabel titleLabel= new JLabel("");
        JButton confirmButton = new JButton("Confirm");

        this.setLayout( new MigLayout("fill"));
        confirmButton.addActionListener(e -> {
            for(int i=0; i<fieldList.size();i++)
            {
                Global.teamList.add(i, fieldList.get(i).getText());
            }
        });
        this.add(titleLabel, "wrap");
        for (int i=0; i<Global.teamCount; i++)
        {
            JLabel teamLabel = new JLabel("Team "+(i+1));
            fieldList.add(i, new JTextField("Team "+(i+1)));
            this.add(teamLabel);
            this.add(fieldList.get(i), "spanx, w 150, wrap");
        }
        this.add(confirmButton, "");

    }
}
