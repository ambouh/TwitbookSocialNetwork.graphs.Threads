/*NAME: Andres Mbouh
 *
 *HONOR PLEDGE: I pledge on my honor that I have not given or received any 
 *unauthorized assistance on this assignment/examination. 
 *
 *PURPOSE: The purpose of the Graph class is to represent a directed graph 
 *containing vertices and weighted edges. It also will implement a dijkstra 
 *algorithm; it finds the shortest path with less weight, from any vertex, to
 *any of its neighbors*/
package twitbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.lang.IllegalArgumentException;
import java.util.NoSuchElementException;

public class Graph<V extends Comparable<V>> {

	// FIELDS
	private Map<V, HashMap<V, Integer>> vertexList;

	/*
	 * Constructor - initializes vertexList into hashMap -- implementing the
	 * adjacency map
	 */
	public Graph() {
		vertexList = new HashMap<V, HashMap<V, Integer>>();
	}

	// addVertex(V) - adds a vertex to vertexList
	public void addVertex(V vertex) throws IllegalArgumentException {
		if (isVertex(vertex))
			throw new IllegalArgumentException();

		vertexList.put(vertex, new HashMap<V, Integer>());
	}

	/*
	 * isVertex(V) - determines if a particular vertex exist in the current
	 * graph object and is a boolean -- True if vertex exists and false, if
	 * vertex doesn't exist
	 */

	public boolean isVertex(V vertex) {
		return vertexList.containsKey(vertex);
	}

	/*
	 * getVertices() - returns a Collection class object, which contains all
	 * vertices in current graph object, if graph has no vertices, it returns an
	 * empty collection with no elements
	 */

	public Collection<V> getVertices() {
		return vertexList.keySet();
	}

	/*
	 * removeVertex(V) - removes vertex from current graph object. - if vertex
	 * doesn't exist in graph throws an exception - otherwise, removes vertex
	 * and
	 */

	public void removeVertex(V vertex) throws NoSuchElementException {
		if (!isVertex(vertex))
			throw new NoSuchElementException();

		vertexList.remove(vertex);
		// removes the edge associated with vertex
		for (V ver : getVertices()) {
			try {
				removeEdge(ver, vertex);
			} catch (NoSuchElementException e) {
			}
		}
	}

	/*
	 * addEdge(V, V, int) - adds an edge between source and dest with an
	 * associated cost.
	 */
	public void addEdge(V source, V dest, int cost)
			throws IllegalArgumentException {
		boolean sourceExist = isVertex(source);
		boolean destExist = isNeighbor(source, dest);

		if (cost >= 0 && !destExist) { // edge doesn't exist & cost not negative
			if (!sourceExist)
				addVertex(source);// adds new source

			// adds an edge 'dest' in source with its associated cost
			vertexList.get(source).put(dest, cost);
		} else
			// throw exception if cost is negative
			throw new IllegalArgumentException();
	}

	/*
	 * getEdgeCost(V, V) - returns the cost or weight of the edge between source
	 * and dest. return -1, if the edge between source and dest does not exist
	 */
	public int getEdgeCost(V source, V dest) {

		boolean sourceExist = isVertex(source);
		boolean destExist = isNeighbor(source, dest);
		int edgeCost;

		if (sourceExist && destExist) {
			Map<V, Integer> values = vertexList.get(source);
			edgeCost = values.get(dest);
		} else
			// assigns -1 if source doesn't exist
			edgeCost = -1;

		return edgeCost;
	}

	/*
	 * changeEdgeCost(V, V, int) - modify the cost of the edge in its current
	 * object graph that goes from the source to vertex dest
	 */
	public void changeEdgeCost(V source, V dest, int newCost)
			throws IllegalArgumentException, NoSuchElementException {

		boolean sourceExist = isVertex(source);
		boolean destExist = isNeighbor(source, dest);

		if (newCost >= 0) { // no negative cost
			if (sourceExist && destExist) { // source and dest must exist
				Map<V, Integer> values = vertexList.get(source);
				values.put(dest, newCost); // replaces cost w/ new cost
			} else
				throw new NoSuchElementException();
		} else
			throw new IllegalArgumentException();

	}

	/* removeEdge(V, V) - removes the edge on source and dest */
	public void removeEdge(V source, V dest) throws NoSuchElementException {

		boolean sourceExist = isVertex(source);
		boolean destExist = isNeighbor(source, dest);

		if (sourceExist && destExist) {
			Map<V, Integer> values = vertexList.get(source);
			values.remove(dest);
		} else
			throw new NoSuchElementException();

	}

	/* getNeighbors(V) - returns all neighbors of vertex */
	public Collection<V> getNeighbors(V vertex) throws IllegalArgumentException {
		boolean sourceExist = isVertex(vertex);

		if (!sourceExist) // source must exist
			throw new IllegalArgumentException();

		Collection<V> sourceNeighbors = vertexList.get(vertex).keySet();
		return sourceNeighbors;
	}

	/*
	 * getPredecessors(V) - return object from Collection interface which
	 * contains all of the predecessors of the vertex in its current object
	 * graph.
	 */
	public Collection<V> getPredecessors(V vertex)
			throws IllegalArgumentException {
		boolean sourceExist = isVertex(vertex);

		// stores all predecessors here
		List<V> predecessors = new ArrayList<V>();

		// new an exception if the source doesn't exist
		if (!sourceExist)
			throw new IllegalArgumentException();

		Collection<V> allVertices = getVertices(); // all vertices in graph

		for (V vert : allVertices) {
			// other vertex neighbors
			Collection<V> allNeighbors = getNeighbors(vert);

			// finds all the vertices that vertex has an incoming edge from
			if (allNeighbors.contains(vertex))
				predecessors.add(vert); // adds predecessor to list
		}

		return predecessors;
	}

