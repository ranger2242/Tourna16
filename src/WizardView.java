import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;

public class WizardView extends JPanel {
    public WizardView() {

        CC componentConstraints = new CC();
        componentConstraints.alignX("center").spanX();

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBackground(Global.accentColor);
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(e -> {
            Global.bracket = new Bracket(Global.teamCount);
            Global.mainFrame = new MainFrame();
        });

        JLabel titleLabel = new JLabel("Tourna 24");
        titleLabel.setForeground(Global.textColor);
        titleLabel.setFont(new Font("robotoThin", Font.PLAIN, 40));

        JLabel textOutput1 = new JLabel("Enter number of teams: " + Global.teamCount);
        textOutput1.setForeground(Global.textColor);


        JScrollBar selector = getjScrollBar(textOutput1);

        this.setBackground(Global.mainColor);
        this.setForeground(Global.textColor);
        this.setLayout(new MigLayout("fill"));
        this.add(titleLabel, componentConstraints);
        this.add(new JSeparator(JSeparator.HORIZONTAL), "growx, wrap");
        this.add(textOutput1, "growx,wrap");
        this.add(selector, "growx,wrap");
        this.add(new JSeparator(JSeparator.HORIZONTAL), "growx, wrap");
        this.add(confirmButton, "pushx,alignx right ");
    }

    private static JScrollBar getjScrollBar(JLabel textOutput1) {
        JScrollBar selector = new JScrollBar(JScrollBar.HORIZONTAL, 8, 1, 6, 33);
        selector.setForeground(Global.textColor);
        selector.setBackground(Global.lightColor);
        selector.addAdjustmentListener(e -> {
            int type = e.getAdjustmentType();
            switch (type) {
                case AdjustmentEvent.UNIT_INCREMENT:
                case AdjustmentEvent.UNIT_DECREMENT:
                case AdjustmentEvent.BLOCK_INCREMENT:
                case AdjustmentEvent.BLOCK_DECREMENT:
                case AdjustmentEvent.TRACK:
                    break;
            }
            Global.teamCount = e.getValue();
            textOutput1.setText("Enter number of teams: " + Global.teamCount);
        });
        return selector;
    }
}
