package tileMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

//need for music and sound

public class MainSquare extends ConstructorClass {	
	public static Random rand = new Random();
	public static int defaultWidth = 800;
	public static int defaultHeight = 800;
	public static int[] nodes = new int[8];
	public static boolean isDebug = false;
	public static TectonicPlate[] plates = new TectonicPlate[8];
	
	public void doInitialization(int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		
		System.out.println("Creating Tiles");
		for(int row = 0; row<defaultHeight/TileSquare.size; row++){
			for(int column = 0; column<defaultWidth/TileSquare.size; column++){
				new TileSquare(column*TileSquare.size,row*TileSquare.size,new int[]{column,row});
			}
		}
		
		/*
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
	
		TileSquare.tilesToColor = TileSquare.allTiles.size();
		
		//Iterate through first 90% of tiles
		System.out.println("General Iteration");
		while(TileSquare.tilesToColor > TileSquare.allTiles.size()*1/64){
			for(int n = 0; n<plates.length; n++){
				plates[n].generalFillIteration();
			}
		}
		
		// Due to time constraints, the last 1/16 of tiles will be determined by the tiles next to them
		System.out.println("Pick and Choose Iteration");
		while(TileSquare.tilesToColor >= 0){
			int coordNum = -1;
			for(int i = 0; i<TileSquare.allTiles.size();i++){
				if(TileSquare.allTiles.get(i).tectonicPlate == null){
					coordNum = i;
					System.out.println(TileSquare.tilesToColor);
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
						case 0: newTile = coordNum - (defaultWidth/TileSquare.size); break;
						case 1: newTile = coordNum + 1; break;
						case 2: newTile = coordNum + (defaultWidth/TileSquare.size); break;
						case 3: newTile = coordNum - 1; break;
					}
					
					if(!(TileSquare.allTiles.get(newTile).tectonicPlate == null)){
						TileSquare.tilesToColor--;
						TileSquare.allTiles.get(coordNum).tectonicPlate = TileSquare.allTiles.get(newTile).tectonicPlate;
						passed = true;
					}
					
				}catch(IndexOutOfBoundsException e){};
			}
		}
		
		TectonicPlate.assignHeightColor();
		
		System.out.println("Adding noise");
		for(int i = 0; i<TileSquare.allTiles.size(); i++){
			TileSquare.allTiles.get(i).addNoise();
		}
		*/
		float[][]noise = new Perlin().printVals(null);
		for (int i = 0; i < noise.length; i++) {
	        for (int j = 0; j < noise[i].length; j++){
	            TileSquare.allTiles.get(i*(defaultWidth/TileSquare.size)+j).c= new Color((int)(noise[i][j]*255),(int)(noise[i][j]*255),(int)(noise[i][j]*255));
	        }
	    }
	} // doInitialization

	// All drawing is done here //
	synchronized public void drawFrame(Graphics g, int width, int height) {
		this.setSize(defaultWidth,defaultHeight);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, defaultWidth, defaultHeight);
		for(int i = 0; i<TileSquare.allTiles.size();i++){
			if(isDebug){
				g.setColor(TileSquare.allTiles.get(i).tectonicPlate.debugColor);
			}
			else{
				g.setColor(TileSquare.allTiles.get(i).c);
			}
			g.fillRect(TileSquare.allTiles.get(i).x,TileSquare.allTiles.get(i).y, TileSquare.size, TileSquare.size);
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
		int x = rand.nextInt(defaultWidth/TileSquare.size);
		int y = rand.nextInt(defaultHeight/TileSquare.size);
		return x + (int)(y*(defaultWidth/TileSquare.size));
	}
}



