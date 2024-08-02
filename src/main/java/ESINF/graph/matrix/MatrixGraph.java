package ESINF.graph.matrix;

import ESINF.graph.CommonGraph;
import ESINF.graph.Edge;
import ESINF.graph.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author DEI-ISEP
 */
public class MatrixGraph<V, E> extends CommonGraph<V, E> {
    public static final int INITIAL_CAPACITY = 10;
    public static final float RESIZE_FACTOR = 1.5F;
    Edge<V, E>[][] edgeMatrix;

    @SuppressWarnings("unchecked")
    public MatrixGraph(boolean directed, int initialCapacity) {
        super(directed);
        edgeMatrix = (Edge<V, E>[][]) (new Edge<?, ?>[initialCapacity][initialCapacity]);
    }

    public MatrixGraph(boolean directed) {
        this(directed, INITIAL_CAPACITY);
    }

    public MatrixGraph(Graph<V, E> g) {
        this(g.isDirected(), g.numVertices());
        copy(g, this);
    }

    @Override
    public Collection<V> adjVertices(V vert) {
        int index = key(vert);
        if (index == -1)
            return null;

        ArrayList<V> outVertices = new ArrayList<>();
        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[index][i] != null)
                outVertices.add(vertices.get(i));
        return outVertices;
    }

    @Override
    public Collection<Edge<V, E>> edges() {

        ArrayList<Edge<V, E>> edges = new ArrayList<>();
        for (int i = 0; i < numVerts; i++)
            for (int j = 0; j < numVerts; j++)
                if (edgeMatrix[i][j] != null)
                    edges.add(edgeMatrix[i][j]);
        return edges;

    }

    public MatrixGraph(boolean directed, ArrayList<V> vs, E[][] m) {
        this(directed, vs.size());
        numVerts = vs.size();
        vertices = new ArrayList<>(vs);
        for (int i = 0; i < numVerts; i++)
            for (int j = 0; j < numVerts; j++)
                if (j != i && m[i][j] != null)
                    addEdge(vertices.get(i), vertices.get(j), m[i][j]);
    }

    @Override
    public Edge<V, E> edge(V vOrig, V vDest) {
        int vOrigKey = key(vOrig);
        int vDestKey = key(vDest);

        if ((vOrigKey < 0) || (vDestKey < 0))
            return null;

        return edgeMatrix[vOrigKey][vDestKey];
    }

    @Override
    public Edge<V, E> edge(int vOrigKey, int vDestKey) {
        if (vOrigKey >= numVerts && vDestKey >= numVerts)
            return null;
        return edgeMatrix[vOrigKey][vDestKey];
    }

    @Override
    public int outDegree(V vert) {
        int vertKey = key(vert);
        if (vertKey == -1)
            return -1;

        int edgeCount = 0;
        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[vertKey][i] != null)
                edgeCount++;
        return edgeCount;
    }

    @Override
    public int inDegree(V vert) {
        int vertKey = key(vert);
        if (vertKey == -1)
            return -1;

        int edgeCount = 0;
        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[i][vertKey] != null)
                edgeCount++;
        return edgeCount;
    }

    @Override
    public Collection<Edge<V, E>> outgoingEdges(V vert) {

        Collection<Edge<V, E>> ce = new ArrayList<>();
        int vertKey = key(vert);
        if (vertKey == -1)
            return ce;

        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[vertKey][i] != null)
                ce.add(edgeMatrix[vertKey][i]);
        return ce;

    }

    @Override
    public Collection<Edge<V, E>> incomingEdges(V vert) {
        Collection<Edge<V, E>> ce = new ArrayList<>();
        int vertKey = key(vert);
        if (vertKey == -1)
            return ce;

        for (int i = 0; i < numVerts; i++)
            if (edgeMatrix[i][vertKey] != null)
                ce.add(edgeMatrix[i][vertKey]);
        return ce;
    }

    @Override
    public boolean addVertex(V vert) {
        int vertKey = key(vert);
        if (vertKey != -1)
            return false;

        vertices.add(vert);
        numVerts++;
        resizeMatrix();
        return true;
    }

    /**
     * Resizes the matrix when a new vertex increases the length of ArrayList
     */
    private void resizeMatrix() {
        if (edgeMatrix.length < numVerts) {
            int newSize = (int) (edgeMatrix.length * RESIZE_FACTOR);

            @SuppressWarnings("unchecked")
            Edge<V, E>[][] temp = (Edge<V, E>[][]) new Edge<?, ?>[newSize][newSize];
            for (int i = 0; i < edgeMatrix.length; i++)
                temp[i] = Arrays.copyOf(edgeMatrix[i], newSize);
            edgeMatrix = temp;
        }
    }

    @Override
    public boolean addEdge(V vOrig, V vDest, E weight) {
        if (vOrig == null || vDest == null) throw new RuntimeException("Vertices cannot be null!");
        if (edge(vOrig, vDest) != null)
            return false;

        if (!validVertex(vOrig))
            addVertex(vOrig);

        if (!validVertex(vDest))
            addVertex(vDest);

        int vOrigKey = key(vOrig);
        int vDestKey = key(vDest);

        edgeMatrix[vOrigKey][vDestKey] = new Edge<>(vOrig, vDest, weight);
        numEdges++;
        if (!isDirected) {
            edgeMatrix[vDestKey][vOrigKey] = new Edge<>(vDest, vOrig, weight);
            numEdges++;
        }
        return true;
    }

    @Override
    public boolean removeVertex(V vert) {
        int vertKey = key(vert);
        if (vertKey == -1)
            return false;

        // first let's remove edges from the vertex
        for (int i = 0; i < numVerts; i++)
            removeEdge(vertKey, i);
        if (isDirected) {
            // first let's remove edges to the vertex
            for (int i = 0; i < numVerts; i++)
                removeEdge(i, vertKey);
        }

        // remove shifts left all vertices after the one removed
        // It is necessary to collapse the edge matrix
        for (int i = vertKey; i < numVerts - 1; i++) {
            for (int j = 0; j < numVerts; j++) {
                edgeMatrix[i][j] = edgeMatrix[i + 1][j];
            }
        }
        for (int i = vertKey; i < numVerts - 1; i++) {
            for (int j = 0; j < numVerts; j++) {
                edgeMatrix[j][i] = edgeMatrix[j][i + 1];
            }
        }
        for (int j = 0; j < numVerts; j++) {
            edgeMatrix[j][numVerts - 1] = null;
            edgeMatrix[numVerts - 1][j] = null;
        }

        vertices.remove(vert);
        numVerts--;
        return true;
    }

    public V returnVertex(V vert){
        int vertKey = key(vert);
        if (vertKey == -1)
            return null;

        return vertices.get(vertKey);

    }

    private void removeEdge(int vOrigKey, int vDestKey) {
        if (edgeMatrix[vOrigKey][vDestKey] != null) {
            edgeMatrix[vOrigKey][vDestKey] = null;
            numEdges--;
        }
        if (!isDirected && (edgeMatrix[vDestKey][vOrigKey] != null)) {
            edgeMatrix[vDestKey][vOrigKey] = null;
            numEdges--;
        }
    }

    @Override
    public boolean removeEdge(V vOrig, V vDest) {
        int vOrigKey = key(vOrig);
        int vDestKey = key(vDest);

        if ((vOrigKey < 0) || (vDestKey < 0) || (edgeMatrix[vOrigKey][vDestKey] == null))
            return false;

        removeEdge(vOrigKey, vDestKey);
        return true;
    }

    @Override
    public MatrixGraph<V, E> clone() {
        MatrixGraph<V, E> g = new MatrixGraph<>(this.isDirected, this.edgeMatrix.length);

        copy(this, g);

        return g;
    }

    @Override
    public boolean containsVertex(V local) {
        for (V vert : vertices) {
            if (vert.equals(local)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a string representation of the graph.
     * Matrix only represents existence of Edge
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertices:\n");
        for (int i = 0; i < numVerts; i++)
            sb.append(vertices.get(i)).append("\n");

        sb.append("\nMatrix:\n");

        sb.append("  ");
        for (int i = 0; i < numVerts; i++) {
            sb.append(" |  ").append(i).append(" ");
        }
        sb.append("\n");

        // aligned only when vertices < 10
        for (int i = 0; i < numVerts; i++) {
            sb.append(" ").append(i).append(" ");
            for (int j = 0; j < numVerts; j++)
                if (edgeMatrix[i][j] != null)
                    sb.append("|  X  ");
                else
                    sb.append("|     ");
            sb.append("\n");
        }

        sb.append("\nEdges:\n");

        for (int i = 0; i < numVerts; i++)
            for (int j = 0; j < numVerts; j++)
                if (edgeMatrix[i][j] != null)
                    sb.append("From " + i + " to " + j + "-> " + edgeMatrix[i][j] + "\n");

        sb.append("\n");

        return sb.toString();
    }
}