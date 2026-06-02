// CO2: LinkedIn Post Indexing using B+ Tree & Fenwick Tree
// KL University | DSA-2 | 2025-26

import java.util.*;

public class LinkedInBPlusTree {

    // --- Fenwick Tree (BIT) for like-count range queries ---
    static int[] bit = new int[20];

    static void update(int i, int v) { for (; i < 20; i += i & -i) bit[i] += v; }
    static int query(int i)          { int s = 0; for (; i > 0; i -= i & -i) s += bit[i]; return s; }
    static int range(int l, int r)   { return query(r) - query(l - 1); }

    // --- Simple sorted list simulating B+ Tree leaf chain ---
    static List<int[]> posts = new ArrayList<>(); // {postId, likes}
    static Map<Integer, String> titles = new LinkedHashMap<>();

    static void insert(int pid, String title, int likes, int idx) {
        int i = 0;
        while (i < posts.size() && posts.get(i)[0] < pid) i++;
        posts.add(i, new int[]{pid, likes});
        titles.put(pid, title);
        update(idx, likes);
    }

    static boolean search(int pid) {
        for (int[] p : posts) if (p[0] == pid) return true;
        return false;
    }

    static List<Integer> rangeQuery(int lo, int hi) {
        List<Integer> res = new ArrayList<>();
        for (int[] p : posts) if (p[0] >= lo && p[0] <= hi) res.add(p[0]);
        return res;
    }

    public static void main(String[] args) {
        System.out.println("=== CO2: LinkedIn Post Indexing - B+ Tree & Fenwick Tree ===\n");

        Object[][] data = {
            {1001, "5 Tips for LinkedIn Profile",     450},
            {1005, "Future of AI in Software Dev",   1200},
            {1009, "Why Developers Should Blog",      300},
            {1013, "Cloud Certifications 2025",       540},
            {1017, "System Design Interview Guide",  1100},
            {1021, "Open Source Contributions",       880},
        };

        System.out.println("--- Inserting Posts ---");
        for (int i = 0; i < data.length; i++) {
            int pid = (int) data[i][0]; String title = (String) data[i][1]; int likes = (int) data[i][2];
            System.out.printf("PostID: %d | %s | Likes: %d%n", pid, title, likes);
            insert(pid, title, likes, i + 1);
        }

        System.out.println("\n--- B+ Leaf Chain (Sorted) ---");
        System.out.print("  ");
        for (int[] p : posts) System.out.print("[" + p[0] + "] -> ");
        System.out.println("null");

        System.out.println("\n--- Search ---");
        for (int id : new int[]{1009, 1013, 1022}) {
            System.out.println(search(id) ? "FOUND: " + id + " | " + titles.get(id) : "NOT FOUND: " + id);
        }

        System.out.println("\n--- Range Query: PostIDs 1005 to 1017 ---");
        System.out.println("  Result: " + rangeQuery(1005, 1017));

        System.out.println("\n--- Fenwick Tree: Like Count Queries ---");
        System.out.println("  Posts 1-3  total likes : " + range(1, 3));
        System.out.println("  Posts 4-6  total likes : " + range(4, 6));
        System.out.println("  All posts  total likes : " + range(1, 6));

        System.out.println("\n--- Time Complexity ---");
        System.out.println("  B+ Tree Search / Insert  : O(log n)");
        System.out.println("  B+ Tree Range Query      : O(log n + k)");
        System.out.println("  Fenwick Update / Query   : O(log n)");
        System.out.println("\nConclusion: B+ Tree + Fenwick Tree power LinkedIn's post");
        System.out.println("indexing and engagement analytics at scale.");
    }
}
