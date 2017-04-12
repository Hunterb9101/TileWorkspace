package tileMain;

import java.awt.Color;

public class Gradient {
	Color a = null;
	Color c = null;
	public static Color getColorAtPoint(Color a, Color c, double x, double lowX, double highX, double exp){
		double p = x/highX;
		p = Math.pow(p, exp);
	

		int r = (int)(a.getRed() * p + c.getRed() * (1-p));
		int g = (int)(a.getGreen() * p + c.getGreen() * (1-p));
		int b = (int)(a.getBlue() * p + c.getBlue() * (1-p));
		return new Color(r,g,b);
	}
}
