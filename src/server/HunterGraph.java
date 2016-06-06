package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.FloydWarshallShortestPaths;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import processing.core.PVector;

public class HunterGraph {
	private UndirectedGraph<PVector, DefaultEdge> graph;
	private int numOfVertex;
	private int numOfEdge;
	private FloydWarshallShortestPaths<PVector,DefaultEdge> floyd;
	
	public HunterGraph() {
		
		graph = new SimpleGraph<PVector, DefaultEdge>(DefaultEdge.class);
		ArrayList<PVector> vertices = new ArrayList<PVector>();
		
		BufferedReader reader = null;
		String line = null;
		try	{									
			reader = new BufferedReader(new FileReader("./results.txt"));
			line = reader.readLine();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		//read vertices
		numOfVertex = Integer.parseInt(line);
		for(int i=0;i<numOfVertex;i++) {
			try {
				line = reader.readLine();
				String[] tmp = line.split(" ");
				int x = (int)Float.parseFloat(tmp[0]);
				int y = (int)Float.parseFloat(tmp[1]);
				graph.addVertex(new PVector(x,y));
				vertices.add(new PVector(x,y));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		//read edges
		try {
			line = reader.readLine();
			numOfEdge = Integer.parseInt(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i=0;i<numOfEdge;i++) {
			try {
				line = reader.readLine();
				String[] tmp = line.split(" ");
				int src = Integer.parseInt(tmp[0]);
				int dst = Integer.parseInt(tmp[1]);
				graph.addEdge(vertices.get(src), vertices.get(dst));
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		floyd = new  FloydWarshallShortestPaths<PVector,DefaultEdge>(graph);
		
	}
	
	public List<PVector> getRandomPathEdgeList() {
		
		Random rand = new Random();
		PVector src=null, dst=null;
		int t = rand.nextInt(graph.vertexSet().size());
		
		int i = 0;
		for(PVector vec : graph.vertexSet()) {
			if(i == t) {
				src = vec;
			}
			i++;
		}
		
		t = rand.nextInt(graph.vertexSet().size());
		i = 0;
		for(PVector vec : graph.vertexSet()) {
			if(i == t) {
				dst = vec;
			}
			i++;
		}
		
		//DijkstraShortestPath<PVector, DefaultEdge> d = 
				//new DijkstraShortestPath<PVector, DefaultEdge> (graph, src, dst);
		
		//return d.getPathEdgeList();
		
		return floyd.getShortestPathAsVertexList(src, dst); 

	}
}
