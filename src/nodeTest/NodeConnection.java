package nodeTest;

import java.awt.Color;
import java.util.ArrayList;

public class NodeConnection {
	public static ArrayList<NodeConnection> allConnections = new ArrayList<>();
	Node node1;
	Node node2;
	int id;
	public static int cnt = 0;
	Color c = Color.blue;
	public NodeConnection(Node a, Node b) throws Exception{
		node1 = a;
		node2 = b;
		boolean passed = true;
		for(int i = 0; i<allConnections.size(); i++){
			if(allConnections.get(i).node1.id == node1.id && allConnections.get(i).node2.id == node2.id ||
					allConnections.get(i).node1.id == node2.id && allConnections.get(i).node2.id == node1.id){
					throw new Exception();
			}
			
		}
		if(passed){
			id = cnt;
			cnt++;
			allConnections.add(this);
		}
		
	}
}
