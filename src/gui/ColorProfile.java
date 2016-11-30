package gui;

import java.awt.*;

/**
 * Created by Chris Cavazos on 11/27/2016.
 */
public class ColorProfile {
    Color textColor =new Color(220,220,220);
    Color mainColor=new Color(60,60,60);
    Color lightColor=new Color(80,80,80);
    Color accentColor = new Color(100,100,255);

    public Color getMainColor() {
        return mainColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getLightColor() {
        return lightColor;
    }

    public Color getAccentColor() {
        return accentColor;
    }
}
