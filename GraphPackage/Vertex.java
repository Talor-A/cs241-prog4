package GraphPackage;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vertex<T> implements VertexInterface<T> {

	private T label;
	private boolean visited;
	private double cost;
	private LinkedList<Edge> edgeList;
	private VertexInterface<T> previousVertex;

	/*================CONSTRUCTORS================*/
	public Vertex(T label) {
		this.label = label;
		this.visited = false;
		this.cost = 0;
		this.edgeList = new LinkedList<Edge>();
		this.previousVertex = null;
	}
	/*================MODIFY================*/
	public boolean connect(VertexInterface<T> endVertex) {
		return connect(endVertex, 0);
	}
	public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {
		boolean result = false;
		if (!this.equals(endVertex)) {
			Iterator<VertexInterface<T>> neighbors = this.getNeighborIterator();
			boolean duplicateEdge = false;

			while (!duplicateEdge && neighbors.hasNext()) {
				VertexInterface<T> nextNeighbor = neighbors.next();
				if (endVertex.equals(nextNeighbor)) {
					duplicateEdge = true;
				}
			}
			if (!duplicateEdge) {
				edgeList.add(new Edge(endVertex, edgeWeight));
				result = true;
			}
		}
		return result;
	}
	public boolean disconnect(VertexInterface<T> end) {
		Iterator<Edge> edgeIterator = edgeList.iterator();
		boolean completed = false;
		while (edgeIterator.hasNext() && !completed) {
			Edge e = edgeIterator.next();
			if (e.getEndVertex() == end) {
				edgeList.remove(e);
				completed = true;
			}
		}
		return completed;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public double getCost() {
		return this.cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}

	public VertexInterface<T> getPredecessor() {
		return this.previousVertex;
	}
	public void setPredecessor(VertexInterface<T> predecessor) {
		this.previousVertex = predecessor;
	}
	public T getLabel() {
		return this.label;
	}
	public void setLabel(T label) {
		this.label = label;
	}
	public void visit() {
		this.visited = true;
	}
	public void unvisit() {
		this.visited = false;
	}
	public boolean isVisited() {
		return this.visited;
	}

	public double distanceTo(VertexInterface<T> end) {
		Iterator<Edge> edgeIterator = edgeList.iterator();
		boolean done = false;

		while (edgeIterator.hasNext() && !done) {
			Edge e = edgeIterator.next();
			if (e.getEndVertex() == end) {
				return e.getWeight();
			}
		}
		return -1;
	}
	/*================ITERATORS================*/
	public Iterator<VertexInterface<T>> getNeighborIterator() {
		return new NeighborIterator();
	}
	public class NeighborIterator implements Iterator<VertexInterface<T>> {
		private Iterator<Edge> edges;

		private NeighborIterator() {
			edges = edgeList.iterator();
		}

		public boolean hasNext() {
			return edges.hasNext();
		}
		public VertexInterface<T> next() {
			VertexInterface<T> nextNeighbor = null;
			if (edges.hasNext()) {
				Edge edgeToNextNeighbor = edges.next();
				nextNeighbor = edgeToNextNeighbor.getEndVertex();
			} else {
				throw new NoSuchElementException();
			}
			return nextNeighbor;
		}
	}
	public Iterator<Double> getWeightIterator() {
		return new WeightIterator();
	}
	public class WeightIterator implements Iterator<Double> {
		private Iterator<Edge> edges;

		private WeightIterator() {
			edges = edgeList.iterator();
		}

		public boolean hasNext() {
			return edges.hasNext();
		}
		public Double next() {
			double weight;
			if (edges.hasNext()) {
				Edge edgeForWeight = edges.next();
				weight = edgeForWeight.getWeight();
			} else {
				throw new NoSuchElementException();
			}
			return (Double)weight;
		}
	}
	public boolean hasNeighbor() {
		return !edgeList.isEmpty();
	}
	public VertexInterface<T> getUnvisitedNeighbor() {
		VertexInterface<T> result = null;

		Iterator<VertexInterface<T>> neighbors = getNeighborIterator();

		boolean found = false;
		while (neighbors.hasNext() && !found) {
			VertexInterface<T> nextNeighbor = neighbors.next();
			if (!nextNeighbor.isVisited()) {
				result = nextNeighbor;
				found = true;
			}
		}
		return result;
	}
	/*================OTHER================*/

	public boolean equals(VertexInterface<T> vertex) {
		if (vertex == null || vertex.getClass() != getClass()) return false;
		return this.label.equals(vertex.getLabel());
	}

	public int compareTo(VertexInterface<T> vertex) {
		return ((Double)cost).compareTo(vertex.getCost());
	}


	protected class Edge {
		private VertexInterface<T> vertex;
		private double weight;
		protected Edge(VertexInterface<T> endVertex, double edgeWeight) {
			vertex = endVertex;
			weight = edgeWeight;
		}
		protected VertexInterface<T> getEndVertex() {
			return vertex;
		}
		protected double getWeight() {
			return weight;
		}
	}
}