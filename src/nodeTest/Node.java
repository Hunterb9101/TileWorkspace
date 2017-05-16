package nodeTest;

import java.awt.Color;
import java.util.ArrayList;

public class Node {
	String name;
	public static int count = 0;
	int id;
	int x;
	int y;
	Color c = Color.green;
	public Node(String name, int x, int y){
		this.name = name;
		this.x = x;
		this.y = y;
		id = count;
		count++;
	}
	
	public static double dist(Node a, Node b){
		return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y,2));
	}
}
