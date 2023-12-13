package org.mwst;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class MinWeightSpanningTree {

    /**
     * Reads the graph from a text file.
     *
     * @param filePath The path to the graph file.
     * @return The number of nodes and a priority queue containing edges sorted by weight.
     * @throws IOException If there is an error reading the file.
     */
    private static Object[] readGraph(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int n = Integer.parseInt(br.readLine().trim());
            PriorityQueue<Edge> edgeQueue = new PriorityQueue<>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s");
                int src = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);
                int weight = Integer.parseInt(parts[2]);

                edgeQueue.offer(new Edge(src, dest, weight));
            }

            return new Object[]{n, edgeQueue};
        }
    }

    /**
     * Finds the minimum weight spanning tree using Kruskal's algorithm.
     *
     * @param n          The number of nodes in the graph.
     * @param edgeQueue  Priority queue containing edges sorted by weight.
     * @return The total weight of the minimum weight spanning tree.
     */
    private static int findMinimumWeightSpanningTree(int n, PriorityQueue<Edge> edgeQueue) {
        DisjointSet disjointSet = new DisjointSet(n);
        int totalWeight = 0;

        while (!edgeQueue.isEmpty()) {
            Edge edge = edgeQueue.poll();
            int rootSrc = disjointSet.find(edge.src);
            int rootDest = disjointSet.find(edge.dest);

            if (rootSrc != rootDest) {
                disjointSet.union(rootSrc, rootDest);
                totalWeight += edge.weight;
                System.out.println(edge.src + " " + edge.dest + " " + edge.weight);
            }
        }

        return totalWeight;
    }

    public static void main(String[] args) {
        try {
            String filePath = (args.length > 0) ? args[0] : "graph.txt";

            // to validate file path
            if (filePath == null || filePath.trim().isEmpty()) {
                System.err.println("File path is empty or null. Provide a valid file path.");
                System.exit(1);
            }

            Object[] graphData = readGraph(filePath);

            int n = (int) graphData[0];
            PriorityQueue<Edge> edgeQueue = (PriorityQueue<Edge>) graphData[1];

            // Validate graph data
            if (n <= 0 || edgeQueue.isEmpty()) {
                System.err.println("Invalid graph data. Ensure the graph is correctly represented.");
                System.exit(1);
            }

            int totalWeight = findMinimumWeightSpanningTree(n, edgeQueue);

            System.out.println("Total Weight of Minimum Weight Spanning Tree: " + totalWeight);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}