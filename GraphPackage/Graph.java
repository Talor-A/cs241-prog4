package GraphPackage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.LinkedList;

public class Graph<T> implements GraphInterface<T> {

	private HashMap<T, VertexInterface<T>> vertices;
	private int edgeCount;

	public Graph() {
		vertices = new HashMap<>();
		edgeCount = 0;
	}

	public boolean addEdge(T begin, T end, double weight) {
		VertexInterface<T> beginVert = vertices.get(begin);
		VertexInterface<T> endVert = vertices.get(end);

		if (beginVert == null || endVert == null) return false;

		boolean edgeAdded = beginVert.connect(endVert, weight) && endVert.connect(beginVert, weight);
		if (edgeAdded) edgeCount++;
		return edgeAdded;
	}

	public boolean addEdge(T begin, T end) {
		return addEdge(begin, end, 0);
	}

	public boolean hasEdge(T begin, T end) {
		boolean found = false;

		VertexInterface<T> beginVert = vertices.get(begin);
		VertexInterface<T> endVert = vertices.get(end);

		if ((beginVert != null) && (endVert != null)) {
			Iterator<VertexInterface<T>> neighbors = beginVert.getNeighborIterator();
			while (!found && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVert.equals(nextNeighbor)) {
					found = true;
				}
			}
		}
		return found;
	}
	public boolean removeEdge(T begin, T end) {
		VertexInterface<T> beginVert = vertices.get(begin);
		VertexInterface<T> endVert = vertices.get(end);
		if (beginVert == null || endVert == null) return false;
		if (!hasEdge(begin, end) || !hasEdge(end, begin)) return false;

		return beginVert.disconnect(endVert) && endVert.disconnect(beginVert);
	}
	public int getNumberOfEdges() {
		return edgeCount;
	}

	public boolean addVertex(T vertexLabel) {
		VertexInterface<T> addOutcome = vertices.put(vertexLabel, new Vertex<>(vertexLabel));
		return addOutcome == null;
	}

	public int getNumberOfVertices() {
		return vertices.size();
	}

	protected void resetVertices() {
		Iterator<VertexInterface<T>> vertexIterator = vertices.values().iterator();

		while (vertexIterator.hasNext()) {
			VertexInterface<T> nextVert = vertexIterator.next();
			nextVert.unvisit();
			nextVert.setCost(0);
			nextVert.setPredecessor(null);
		}
	}

	public double shortestPath(T begin, T end) {
		VertexInterface<T> beginVert = vertices.get(begin);
		VertexInterface<T> endVert = vertices.get(end);
		PriorityQueue<VertexInterface<T>> pq = new PriorityQueue();

		for (VertexInterface<T> vert : vertices.values()) {
			vert.setCost(Double.POSITIVE_INFINITY);
			if (vert == beginVert) vert.setCost(0);
			vert.unvisit();
			vert.setPredecessor(null);
			pq.offer(vert);
		}
		VertexInterface<T> current = pq.poll();
		System.out.println("starting at " + current.getLabel() + " cost " + current.getCost());
		while (!pq.isEmpty()) {
			while (current.isVisited()) {
				current = pq.poll();
			}
			current.visit();
			VertexInterface<T> neighbor;
			Iterator<VertexInterface<T>> neighbors = current.getNeighborIterator();
			while (neighbors.hasNext()) {
				neighbor = neighbors.next();
				if (!neighbor.isVisited()) {

					double edgeCost = current.distanceTo(neighbor);
					System.out.println("dist from " + current.getLabel() + "=>" + neighbor.getLabel() + "=" + current.distanceTo(neighbor));
					if (edgeCost == -1) System.out.println("Error! neighbor not connected to current!");
					if (edgeCost + current.getCost() < neighbor.getCost()) {
						System.out.println("new shortest path found");
						neighbor.setCost(edgeCost + current.getCost());
						neighbor.setPredecessor(current);
					}
				}
			}

		}

		// return beginVert.distanceTo(endVert);
		return endVert.getCost();

	}
	public String pathTo(T end) {
		return pathTo(vertices.get(end));
	}
	private String pathTo(VertexInterface<T> endVert) {
		VertexInterface<T> current = endVert;
		String path = "";
		while (current != null) {
			path = current.getLabel() + " " + path;
			current = current.getPredecessor();
		}
		return path;
	}
	// public T[] pathTo(T end) {
	// 	return (T[])pathTo(vertices.get(end));
	// }
	// private T[] pathTo(VertexInterface<T> endVert) {
	// 	T[] path = (T[])new Object[vertices.size()];
	// 	VertexInterface<T> current = endVert;
	// 	int index = 0;
	// 	while (current != null) {
	// 		path[index] = current.getLabel();
	// 		current = current.getPredecessor();
	// 		index++;
	// 	}
	// 	T[] fwdPath = (T[])new Object[index];
	// 	for (int i = 0; i < index; i++) {
	// 		fwdPath[i] = fwdPath[(index - 1) - i];
	// 	}
	// 	return fwdPath;
	// }


	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	public void clear() {
		vertices.clear();
		edgeCount = 0;
	}
}