	/*
	 * isClique() - returns a boolean if object graph is a clique or if every
	 * vertex has an edge to every other vertex
	 */
	public boolean isClique() {
		Collection<V> possibleClique = getVertices();

		if (possibleClique.isEmpty()) // if empty graph
			return true;

		for (V vertex : possibleClique) {
			for (V otherVertex : possibleClique) {
				if (vertex != otherVertex
						&& !vertexList.get(vertex).containsKey(otherVertex)) {
					return false;
				}
			}
		}

		return true;
	}

	/*
	 * dijkstra(V, V, List<V>) - returns the actual cost of the path between
	 * sourceVertex and destVertex. - stores the shortest path between source
	 * and dest in shortestPath
	 */
	public int dijkstra(V sourceVertex, V destVertex, List<V> shortestPath)
			throws IllegalArgumentException {

		boolean sourceExist = isVertex(sourceVertex);
		boolean destExist = isVertex(destVertex);

		if (!sourceExist || !destExist)
			throw new IllegalArgumentException();

		// empties shortest path list;
		shortestPath.removeAll(shortestPath);
		if (sourceVertex.compareTo(destVertex) == 0) {
			return 0;
		}

		HashMap<V, Integer> minCost = new HashMap<V, Integer>();
		HashMap<V, V> predecessor = new HashMap<V, V>();

		Integer infinity = Integer.MAX_VALUE;

		// sets infinity, none For other vertices
		for (V vert : getVertices()) {
			minCost.put(vert, infinity);
			predecessor.put(vert, null);
		}

		// set 0, none For Source
		minCost.put(sourceVertex, 0);
		predecessor.put(sourceVertex, null);

		HashSet<V> processed = new HashSet<V>(); // stores processed vertices
		int verticeSize = getVertices().size();

		int processedSize = 0;

		// updates processed vertex
		while (processedSize < verticeSize) {

			V currentMin = minVertex(minCost, processed);

			if (currentMin != null) {
				// get neighbors of current minimum
				Collection<V> neighbors = getNeighbors(currentMin);
				processed.add(currentMin); // add currentMin to processed

				for (V n : neighbors) {
					// processed cost + (path to N) cost
					int pathToN = minCost.get(currentMin)
							+ getEdgeCost(currentMin, n);

					// update minCost for N if (minCost for N) is > (path to N)
					if (minCost.get(n) > pathToN) {
						minCost.put(n, pathToN);
						predecessor.put(n, currentMin);
					}
				}
				processedSize = processed.size();
			} else {
				processedSize = verticeSize;
			}

		}
		int result = minCost.get(destVertex);

		// constraints result if no path exist
		result = (result != infinity) ? result : -1;

		// sets shortest path
		shortestPath = shortestPathHelper(shortestPath, destVertex,
				predecessor, sourceVertex, result);

		return result;

	}

	/*--------------------------- HELPER METHODS---------------------- */
	// Helper - finds minimum vertex that's not processed
	private V minVertex(HashMap<V, Integer> minCost, HashSet<V> processed) {
		V newMinVertex = null;
		int cost = Integer.MAX_VALUE;
		Set<V> keySet = minCost.keySet();

		for (V v : keySet) {
			if (!processed.contains(v) && (minCost.get(v) < cost)) {
				cost = minCost.get(v); // updates the next minCost
				newMinVertex = v; // updates the next minVertex
			}
		}

		// minimum vertex that's not processed now found!
		return newMinVertex;
	}

	// Helper- returns list of vertices that comprise the path b/t source & dest
	private List<V> shortestPathHelper(List<V> shortestPath, V destVertex,
			Map<V, V> predecessor, V sourceVertex, int result) {
		if (result >= 0) {
			V p = destVertex; // initial predecessor is itself, destVertex
			shortestPath.add(0, p); // adds in-front

			while (predecessor.get(p) != null) { // stops when source is found
				p = predecessor.get(p); // p assigned what came before
				shortestPath.add(0, p);// updates predecessors up until source
			}
		}

		return shortestPath;
	}

	// helper- return boolean if dest is contained in source neighbors
	boolean isNeighbor(V source, V dest) {
		Map<V, Integer> values = vertexList.get(source);
		boolean hasValues = (values != null) ? true : false; // constraints null

		if (hasValues)
			hasValues = values.containsKey(dest); // checks if neighbor exists

		return hasValues;
	}
	
	//isClique() - checks if two users are friend with each other
	protected boolean isClique(String user1, String user2) 
			throws IllegalArgumentException {
		Collection<V> possibleClique = getVertices();
		
		if (possibleClique.isEmpty()) // if empty graph
			return false;
		
		if (user1.compareTo(user2) == 0)
			throw new IllegalArgumentException();
				
		boolean TwoisFriendofOne = vertexList.get(user1).containsKey(user2);
		boolean OneisFriendofTwo = vertexList.get(user2).containsKey(user1);
		boolean result = false;
		
		if (OneisFriendofTwo && TwoisFriendofOne)
			result = true;
		
		return result;
	}

}
