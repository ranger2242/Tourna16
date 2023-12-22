import java.awt.*;
import java.util.ArrayList;

public class Global {
    //some static vars can be defined at compile
    public static Color textColor = new Color(220, 220, 220);
    public static Color mainColor = new Color(60, 60, 60);
    public static Color lightColor = new Color(80, 80, 80);
    public static Color accentColor = new Color(100, 100, 255);
    public static Bracket bracket;
    public static int teamCount =8;
    static Font robotoThin;
    public static ArrayList teamList = new ArrayList();


    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        System.out.println("Screen:" + dimension.getWidth() + " " + dimension.getHeight());

        frame.setLocation(x, y);
    }

}
