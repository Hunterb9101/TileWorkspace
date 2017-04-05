package tileMain;

import java.awt.Color;
import java.util.Random;

public class FillPlate {
	public Random rand = new Random();
	// Needed for Flood Fill
	public int nodeLocation;
	public Color debugColor;
	
	public int center;
	public int elevation;
	
	public int magnitude;
	public int angle;
	
	public FillPlate(int nodeLocation, int elevation, Color debugColor){
		this.nodeLocation = nodeLocation;
		center = nodeLocation;
		this.debugColor = debugColor;
		Tile.allTiles.get(nodeLocation).c = debugColor;
		this.elevation = elevation;
	}
	
	public void generalFillIteration(){
		boolean passed = false;
		while(!passed){
			int dir = rand.nextInt(4);
			int newTile;
			try{
				switch(dir){
					case 0: newTile = nodeLocation - (Main.defaultWidth/Tile.size); break;
					case 1: newTile = nodeLocation + 1; break;
					case 2: newTile = nodeLocation + (Main.defaultWidth/Tile.size); break;
					case 3: newTile = nodeLocation - 1; break;
					default: newTile = -1; break;
				}
				
				if(Tile.allTiles.get(newTile).tectonicPlate == this || Tile.allTiles.get(newTile).tectonicPlate == null){
					if(Tile.allTiles.get(newTile).tectonicPlate == null){
						Tile.tilesToColor--;
					}
					Tile.allTiles.get(newTile).c = debugColor;
					nodeLocation = newTile;
					passed = true;
				}
				
			}catch(IndexOutOfBoundsException e){};
		}
	}
}
