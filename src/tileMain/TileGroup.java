package tileMain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import tileMain.Tile.ColorSchemes;

public class TileGroup {
	public List<Tile> allTiles = new ArrayList<>();
	public String name = "";
	
	public void setColors(ColorSchemes colorScheme, float[][] perlin){
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
				allTiles.get(i*perlin[i].length + j).addNoise();
			}
		}
	}
}
