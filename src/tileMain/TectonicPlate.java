package tileMain;

import java.awt.Color;
import java.util.Random;

public class TectonicPlate {
	public Random rand = new Random();
	// Needed for Flood Fill
	public int nodeLocation;
	public Color debugColor;
	
	public int center;
	public int elevation;
	
	public int magnitude;
	public int angle;
	
	/*
	public static Color[] pallete = new Color[]{
			new Color(0,0,140),new Color(0,0,184),new Color(68,68,207), new Color(69,91,221), new Color(69,117,221),
			new Color(229,223,189),
			new Color(190,218,0), new Color(144,218,78), new Color(129,196,70), new Color(133,167,132), new Color(133,133,133)
	};
	*/
	
	public static Color[] pallete = new Color[]{
		new Color(0,0,128), new Color(0,0,153), new Color(0,0,178), new Color(0,25,203), new Color(0,50,228),
		new Color(229,223,189),
		new Color(184,218,78), new Color(164,218,58),new Color(144,208,38), new Color(144,208,38), new Color(149,194,74), new Color(145,168,106), new Color(136,143,126)	
	};
	
	public TectonicPlate(int nodeLocation, int elevation, Color debugColor){
		this.nodeLocation = nodeLocation;
		center = nodeLocation;
		this.debugColor = debugColor;
		Tile.allTiles.get(nodeLocation).tectonicPlate = this;
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
					Tile.allTiles.get(newTile).tectonicPlate = this;
					nodeLocation = newTile;
					passed = true;
				}
				
			}catch(IndexOutOfBoundsException e){};
		}
	}
	
	public static void assignHeightColor(){
		for(int i = 0; i<Tile.allTiles.size(); i++){
			try{
				Tile.allTiles.get(i).c = pallete[Tile.allTiles.get(i).tectonicPlate.elevation + 5];
			}catch(NullPointerException e){
				System.out.println(Tile.allTiles.get(i).tectonicPlate.elevation);
			}
		}
	}
}
