import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    public MainView(){
        this.setLayout(new GridLayout(2, 1));
        Global.winnerPanel = new BracketView("Winners",Global.bracket.winners);
        Global.loserPanel = new BracketView("Losers",Global.bracket.losers);
        this.add(Global.winnerPanel);
        this.add(Global.loserPanel);
    }
}
