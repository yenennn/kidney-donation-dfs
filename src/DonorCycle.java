import java.util.*;

public class DonorCycle{

    // Array Matrix Graph Class
    public static class Graph {
        private int vertexCount;
        private int[][] edges;

        // Constructor
        public Graph(int vertexCount){
            this.vertexCount = vertexCount;
            edges = new int[vertexCount][vertexCount];
            for (int i = 0; i < vertexCount; i++){
                for (int j = 0; j < vertexCount; j++){
                    edges[i][j] = 0;
                }
            }
        }

        // Add an edge from one vertex to another
        public void addEdge(int from, int to){
            edges[from][to] = 1;
        }

    }

    public static boolean isInCycle(int[][] matchScores, int[] donorsFriends, int query) {
        int n = matchScores[0].length; // number of recipients
        Graph graph = new Graph(n);

        // Build the graph
        for (int donor = 0; donor < donorsFriends.length; donor++) {
            int from = donorsFriends[donor];
            for (int to = 0; to < n; to++) {
                if (matchScores[donor][to] >= 60) {
                    graph.addEdge(from, to);
                }
            }
        }

        // Check for cycles that include query
        boolean[] visited = new boolean[n];
        return dfs(graph, query, query, visited, 0);
    }

    // Check if there's a path from v back to start with at least one edge
    private static boolean dfs(Graph graph, int start, int v, boolean[] visited, int pathLength) {
        // If we've returned to the starting vertex after traversing at least one edge
        if (v == start && pathLength > 0) {
            return true;
        }

        // Mark current vertex as visited
        visited[v] = true;

        // Explore all neighbors
        for (int neighbor = 0; neighbor < graph.vertexCount; neighbor++) {
            // Check if there's an edge from v to neighbor
            if (graph.edges[v][neighbor] == 1) {
                // If neighbor is unvisited, or it's the start vertex (to complete the cycle)
                if (!visited[neighbor] || (neighbor == start && pathLength > 0)) {
                    if (dfs(graph, start, neighbor, visited, pathLength + 1)) {
                        return true;
                    }
                }
            }
        }

        // Backtrack by unmarking this vertex
        visited[v] = false;
        return false;
    }


    public static void main(String[] args){

        Scanner s = new Scanner(System.in);

        int n = s.nextInt();
        int m = s.nextInt();

        String donorsLine = s.next();
        String[] donorsArray = donorsLine.split(",",0);
        int[] donorsFriends = new int[donorsArray.length];
        for(int i=0; i<m; i++){
            donorsFriends[i] = Integer.parseInt(donorsArray[i]);
        }

        int[][] matchScores = new int[m][n];
        for(int i=0; i<m; i++){
            String matchscoreLine = s.next();
            String[] matchscoreArray = matchscoreLine.split(",",0);
            for(int j=0; j<n; j++){
                matchScores[i][j] = Integer.parseInt(matchscoreArray[j]);
            }
        }
        int query = s.nextInt();
        System.out.println(isInCycle(matchScores, donorsFriends, query));
    }


}

