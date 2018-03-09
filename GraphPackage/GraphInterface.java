package GraphPackage;

public interface GraphInterface<T>{
	public boolean addVertex(T label);

	public boolean addEdge(T start, T end, double weight);
	public boolean addEdge(T start, T end);

	public boolean hasEdge(T begin, T end);

	public boolean isEmpty();

	public int getNumberOfVertices();

	public int getNumberOfEdges();

	public void clear();
}