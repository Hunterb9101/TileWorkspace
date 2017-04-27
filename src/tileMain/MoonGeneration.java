package tileMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import noiseFunctions.Perlin;
import tileMain.Tile.ColorSchemes;

//need for music and sound

public class MoonGeneration extends ConstructorClass {	
	Random rand = new Random();
	public static int defaultWidth = 800;
	public static int defaultHeight = 800;
	public static boolean isDebug = false;
	
	TileGroup planet = new TileGroup();
	TileGroup stars = new TileGroup();
	
	public void doInitialization(int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		/*Planet s = new Planet();
		s.createMesh(6);

		
		s.draw(g);*/
		System.out.println("Creating Tiles");
		for(int row = 0; row<defaultHeight/Tile.size; row++){
			for(int column = 0; column<defaultWidth/Tile.size; column++){
				planet.allTiles.add(new Tile(column*Tile.size,row*Tile.size,new int[]{column,row}));
			}
		}
		for(int row = 0; row<defaultHeight/Tile.size; row++){
			for(int column = 0; column<defaultWidth/Tile.size; column++){
				stars.allTiles.add(new Tile(column*Tile.size,row*Tile.size,new int[]{column,row}));
			}
		}
		
		float[][] starPerlin = new Perlin().generateMultiOctaveRidgedPerlinNoise(4, .5, 800, 800);
		
		float[][] plainsPerlin = new Perlin().generateMultiOctavePerlinNoise(8, .5, 800, 800);
		plainsPerlin = new Perlin().scaleNBias(plainsPerlin, 1.4, 0); // .75,.25
		
		for(int i = 0; i<starPerlin.length; i++){
			for(int j = 0;j<starPerlin[i].length;j++){
				if(starPerlin[i][j] < .007){
					starPerlin[i][j] = 1 - starPerlin[i][j];
				}
				else{
					starPerlin[i][j] = 0;
				}
			}
		}
		
		planet.setColors(ColorSchemes.NORMAL, plainsPerlin);
		stars.setColors(ColorSchemes.HEIGHTDEBUG, starPerlin);
		
	} // doInitialization

	// All drawing is done here //
	synchronized public void drawFrame(Graphics g, int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		g.setColor(Color.black);
		g.fillRect(0, 0, defaultWidth, defaultHeight);
		
		for(int i = 0; i<stars.allTiles.size(); i++){
			g.setColor(stars.allTiles.get(i).c);
			g.fillRect(stars.allTiles.get(i).x,stars.allTiles.get(i).y, Tile.size, Tile.size);
			
		}
		for(int i = 0; i<planet.allTiles.size(); i++){
			g.setColor(planet.allTiles.get(i).c);
			g.fillRect(planet.allTiles.get(i).sphericalX,planet.allTiles.get(i).sphericalY, Tile.size, Tile.size);
		}
	}

	public void mousePressed(MouseEvent evt) {
		super.mousePressed(evt);
	}

	public void keyPressed(KeyEvent evt) {	
	}

	public void keyReleased(KeyEvent evt){
	}
	
	public int pickRandomTile(){
		int x = rand.nextInt(defaultWidth/Tile.size);
		int y = rand.nextInt(defaultHeight/Tile.size);
		return x + (int)(y*(defaultWidth/Tile.size));
	}
}



