package tileMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import noiseFunctions.Perlin;
import tileMain.Tile.ColorSchemes;

//need for music and sound

public class Main extends ConstructorClass {	
	Random rand = new Random();
	public static int defaultWidth = 800;
	public static int defaultHeight = 800;
	TileGroup planet = new TileGroup();
	TileGroup clouds = new TileGroup();
	public void doInitialization(int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		
		for(int row = 0; row<defaultHeight/Tile.size; row++){
			for(int column = 0; column<defaultWidth/Tile.size; column++){
				planet.allTiles.add(new Tile(column*Tile.size,row*Tile.size,new int[]{column,row}));
			}
		}
		
		for(int row = 0; row<defaultHeight/Tile.size; row++){
			for(int column = 0; column<defaultWidth/Tile.size; column++){
				clouds.allTiles.add(new Tile(column*Tile.size,row*Tile.size,new int[]{column,row}));
			}
		}

		float[][] mountainousPerlin = new Perlin().generateMultiOctaveRidgedPerlinNoise(8, .5, 800, 800);
		mountainousPerlin = new Perlin().scaleNBias(mountainousPerlin, 1, .55);
		
		float[][] plainsPerlin = new Perlin().generateMultiOctavePerlinNoise(8, .5, 800, 800);
		plainsPerlin = new Perlin().scaleNBias(plainsPerlin, .75, .25);
		
		float[][] seaPerlin = new Perlin().generateMultiOctaveRidgedPerlinNoise(8, .5, 800, 800);
		seaPerlin = new Perlin().scaleNBias(seaPerlin, .5,0);
		
		float[][] perlin = new Perlin().generateMultiOctavePerlinNoise(10, .5, 800, 800); // Selector Perlin
		float[][] finalHeightMap = new float[perlin.length][perlin[0].length];
		
		
		for(int i = 0; i<perlin.length; i++){
			for(int j = 0; j<perlin[i].length;j++){
				if(perlin[i][j] < .20){
					finalHeightMap[i][j] = seaPerlin[i][j];
				}
				else if(perlin[i][j] > .20 && perlin[i][j] < .24){
					finalHeightMap[i][j] = (float) Gradient.getValueAtPoint(seaPerlin[i][j], plainsPerlin[i][j], perlin[i][j], .2f, .28f);
				}
				else if(perlin[i][j] > .24 && perlin[i][j] < .26){
					finalHeightMap[i][j] = plainsPerlin[i][j];
				}
				else if(perlin[i][j] > .26 && perlin[i][j] < .32){
					finalHeightMap[i][j] = (float) Gradient.getValueAtPoint(plainsPerlin[i][j], mountainousPerlin[i][j], perlin[i][j], .26f, .32f);
				}
				else{
					finalHeightMap[i][j] = mountainousPerlin[i][j];
				}
			}
		}
		planet.setColors(ColorSchemes.NORMAL, finalHeightMap);
		clouds.setColors(ColorSchemes.NORMAL, mountainousPerlin);
		
		for(int i = 0; i<planet.allTiles.size(); i++){
			planet.allTiles.get(i).addNoise();
		}
		
	} // doInitialization

	// All drawing is done here //
	synchronized public void drawFrame(Graphics g, int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		g.fillRect(0, 0, defaultWidth, defaultHeight);
		for(int i = 0; i<planet.allTiles.size();i++){
			g.setColor(planet.allTiles.get(i).c);
			g.fillRect(planet.allTiles.get(i).sphericalX,planet.allTiles.get(i).sphericalY, Tile.size, Tile.size);
		}
		
		for(int i = 0; i<clouds.allTiles.size();i++){
				g.setColor(clouds.allTiles.get(i).c);
				g.fillRect(clouds.allTiles.get(i).sphericalX,clouds.allTiles.get(i).sphericalY, Tile.size, Tile.size);
			
		}
	}

	public void mousePressed(MouseEvent evt) {
		super.mousePressed(evt);
	}

	public void keyPressed(KeyEvent evt) {	
	}

	public void keyReleased(KeyEvent evt){
	}
}



