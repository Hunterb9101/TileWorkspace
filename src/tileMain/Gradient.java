package tileMain;

import java.awt.Color;

public class Gradient {
	Color a = null;
	Color c = null;
	public static Color getColorAtPoint(Color a, Color c, double x, double lowX, double highX){
		double p = x/highX;
		if(highX - lowX < .18){
			p = Math.pow(p, 6);
		}
		if(highX - lowX < .05){
			p = Math.pow(p, 4);
		}

		int r = (int)(a.getRed() * p + c.getRed() * (1-p));
		int g = (int)(a.getGreen() * p + c.getGreen() * (1-p));
		int b = (int)(a.getBlue() * p + c.getBlue() * (1-p));
		return new Color(r,g,b);
	}
}
