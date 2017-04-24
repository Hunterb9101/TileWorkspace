package tileMain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Tile {
	public static ArrayList<Tile> allTiles = new ArrayList<Tile>();
	public static int tilesToColor = 0;
	public static int size = 1;
	
	public int x;
	public int y;
	
	public int sphericalX;
	public int sphericalY;
	public int sphericalZ;
	
	public int[] coords;
	public Color c = null;
	public enum ColorSchemes{NORMAL,HEIGHTDEBUG,PERLINSELECTOR};
	public TectonicPlate tectonicPlate = null;
	Random rand = new Random();
	
	public Tile(int x, int y, int[] coords){
		this.x = x;
		this.y = y;
		this.coords = coords;
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
	
	public static void setColors(ColorSchemes colorScheme, float[][] perlin){
		for(int i = 0; i<perlin.length; i++){
			for(int j = 0;j<perlin[i].length;j++){
				Color c = Color.red;
				
				switch(colorScheme){
				case NORMAL:
					if(perlin[i][j] < .25){
						c = Gradient.getColorAtPoint(new Color(0,0,196), new Color(0,0,96),perlin[i][j], 0, .25, 1);
					}
					else if(perlin[i][j] > .25 && perlin[i][j] < .4){
						c = Gradient.getColorAtPoint(new Color(52,172,0), new Color(0,96,0),perlin[i][j], .25, .45, 3);
					}
					else if(perlin[i][j] > .4 && perlin[i][j] < .5){
						c = Gradient.getColorAtPoint(new Color(52,128,0), new Color(52,172,0),perlin[i][j], .4, .5, 1);
					}
					else if(perlin[i][j] > .5 && perlin[i][j] < .65){
						c = Gradient.getColorAtPoint(new Color(60,60,60), new Color(52,128,0),perlin[i][j], .5, .7, 6);
					}
					else if(perlin[i][j] > .65 && perlin[i][j] < .85){
						c = Gradient.getColorAtPoint(new Color(140,140,140), new Color(60,60,60),perlin[i][j], .65, .85, 6);
					}
					else if(perlin[i][j] > .85){
						c = Gradient.getColorAtPoint(new Color(210,210,210), new Color(235,235,235),perlin[i][j], .85, 1, 6);
					}
					break;
					
				case HEIGHTDEBUG:
					try{
						c = new Color((int)(perlin[i][j]*255),(int)(perlin[i][j]*255),(int)(perlin[i][j]*255));
					}catch(IllegalArgumentException e){}
					break;
				
				case PERLINSELECTOR:
					if(perlin[i][j] < .22){
						c = Color.blue;
					}
					else if(perlin[i][j] > .22 && perlin[i][j] < .28){
						c = Color.green;
					}
					else{
						c = Color.gray;
					}
					break;
				}
				allTiles.get(i*perlin[i].length + j).c = c;
				
			}
		}
	}
	
	public void generateSphericalCoords(){
		int R = 400;
		int S = 400;
		
		float longitude = (float)(this.x)/R;
		float latitude = (float) (2*Math.atan(Math.exp((double)(this.y)/R)) - Math.PI/2);
		sphericalX = (int) (S*Math.cos(latitude) * Math.cos(longitude)) + 300;
		sphericalY = (int) (S*Math.cos(latitude) * Math.sin(longitude)) + 300;
		sphericalZ = (int) (S*Math.sin(longitude));
		//System.out.println(sphericalX + " " + sphericalY + " " + sphericalZ);
	}
	
}
