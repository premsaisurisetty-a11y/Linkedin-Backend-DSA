// CO3: LinkedIn Friend Network using BFS, DFS & MST
// KL University | DSA-2 | 2025-26

import java.util.*;

public class LinkedInGraph {

    static final int V = 8;
    static String[] u = {"Arjun","Priya","Rahul","Sneha","Kiran","Divya","Amit","Lakshmi"};
    static List<List<int[]>> adj = new ArrayList<>();
    static int[][] edges = {{0,1,4},{0,2,3},{1,3,2},{1,4,6},{2,4,5},{3,5,4},{4,6,3},{5,7,2},{6,7,4}};

    static void bfs(int src) {
        int[] level = new int[V]; Arrays.fill(level, -1);
        Queue<Integer> q = new LinkedList<>();
        level[src] = 0; q.add(src);
        System.out.println("  BFS 'People You May Know' from " + u[src] + ":");
        while (!q.isEmpty()) {
            int c = q.poll();
            System.out.println("    " + u[c] + " -> Degree: " + level[c]);
            for (int[] nb : adj.get(c)) if (level[nb[0]] == -1) { level[nb[0]] = level[c]+1; q.add(nb[0]); }
        }
    }

    static boolean[] visited = new boolean[V];
    static void dfs(int node, List<String> cluster) {
        visited[node] = true; cluster.add(u[node]);
        for (int[] nb : adj.get(node)) if (!visited[nb[0]]) dfs(nb[0], cluster);
    }

    // Kruskal MST
    static int[] par = new int[V];
    static int find(int x) { return par[x] == x ? x : (par[x] = find(par[x])); }
    static boolean union(int a, int b) {
        int pa = find(a), pb = find(b);
        if (pa == pb) return false;
        par[pa] = pb; return true;
    }

    static void kruskal() {
        for (int i = 0; i < V; i++) par[i] = i;
        int[][] sorted = edges.clone();
        Arrays.sort(sorted, (a, b) -> a[2] - b[2]);
        int cost = 0;
        System.out.println("  Kruskal MST:");
        for (int[] e : sorted) {
            if (union(e[0], e[1])) {
                System.out.printf("    %s -- %s : %d%n", u[e[0]], u[e[1]], e[2]);
                cost += e[2];
            }
        }
        System.out.println("  Total MST Weight: " + cost);
    }

    static void prim() {
        boolean[] inMST = new boolean[V];
        int[] key = new int[V]; Arrays.fill(key, Integer.MAX_VALUE);
        int[] parent = new int[V]; Arrays.fill(parent, -1);
        key[0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{0, 0});
        int cost = 0;
        System.out.println("  Prim MST:");
        while (!pq.isEmpty()) {
            int c = pq.poll()[0];
            if (inMST[c]) continue;
            inMST[c] = true;
            if (parent[c] != -1) { System.out.printf("    %s -- %s : %d%n", u[parent[c]], u[c], key[c]); cost += key[c]; }
            for (int[] nb : adj.get(c)) if (!inMST[nb[0]] && nb[1] < key[nb[0]]) { key[nb[0]] = nb[1]; parent[nb[0]] = c; pq.offer(new int[]{nb[0], nb[1]}); }
        }
        System.out.println("  Total MST Weight: " + cost);
    }

    public static void main(String[] args) {
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) { adj.get(e[0]).add(new int[]{e[1],e[2]}); adj.get(e[1]).add(new int[]{e[0],e[2]}); }

        System.out.println("=== CO3: LinkedIn Network - BFS, DFS & MST ===\n");
        System.out.println("Users: " + Arrays.toString(u));
        System.out.println("Edges: " + edges.length + "\n");

        bfs(0);

        System.out.println("\n--- DFS Community Detection ---");
        List<String> cluster = new ArrayList<>();
        dfs(0, cluster);
        System.out.println("  Cluster: " + cluster);

        System.out.println("\n--- Kruskal's Algorithm ---");
        kruskal();
        System.out.println("\n--- Prim's Algorithm ---");
        prim();

        System.out.println("\n--- Time Complexity ---");
        System.out.println("  BFS / DFS   : O(V + E)");
        System.out.println("  Kruskal MST : O(E log E)");
        System.out.println("  Prim MST    : O(E log V)");
        System.out.println("\nConclusion: BFS powers 'People You May Know', DFS detects");
        System.out.println("communities, and MST minimizes LinkedIn's network cable cost.");
    }
}
