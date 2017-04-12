package tileMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import tileMain.Tile.ColorSchemes;

//need for music and sound

public class Main extends ConstructorClass {	
	Random rand = new Random();
	public static int defaultWidth = 800;
	public static int defaultHeight = 800;
	public static boolean isDebug = false;
	
	public void doInitialization(int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		
		System.out.println("Creating Tiles");
		for(int row = 0; row<defaultHeight/Tile.size; row++){
			for(int column = 0; column<defaultWidth/Tile.size; column++){
				new Tile(column*Tile.size,row*Tile.size,new int[]{column,row});
			}
		}

		float[][] mountainousPerlin = new Perlin().generateMultiOctavePerlinNoise(8, .35, 800, 800);
		mountainousPerlin = new Perlin().scaleNBias(mountainousPerlin, 1, .5);
		
		float[][] plainsPerlin = new Perlin().generateMultiOctavePerlinNoise(8, .35, 800, 800);
		plainsPerlin = new Perlin().scaleNBias(plainsPerlin, .75, .25);
		
		float[][] seaPerlin = new Perlin().generateMultiOctavePerlinNoise(8, .35, 800, 800);
		seaPerlin = new Perlin().scaleNBias(mountainousPerlin, .5, 0);
		
		float[][] selectorPerlin = new Perlin().generateMultiOctavePerlinNoise(9, .4, 800, 800);
		float[][] perlin = selectorPerlin;
		
		
		Tile.setColors(ColorSchemes.NORMAL, mountainousPerlin);
		System.out.println("FINISHED!!!");
		
		for(int i = 0; i<Tile.allTiles.size(); i++){
			Tile.allTiles.get(i).addNoise();
		}
		
	} // doInitialization

	// All drawing is done here //
	synchronized public void drawFrame(Graphics g, int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, defaultWidth, defaultHeight);
		for(int i = 0; i<Tile.allTiles.size();i++){
			if(isDebug){
				g.setColor(Tile.allTiles.get(i).tectonicPlate.debugColor);
			}
			else{
				g.setColor(Tile.allTiles.get(i).c);
			}
			g.fillRect(Tile.allTiles.get(i).x,Tile.allTiles.get(i).y, Tile.size, Tile.size);
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



