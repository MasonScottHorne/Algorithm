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
    private int[][] graph;
    private final int V = 5; // Number of vertices in graph

    public BellmanFordGUI() {
        setTitle("Bellman-Ford Algorithm");
        setSize(600, 400);
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
        inputArea.setText("0 1 2\n0 2 3\n1 3 4\n1 4 5\n2 3 1\n3 4 2");
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
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    private void generateRandomGraph(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i != j) {
                    int weight = random.nextInt(20) - 10;
                    sb.append(i).append(" ").append(j).append(" ").append(weight).append("\n");
                }
            }
        }
        inputArea.setText(sb.toString());
    }

    private int[][] parseGraph(String input) {
        int[][] graph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        String[] lines = input.split("\n");
        for (String line : lines) {
            String[] parts = line.trim().split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid line format: " + line);
            }
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            int weight = Integer.parseInt(parts[2]);
            graph[u][v] = weight;
        }
        return graph;
    }

    private int[] bellmanFord(int[][] graph, int src) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BellmanFordGUI::new);
    }
}
