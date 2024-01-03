import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Global {
    //some static vars can be defined at compile
    public static final Color textColor = new Color(220, 220, 220);
    public static final Color mainColor = new Color(60, 60, 60);
    public static final Color lightColor = new Color(80, 80, 80);
    public static final Color accentColor = new Color(100, 100, 255);
    public static Bracket bracket;
    public static int teamCount = 8;
    public static MainFrame mainFrame;
    public static final ArrayList<Team> teamList = new ArrayList<>();
    public static BracketView winnerPanel ;
    public static       BracketView loserPanel ;
    public static int scrWidth = 1920;
    public static int scrHeight = 1080;


    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        System.out.println("Screen:" + dimension.getWidth() + " " + dimension.getHeight());

        frame.setLocation(x, y);
    }

    public static void error(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);

    }

    public static void print(Object... s) {
        String[] ar = new String[s.length];
        int i = 0;
        for (Object o : s) {
            if (o != null)
                ar[i++] = o.toString();
            else
                ar[i++] = "null";
        }
        StringBuilder b = new StringBuilder();
        List.of(ar).forEach((x) -> {
            b.append(x);
            b.append(" ");
        });
        System.out.println(b);
    }
}
