import java.util.Random;
import java.util.Arrays;

public class BellmanFord {
    
    // Generates a random adjacency matrix
    public static int[][] graphGen() {
        Random rand = new Random(System.currentTimeMillis());
        int n = 4;
        int[][] adjMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) adjMatrix[i][j] = 0;
                else {
                    adjMatrix[j][i] = rand.nextInt(10) - 5;
                }
            }
        }

        return adjMatrix;
    }

    // Bellman-Ford Algorithm
    public static Integer[] BellmanFord(int[][] weight, int s) {

        int INF = Integer.MAX_VALUE;

        int numVertices = weight.length;
        Integer[] dist = new Integer[numVertices], 
                prev = new Integer[numVertices];
        Boolean noUpdates = false;

        for (int i = 0; i < numVertices; i++) {
            dist[i] = INF;
            prev[i] = null;
        }

        dist[s] = 0;

        do {
            noUpdates = true;
            for (int u = 0; u < numVertices; u++) {
                for (int v = 0; v < numVertices; v++) {
                    if ((dist[v] > dist[u] + weight[u][v]) && (prev[v] == null)) { // If the distance to v through u creates the shortest path
                        dist[v] = dist[u] + weight[u][v];
                        prev[v] = u;
                        noUpdates = false;
                    }
                }
            }
        }
        while (!noUpdates);

        return dist;
    }
    public static void main(String[] args) {
        int[][] graph = graphGen();
        
        System.out.println("\nAdjacency Matrix of G:\n");
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        Integer[] dist = BellmanFord(graph, 0);

        System.out.println("\nBellmanFord Shortest Paths: ");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(dist[i] + " ");
        }

    }
}
