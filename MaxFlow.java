import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
 
public class MaxFlow {
 
	
	public static void main(String args[]) {
		
		Map<Character, Integer> nodeToIdx = new HashMap<>();
		for (int i = 65; i <= (int)'O'; i++) {
			nodeToIdx.put((char)i, i-65);
		}
		nodeToIdx.put('S', 15);
		nodeToIdx.put('T', 16);
		
		Graph g = new Graph<>(17);
		
		Scanner r = new Scanner(System.in);
		
		int edges = r.nextInt();
		while (edges > 0) {
			
			g.addEdge(nodeToIdx.get(r.next().charAt(0)), 
					nodeToIdx.get(r.next().charAt(0)), 
					r.nextInt());
			edges--;
		}
		
		System.out.println(g.getMaxFlow(15, 16));
		r.close();
	}
	
	
	static class Graph<T> {
		
		Map<Integer, Vertex> vertexIdToVertexMap;
		
		List<Map<Integer, Integer>> adjList;
		
		int numOfNodes;
		
		boolean isDirected;
		
		Graph(int numOfNodes) {
			this(numOfNodes, false);
		}
		
		Graph(int numOfNodes, boolean isDirected) {
			this.numOfNodes = numOfNodes;
			vertexIdToVertexMap = new HashMap<>();
			
			this.adjList = new ArrayList<>();
			for (int i = 0; i < numOfNodes; i++) {
				vertexIdToVertexMap.put(i, new Vertex(i));
				adjList.add(new HashMap<Integer, Integer>());
			}
			
			this.isDirected = isDirected;
		}
		
		public void addEdge( int from, int to, int wt) {
			
			adjList.get(from).put(to, wt);
			
			//Opposite direction does not exist.
			adjList.get(to).put(from, 0);
			
		}
		
		public long getMaxFlow(int source, int sink) {
			
			long maxFlow = 0;
			boolean isBFSPossibleFromSourceNode = true;
			
			while (isBFSPossibleFromSourceNode) {
				
				
				boolean visited[] = new boolean[numOfNodes];
				Map<Integer, Integer> childToParent = new HashMap<>();
				
				Queue<Integer> queue = new LinkedList<>();
				queue.add(source);
				visited[source] = true;
				while (!queue.isEmpty()) {
					
					int node = queue.poll();
					
					Map<Integer, Integer> edgeToWtMap = adjList.get(node);
					for (int toVertex : edgeToWtMap.keySet()) {
						if (visited[toVertex] == false  && edgeToWtMap.get(toVertex) > 0) {
							queue.add(toVertex);
							childToParent.put(toVertex, node);
							visited[node] = true;
							if (node == sink) {
								break;
							}
						}
					}
				}
				
				int minDist = Integer.MAX_VALUE;
				
				int child = sink;
				if (childToParent.get(child) == null) {
					break;
				}
				int parent = childToParent.get(child);
				while (parent != source) {
					if (minDist > adjList.get(parent).get(child)) {
						minDist = adjList.get(parent).get(child);
					}
					child = parent;
					parent = childToParent.get(child);
				}
				if (minDist > adjList.get(parent).get(child)) {
					minDist = adjList.get(parent).get(child);
				}
				
				child = sink;
				parent = childToParent.get(child);
 
				while (parent != source) {
					
					int dist = adjList.get(parent).get(child);
					adjList.get(parent).put(child, dist - minDist);
					
					dist = adjList.get(child).get(parent);
					adjList.get(child).put(parent, dist + minDist);
					
					child = parent;
					parent = childToParent.get(child);
				}
				
				int dist = adjList.get(parent).get(child);
				adjList.get(parent).put(child, dist - minDist);
				
				dist = adjList.get(child).get(parent);
				adjList.get(child).put(parent, dist + minDist);
				
				maxFlow += minDist;
				//Check whether BFS is poosible from source node or not 
				//and set isBFSPossibleFromSourceNode flag.
 
				Map<Integer, Integer> m = adjList.get(source);
				boolean isEdgeFound = false;
				for (int toVertex : m.keySet()) {
					if (m.get(toVertex) > 0) {
						isEdgeFound = true;
						break;
					}
				}
				
				isBFSPossibleFromSourceNode = isEdgeFound;
			}
			
			return maxFlow;
		}
	}
	
	static class Vertex {
		
		int nodeVal;
 
		public Vertex(int nodeVal) {
			super();
			this.nodeVal = nodeVal;
		}
		
		
		
	}
	
	static class Edge {
		int from;
		int to;
		int weight;
		
		public Edge(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}
	}
}