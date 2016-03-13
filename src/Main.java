import java.awt.*;

public class Main {

	static GameDetailsPopup gdp = new GameDetailsPopup();
	static MainWindow window = new MainWindow();
	static WizardPopup wizard = new WizardPopup();
	static Font robotoThin;
	static Font robotoThinSmall;

	public static void out(String s){
		System.out.println(s);
	}

	public static void main(String[] args) {

		window.onStart();
		wizard.onStart();
		//gdp.onStart();

	}
	public static void setFont()
	{
		robotoThin =wizard.getFont();
		robotoThinSmall = wizard.getFontSmall();
	}

	public static MainWindow getWindow()
	{
		return window;
	}
	public static void setWindow(MainWindow w)
	{
		window = w;
	}
	public static void centreWindow(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		System.out.println("Screen:"+dimension.getWidth()+" "+dimension.getHeight());

		frame.setLocation(x, y);
	}
}


