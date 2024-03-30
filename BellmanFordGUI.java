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

        inputArea = new JTextArea(5, 40);
        inputArea.setText("0 1 -1\n0 2 4\n1 2 3\n1 3 2\n1 4 2\n3 2 5\n3 1 1\n4 3 -3");
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
        String[] lines = input.split("\n");
        int[][] graph = new int[lines.length][3];
        for (int i = 0; i < lines.length; i++) {
            String[] parts = lines[i].trim().split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid line format: " + lines[i]);
            }
            graph[i][0] = Integer.parseInt(parts[0]);
            graph[i][1] = Integer.parseInt(parts[1]);
            graph[i][2] = Integer.parseInt(parts[2]);
        }
        return graph;
    }


    private int[] bellmanFord(int[][] graph, int src) {
      
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BellmanFordGUI::new);
    }
}
