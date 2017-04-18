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

		float[][] mountainousPerlin = new Perlin().generateMultiOctaveRidgedPerlinNoise(8, .5, 800, 800);
		mountainousPerlin = new Perlin().scaleNBias(mountainousPerlin, 1, .55);
		
		float[][] plainsPerlin = new Perlin().generateMultiOctavePerlinNoise(8, .5, 800, 800);
		plainsPerlin = new Perlin().scaleNBias(plainsPerlin, .75, .25);
		
		float[][] seaPerlin = new Perlin().generateMultiOctaveRidgedPerlinNoise(8, .5, 800, 800);
		seaPerlin = new Perlin().scaleNBias(seaPerlin, .5,0);
		
		float[][] selectorPerlin = new Perlin().generateMultiOctavePerlinNoise(10, .5, 800, 800);
		float[][] perlin = selectorPerlin;
		
		float[][] finalHeightMap = new float[selectorPerlin.length][selectorPerlin[0].length];
		
		for(int i = 0; i<perlin.length; i++){
			for(int j = 0; j<perlin[i].length;j++){
				if(perlin[i][j] < .22){
					finalHeightMap[i][j] = seaPerlin[i][j];
				}
				else if(perlin[i][j] > .22 && perlin[i][j] < .28){
					finalHeightMap[i][j] = plainsPerlin[i][j];
				}
				else{
					finalHeightMap[i][j] = mountainousPerlin[i][j];
				}
			}
		}
		Tile.setColors(ColorSchemes.NORMAL, finalHeightMap);
		
		for(int i = 0; i<finalHeightMap.length; i++){
			for(int j = 0; j<finalHeightMap[i].length;j++){			
				try{
					while
					if(Math.abs(finalHeightMap[i][j] - finalHeightMap[i][j-1]) > .015){
						finalHeightMap[i][j] = (finalHeightMap[i][j] - finalHeightMap[i][j-1])/2;
					}
					if(Math.abs(finalHeightMap[i][j] - finalHeightMap[i][j+1]) > .015){
						finalHeightMap[i][j] = (finalHeightMap[i][j] - finalHeightMap[i][j+1])/2;
					}
					if(Math.abs(finalHeightMap[i+1][j] - finalHeightMap[i][j]) > .015){
						finalHeightMap[i][j] = (finalHeightMap[i][j] - finalHeightMap[i+1][j])/2;
					}
					if(Math.abs(finalHeightMap[i-1][j] - finalHeightMap[i][j]) > .015){
						Tile.allTiles.get(i*finalHeightMap[i].length+ j).c = Color.RED;
						finalHeightMap[i][j] = (finalHeightMap[i][j] - finalHeightMap[i-1][j])/2;
					}
				}
				catch(IndexOutOfBoundsException e){}
			}
		}
		
		for(int i = 0; i<Tile.allTiles.size(); i++){
			Tile.allTiles.get(i).addNoise();
		}
		
	} // doInitialization

	// All drawing is done here //
	synchronized public void drawFrame(Graphics g, int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		g.fillRect(0, 0, defaultWidth, defaultHeight);
		for(int i = 0; i<Tile.allTiles.size();i++){
			g.setColor(Tile.allTiles.get(i).c);
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



