package aaronpost.clashcraft.Graph;

import java.util.*;

public class GridGraph<T> {
    private List<List<Vertex<T>>> gridGraph;
    private List<Vertex<T>> nodes;
    private Map<Vertex<T>, List<Vertex<T>>> adjacencies;
    private int nodesNum = 0;
    static int[][] directions = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    public GridGraph(int x, int y) {
        this.gridGraph = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.adjacencies = new HashMap<>();
        for(int i = 0; i < x; i++) {
            List<Vertex<T>> vertices = new ArrayList<>();
            for(int j = 0; j < y; j++) {
                // first vertex id is 1
                Vertex<T> v = new Vertex<T>(++nodesNum);
                vertices.add(v);
                nodes.add(v);
            }
            gridGraph.add(vertices);
        }
        int counter = 0;
        for(int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                List<Vertex<T>> adjacenciesAtVertex = getAdjacentAtVertex(i,j);
                adjacencies.put(this.gridGraph.get(i).get(j), adjacenciesAtVertex);
                System.out.println("Node " + (++counter) + " - x: " + i + " y: " + j + " adjacencies: " + adjacenciesAtVertex.size());
            }
        }
    }

    /**
     * BFS SEARCH:
     * Heavily inspired by the pseudocode on studies.nawaz.org
     * @param start
     * @param end
     * @return

    public ArrayList<Vertex<T>> shortestPath(Vertex<T> start, Vertex<T> end) {
        // Initialize queue
        Queue<Vertex<T>> queue = new PriorityQueue<>();
        // Initialize array distances and set all values to "infinity"
        int[] distances = new int[nodesNum];
        for(int i = 0; i< nodesNum; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        // Add starting node to the queue and initialize distances to 0 for this node
        queue.add(start);
        int distance = 0;
        distances[nodes.indexOf(start)] = 0;
        // While queue is not empty
        while(queue.size() > 0) {
            Vertex<T> n = queue.remove();
            distance += 1;
            Pair<Integer,Integer> vertex = gridPosition(n);
            for(Vertex<T> neighbor : getAdjacentAtVertex(vertex.first, vertex.second)) {
                int neighborIndex = nodes.indexOf(neighbor);
                if(distances[neighborIndex] == Integer.MAX_VALUE) {
                    distances[neighborIndex] = distance;
                    queue.add(neighbor);
                }
            }
        }

        return  null;
    }
    **/

    // could use a tiny bit of optimization
    /**private Pair<Integer, Integer> gridPosition(Vertex<T> v) {
        for(int i = 0; i < gridGraph.size(); i++) {
            for(int j = 0; j < gridGraph.get(i).size(); j++) {
                if(gridGraph.get(i).get(j) == v) {
                    return new Pair<>(i,j);
                }
            }
        }
        return new Pair<>(-1,-1);
    } **/
    /**
     *      Returns a list of vertices that are adjacent horizontally, vertically, or diagonally. Accounts for edge
     *      cases.
     *
     *      Examples:
     *
     *      in middle   at top    at side
     *      X X X       X O X     X X
     *      X O X       X X X     O X
     *      X X X                 X X
     */
    private List<Vertex<T>> getAdjacentAtVertex(int i, int j) {
        int n = gridGraph.size();
        int m = gridGraph.get(0).size();
        List<Vertex<T>> adjacencies = new ArrayList<>();
        for (int[] dir : directions) {
            int newRow = i + dir[0];
            int newCol = j + dir[1];
            if (isValidPos(newRow, newCol, n, m)) {
                adjacencies.add(gridGraph.get(newRow).get(newCol));
            }
        }
        return adjacencies;
    }

    private static boolean isValidPos(int row, int col, int numRows, int numCols) {
        return row >= 0 && row < numRows && col >= 0 && col < numCols;
    }


    List<Vertex<T>> getAdjVertices(int x, int y) {
        return adjacencies.get(gridGraph.get(x).get(y));
    }
}
