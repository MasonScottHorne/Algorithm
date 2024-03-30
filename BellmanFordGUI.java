import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class BellmanFordGUI extends JFrame {
    private JTextField sourceField;
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JButton runButton;
    private JButton randomButton;
    private GraphPanel graphPanel;
    private int[][] graph;
    private final int V = 5; // Number of vertices in graph

    public BellmanFordGUI() {
        setTitle("Bellman-Ford Algorithm");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 1));

        JPanel sourcePanel = new JPanel();
        sourcePanel.add(new JLabel("Source Vertex: "));
        sourceField = new JTextField(5);
        sourcePanel.add(sourceField);
        inputPanel.add(sourcePanel);

        inputArea = new JTextArea(10, 50);
        inputArea.setText("0 2 3 0 0\n0 0 0 4 5\n0 0 0 1 0\n0 0 0 0 2\n0 0 0 0 0");
        JScrollPane scrollPane = new JScrollPane(inputArea);
        inputPanel.add(scrollPane);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        runButton = new JButton("Run Algorithm");
        runButton.addActionListener(this::runAlgorithm);
        buttonPanel.add(runButton);

        randomButton = new JButton("Generate Random Graph");
        randomButton.addActionListener(this::generateRandomGraph);
        buttonPanel.add(randomButton);

        add(buttonPanel, BorderLayout.CENTER);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.SOUTH);

        graphPanel = new GraphPanel(new int[V][V]);
        add(graphPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void runAlgorithm(ActionEvent e) {
        try {
            int source = Integer.parseInt(sourceField.getText());
            graph = parseGraph(inputArea.getText());
            int[] distances = bellmanFord(graph, source);

            outputArea.setText("Vertex Distance from Source\n");
            for (int i = 0; i < distances.length; ++i)
                outputArea.append(i + "\t\t" + distances[i] + "\n");

            graphPanel.updateGraph(graph);
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    private void generateRandomGraph(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                int weight = (i == j) ? 0 : random.nextInt(20); // Generate weights from 0 to 19
                sb.append(weight).append(" ");
            }
            sb.append("\n");
        }
        inputArea.setText(sb.toString());
    }

    private int[][] parseGraph(String input) {
        String[] lines = input.split("\n");
        if (lines.length != V) {
            throw new IllegalArgumentException("Input must have " + V + " lines for " + V + " vertices.");
        }
        int[][] graph = new int[V][V];
        int NO_EDGE = -1; // Special value to represent the absence of an edge
        for (int i = 0; i < V; i++) {
            String[] parts = lines[i].trim().split("\\s+");
            if (parts.length != V) {
                throw new IllegalArgumentException("Line " + (i + 1) + " must have " + V + " numbers.");
            }
            for (int j = 0; j < V; j++) {
                int weight = Integer.parseInt(parts[j]);
                graph[i][j] = (weight != NO_EDGE) ? weight : Integer.MAX_VALUE;
            }
        }
        return graph;
    }

    private int[] bellmanFord(int[][] graph, int src) {
        int[] dist = new int[V];
        for (int i = 0; i < V; ++i)
            dist[i] = Integer.MAX_VALUE;
        dist[src] = 0;

        for (int i = 1; i < V; ++i) {
            for (int u = 0; u < V; ++u) {
                for (int v = 0; v < V; ++v) {
                    if (graph[u][v] != Integer.MAX_VALUE && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                        dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        for (int u = 0; u < V; ++u) {
            for (int v = 0; v < V; ++v) {
                if (graph[u][v] != Integer.MAX_VALUE && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    throw new RuntimeException("Graph contains a negative-weight cycle");
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BellmanFordGUI::new);
    }
}
