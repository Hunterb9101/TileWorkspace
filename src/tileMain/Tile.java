package tileMain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import floodFill.TectonicPlate;

public class Tile {
	public static ArrayList<Tile> allTiles = new ArrayList<Tile>();
	public static int size = 1;
	
	public int x;
	public int y;
	
	public int sphericalX;
	public int sphericalY;
	public int sphericalZ;
	
	public Color c = null;
	public enum ColorSchemes{NORMAL,HEIGHTDEBUG,PERLINSELECTOR};
	Random rand = new Random();
	
	public Tile(int x, int y, int[] coords){
		this.x = x;
		this.y = y;
		generateSphericalCoords();
		allTiles.add(this);
	}
	
	public void addNoise(){
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		int variance = 20;
		
		r += rand.nextInt(variance) - variance/2;
		g += rand.nextInt(variance) - variance/2;
		b += rand.nextInt(variance) - variance/2;
		
		if(r < 0){
			r = 0;
		}
		if(g<0){
			g = 0;
		}
		if(b<0){
			b = 0;
		}
		if(r > 255){
			r = 255;
		}
		if(g > 255){
			g = 255;
		}
		if(b > 255){
			b = 255;
		}
		c = new Color(r,g,b);
	}
	
	public void generateSphericalCoords(){
		int R = 400;
		int S = 125;
		
		float longitude = (float) ((float)(Math.PI*this.x)/R);
		float latitude = (float) (8.75*Math.atan(Math.exp((float)(this.y)/R)));
		sphericalX = (int) ((float)(S*Math.cos(latitude) * Math.cos(longitude))) + 600;
		sphericalY = (int) (S*Math.cos(latitude) * Math.sin(longitude)) + 200;
		sphericalZ = (int) (S*Math.sin(longitude)) + 300;
	}
}
