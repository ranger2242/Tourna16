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
                Team t =Global.teamList.get(i);
                t.name = fieldList.get(i).getText();

               // Global.teamList.add(i, fieldList.get(i).getText());
            }
            Global.winnerPanel.refresh();
            Global.loserPanel.refresh();
            Global.print("REFRESHG");
        });
        this.add(titleLabel, "wrap");
        fieldList.clear();
        for (int i=0; i<Global.teamCount; i++)
        {
            JLabel teamLabel = new JLabel("Team "+(i+1));
            fieldList.add(i, new JTextField(Global.teamList.get(i).name));
            this.add(teamLabel);
            this.add(fieldList.get(i), "spanx, w 150, wrap");
        }
        this.add(confirmButton, "");

    }
}
