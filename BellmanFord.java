import java.util.Random;

public class BellmanFord {
    
    // Generates a random adjacency matrix
    public static int[][] graphGen() {

        Random rand = new Random();
        int n = 50;

        int[][] adjMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjMatrix[i][j] = rand.nextInt(10) - rand.nextInt(10);
            }
        }

        return null;

    }
    public static void main(String[] args) {
        
    }




}
