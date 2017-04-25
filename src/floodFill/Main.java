package floodFill;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

import tileMain.ConstructorClass;

//need for music and sound

public class Main extends ConstructorClass {	
	public static Random rand = new Random();
	public static int defaultWidth = 800;
	public static int defaultHeight = 800;
	public static int[] nodes = new int[8];
	public static boolean isDebug = false;
	public static TectonicPlate[] plates = new TectonicPlate[8];
	
	public void doInitialization(int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		
		System.out.println("Creating Tiles");
		for(int row = 0; row<defaultHeight/Tile.size; row++){
			for(int column = 0; column<defaultWidth/Tile.size; column++){
				new Tile(column*Tile.size,row*Tile.size,new int[]{column,row});
			}
		}
		
		plates[0] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.RED);
		plates[1] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.GREEN);
		plates[2] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.BLUE);
		plates[3] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.YELLOW);
		plates[4] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.WHITE);
		plates[5] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.ORANGE);
		plates[6] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.CYAN);
		plates[7] = new TectonicPlate(pickRandomTile(), rand.nextInt(11) - 5, Color.MAGENTA);
		
		// Pick 3 random nodes to start fill commands
		System.out.println("Picking Nodes");
		for(int i = 0; i<nodes.length; i++){
			nodes[i] = pickRandomTile();
		}
	
		Tile.tilesToColor = Tile.allTiles.size();
		
		//Iterate through first 90% of tiles
		System.out.println("General Iteration");
		while(Tile.tilesToColor > Tile.allTiles.size()*1/64){
			for(int n = 0; n<plates.length; n++){
				plates[n].generalFillIteration();
			}
		}
		
		// Due to time constraints, the last 1/16 of tiles will be determined by the tiles next to them
		System.out.println("Pick and Choose Iteration");
		while(Tile.tilesToColor >= 0){
			int coordNum = -1;
			for(int i = 0; i<Tile.allTiles.size();i++){
				if(Tile.allTiles.get(i).tectonicPlate == null){
					coordNum = i;
					System.out.println(Tile.tilesToColor);
					break;
				}
			}
			
			if(coordNum == -1 || coordNum == 0){
				break;
			}
			
			boolean passed = false;
			while(!passed){
				int dir = rand.nextInt(4);
				int newTile = 0;
				System.out.println(coordNum);
				try{
					switch(dir){
						case 0: newTile = coordNum - (defaultWidth/Tile.size); break;
						case 1: newTile = coordNum + 1; break;
						case 2: newTile = coordNum + (defaultWidth/Tile.size); break;
						case 3: newTile = coordNum - 1; break;
					}
					
					if(!(Tile.allTiles.get(newTile).tectonicPlate == null)){
						Tile.tilesToColor--;
						Tile.allTiles.get(coordNum).tectonicPlate = Tile.allTiles.get(newTile).tectonicPlate;
						passed = true;
					}
					
				}catch(IndexOutOfBoundsException e){};
			}
		}
		
		TectonicPlate.assignHeightColor();
		
		System.out.println("Adding noise");
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