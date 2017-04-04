package tileMain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Tile {
	public static ArrayList<Tile> allTiles = new ArrayList<Tile>();
	public static int tilesToColor = 0;
	public static int size = 10;
	public int x;
	public int y;
	public int[] coords;
	public Color c = Color.red;
	Random rand = new Random();
	
	public Tile(int x, int y, int[] coords){
		this.x = x;
		this.y = y;
		this.coords = coords;
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
		c = new Color(r,g,b);
	}
}
