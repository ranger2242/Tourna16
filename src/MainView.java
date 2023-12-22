import javax.swing.*;
import java.awt.*;

public class MainView extends JPanel {
    public MainView(){
        this.setLayout(new GridLayout(2, 1));
        BracketView winnerPanel = new BracketView("Winners",Global.bracket.winners);
        BracketView loserPanel = new BracketView("Losers",Global.bracket.losers);
        this.add(winnerPanel);
        this.add(loserPanel);
    }
}
