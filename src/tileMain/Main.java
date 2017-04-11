package tileMain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Random;

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

		float[][] perlin = new Perlin().generateMultiOctavePerlinNoise(6, .35, 800, 800);
		
		for(int i = 0; i<perlin.length; i++){
			for(int j=0; j<perlin[i].length;j++){
				/*
				Color c = Color.GREEN;
				try{
					c = Gradient.getColorAtPoint(Color.black, Color.green, perlin[i][j], 0, 1);
				}
				catch(IllegalArgumentException e){
					System.out.println(perlin[i][j]);
				}
				*/
				if(perlin[i][j] < .2){
					Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c =  Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(new Color(120,120,120),new Color(60,60,60),(double)perlin[i][j],(double)0,(double).2);
				}
				if(perlin[i][j] > .2 && perlin[i][j] <= .5){
			        Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c =  Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(Color.white,new Color(60,60,60),(double)perlin[i][j],(double).2,(double).5);
			    }
			    if(perlin[i][j] > .5){
			        Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c =  Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(Color.white,Color.orange,(double)perlin[i][j],(double).5,(double)1);
			    }
	
				//Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c= Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = c;
				
			}
		}
		System.out.println("FINISHED!!!");
		/*
		for (int i = 0; i < perlin.length; i++) {
	        for (int j = 0; j < perlin[i].length; j++){
	        	if(perlin[i][j] < .5){
	        		Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c =  Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(new Color(0,0,196),new Color(0,0,96),(double)perlin[i][j],(double)0,(double).5);
	        	}
	        	else if(perlin[i][j] >= .5 && perlin[i][j] < .515){
	        		 Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c= new Color(209,203,169);
	        	}
	        	else if(perlin[i][j] >= .515 && perlin[i][j] < .6){
	        		 Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c= Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(new Color(43,77,0),new Color(84,150,0),(float)perlin[i][j],(float).515,(float).6);
	        	}
	        	else if(perlin[i][j] >= .6 && perlin[i][j] < .65){
	        		Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c= Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(new Color(50,50,50),new Color(43,77,0),(float)perlin[i][j],(float).6,(float).65);
	        	}
	        	else if(perlin[i][j] >= .65 && perlin[i][j] < .8){
	        		Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c= Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c = Gradient.getColorAtPoint(new Color(120,120,120),new Color(30,30,30),(float)perlin[i][j],(float).65,(float).8);
	        	}
	        	else{
	        		Tile.allTiles.get(i*(defaultWidth/Tile.size)+j).c= new Color((int)(255*perlin[i][j]),(int)(255*perlin[i][j]),(int)(255*perlin[i][j]));
	        	}
	      
	        }
	    }
	    
		for(int i = 0; i<Tile.allTiles.size(); i++){
			Tile.allTiles.get(i).addNoise();
		}
		*/
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



