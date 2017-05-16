package nodeTest;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Main extends ConstructorClass{
	BufferedImage sample;
	ArrayList<Node> nodes = new ArrayList<Node>();
	int selectedNode = -1;
	
	public void init(int width, int height){
		this.setSize(640,640);
		Random rand = new Random();

		for(int i = 0; i<30; i++){
			nodes.add(new Node("xx",rand.nextInt(560)+40,rand.nextInt(560)+40));
		}
		
		
		int prevNodeId = rand.nextInt(nodes.size());
		for(int i = 0; i<nodes.size(); i++){
			int nodeId = -1;
			double smallestDist = 640;
			double floor = 200; 
			int errCnt = 0;
			while(true){
				for(int j = 0; j<nodes.size();j++){
					if(Node.dist(nodes.get(prevNodeId), nodes.get(j)) < smallestDist && Node.dist(nodes.get(prevNodeId), nodes.get(j))>floor){
						nodeId = nodes.get(j).id;
						smallestDist = Node.dist(nodes.get(prevNodeId), nodes.get(j));
					}
				}
				
				try {
					new NodeConnection(nodes.get(prevNodeId),nodes.get(nodeId)).c = Color.CYAN;
					prevNodeId = nodeId;
					break;
				}catch(Exception e){
					floor = smallestDist;
					smallestDist = 320;
					prevNodeId = rand.nextInt(nodes.size());
					break;
				}
			}
		}
		
		
		//Basic algorithm to connect closest points
		for(int i = 0; i<nodes.size(); i++){
			double smallestDist = 320;
			int nodeId = -1;
			double floor = 0; 
			int errCnt = 0;
			while(true){
				for(int j = 0; j<nodes.size();j++){
					if(Node.dist(nodes.get(i), nodes.get(j)) < smallestDist && Node.dist(nodes.get(i), nodes.get(j))>floor){
						nodeId = nodes.get(j).id;
						smallestDist = Node.dist(nodes.get(i), nodes.get(j));
					}
				}
				
				try {
					new NodeConnection(nodes.get(i),nodes.get(nodeId)).c = Color.GREEN;
					break;
				}catch(Exception e){
					floor = smallestDist;
					smallestDist = 320;
					break;
				}
			}
		}
	}
	
	public void draw(Graphics g, int width, int height){	
		g.setColor(Color.black);
		g.fillRect(0,0,width,height);
		for(int i = 0; i<nodes.size(); i++){
			g.setColor(nodes.get(i).c);
			g.drawRect(nodes.get(i).x, nodes.get(i).y, 10, 10);
		}
		
		for(int i = 0; i<NodeConnection.allConnections.size(); i++){
			g.setColor(NodeConnection.allConnections.get(i).c);
			g.drawLine(NodeConnection.allConnections.get(i).node1.x,NodeConnection.allConnections.get(i).node1.y,NodeConnection.allConnections.get(i).node2.x,NodeConnection.allConnections.get(i).node2.y);
		}
	}
	
	public void mouseClicked(MouseEvent evt) {
		for(int i = 0; i<nodes.size(); i++){
			if(evt.getX() > nodes.get(i).x && evt.getX() < nodes.get(i).x + 10 &&
					evt.getY() > nodes.get(i).y && evt.getY() < nodes.get(i).y + 10){
				nodes.get(i).c = Color.YELLOW;
				selectedNode = i;
			}
		}
		
		for(int i = 0; i<NodeConnection.allConnections.size();i++){
			if(NodeConnection.allConnections.get(i).node1.id == selectedNode || NodeConnection.allConnections.get(i).node2.id == selectedNode){
				NodeConnection.allConnections.get(i).c = Color.RED;
			}
		}
		System.out.println("Click");
	}
}
