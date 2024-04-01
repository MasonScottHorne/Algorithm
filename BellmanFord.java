/**
 * Authors: Josh Reynolds, Atish Pillai
 * Class: CSC301
 * Date: 3/30/2024
 * Description: This program acts as a study of the Bellman-Ford algorithm. Its functions
 *              include generating random graphs using adjacency matrices and allowing the
 *              user to decide what percent negative edge weights they would like. The user
 *              can also analyze the probability of negative cycles occuring within randomly
 *              generated graphs.
 */

import java.util.Random;
import java.util.Arrays;

public class BellmanFord {

    // Calculates the percent chance of a negative cycle occuring in a randomly generated n x n matrix, 
    // using probability of negative entry. Results are determined with 1,000,000 sample graphs.
    public static double probOfNegCycle(int size, int p_negative) {
        double num_failure = 0;
        
        for (int i = 0; i < 1000000; i++) {
            if (BellmanFord(graphGen(size, p_negative), 0) == null) {
                num_failure++;
            }
        }

        return 100 * (num_failure/1000000);
    }

    // Generates a random adjacency matrix of size n.
    //      The input 'p_negative' is the percent chance of an edge weight
    //      being negative in the random matrix.
    public static int[][] graphGen(int n, int p_negative) {
        Random rand = new Random(System.currentTimeMillis());
        int[][] adjMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) { // Makes sure vertices do not have edges to themselves
                    adjMatrix[i][j] = 0;
                }
                else {
                    adjMatrix[i][j] = rand.nextInt(100) - p_negative;
                }
            }
        }
        return adjMatrix;
    }

    // Bellman-Ford Algorithm:
    //      In a graph, calculates the shortest distance from some source vertex s to
    //      every other vertex and outputs results in a distance array.
    //      Operates by running an update procedure on the paths at most |V| - 1 times,
    //      however, if a |V|th update occurs, there is determined to be a negative cycle
    //      somewhere in the graph.
    public static Integer[] BellmanFord(int[][] weight, int s) {

        int INF = 10000000, numVertices = weight.length;
        int count = 0; // Counter for the update procedure
        Integer[] dist = new Integer[numVertices], // Distance array
                prev = new Integer[numVertices]; // Previous array
        Boolean noUpdates = false;

        // Initialize all distances to infinity and all previous vertices to null.
        for (int i = 0; i < numVertices; i++) {
            dist[i] = INF;
            prev[i] = null;
        }

        // Initialize distance of source vertex to 0.
        dist[s] = 0;

        // Update prodecure : d[v] = min{d[v], d[u] + w[(u, v)]}
        while(!noUpdates) {
            noUpdates = true;
            for (int u = 0; u < numVertices; u++) {
                for (int v = 0; v < numVertices; v++) {
                    if ((dist[v] > dist[u] + weight[u][v])) { // If the distance to v through u creates the shortest path
                        dist[v] = dist[u] + weight[u][v];
                        prev[v] = u;
                        noUpdates = false;
                    }
                }
            }

            // If the update procedure has ran |V| times, there must be a negative cycle.
            if (count == numVertices) {
                return null;
            }
            count++;
        }
        return dist;
    }

    public static void main(String[] args) {
        int[][] graph = graphGen(7, 10);

        // Probabiliy of Negative Cycle Test
        System.out.println("Probability of Negative Cycle: " + probOfNegCycle(7, 10));
        
        // Print adjacency matrix of graph
        System.out.println("\nAdjacency Matrix of G:\n");
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        // Run Bellman-Ford
        Integer[] dist = BellmanFord(graph, 0);

        // Negative Cycle Detected?
        if (dist == null) {
            System.out.println("\nNegative cycle detected.\n");
            System.exit(0);
        }

        // Print result of Bellman-Ford
        System.out.println("\nBellmanFord Shortest Paths: ");
        for (int i = 0; i < dist.length; i++) {
            System.out.print(dist[i] + " ");
        }
    }
}
