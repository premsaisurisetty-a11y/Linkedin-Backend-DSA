// CO1: LinkedIn User Profile Search using AVL Tree
// KL University | DSA-2 | 2025-26

public class LinkedInAVLTree {

    static class Node {
        int id; String name; int h;
        Node left, right;
        Node(int id, String name) { this.id = id; this.name = name; this.h = 1; }
    }

    static int h(Node n) { return n == null ? 0 : n.h; }
    static int bf(Node n) { return n == null ? 0 : h(n.left) - h(n.right); }
    static void upd(Node n) { n.h = 1 + Math.max(h(n.left), h(n.right)); }

    static Node rr(Node y) {
        Node x = y.left; y.left = x.right; x.right = y;
        upd(y); upd(x);
        System.out.println("  [RR Rotation at " + y.id + "]");
        return x;
    }
    static Node ll(Node x) {
        Node y = x.right; x.right = y.left; y.left = x;
        upd(x); upd(y);
        System.out.println("  [LL Rotation at " + x.id + "]");
        return y;
    }

    static Node insert(Node n, int id, String name) {
        if (n == null) return new Node(id, name);
        if (id < n.id) n.left  = insert(n.left,  id, name);
        else           n.right = insert(n.right, id, name);
        upd(n);
        int b = bf(n);
        if (b >  1 && id < n.left.id)  return rr(n);
        if (b < -1 && id > n.right.id) return ll(n);
        if (b >  1 && id > n.left.id)  { n.left  = ll(n.left);  return rr(n); }
        if (b < -1 && id < n.right.id) { n.right = rr(n.right); return ll(n); }
        return n;
    }

    static Node search(Node n, int id) {
        if (n == null || n.id == id) return n;
        return id < n.id ? search(n.left, id) : search(n.right, id);
    }

    static void inorder(Node n) {
        if (n == null) return;
        inorder(n.left);
        System.out.printf("  ID: %-6d | %s | h=%d | bf=%d%n", n.id, n.name, n.h, bf(n));
        inorder(n.right);
    }

    public static void main(String[] args) {
        System.out.println("=== CO1: LinkedIn User Search - AVL Tree ===\n");

        int[]    ids   = {1045, 1020, 1070, 1010, 1030, 1060, 1080, 1005, 1015, 1075, 1090};
        String[] names = {"Arjun","Priya","Rahul","Sneha","Kiran","Divya","Amit","Lakshmi","Rohan","Meera","Vikram"};

        Node root = null;
        System.out.println("--- Inserting 11 Users ---");
        for (int i = 0; i < ids.length; i++) {
            System.out.println("Insert: " + ids[i] + " | " + names[i]);
            root = insert(root, ids[i], names[i]);
        }

        System.out.println("\n--- In-Order Traversal (Sorted by ID) ---");
        inorder(root);

        System.out.println("\n--- Search ---");
        int[] sq = {1030, 1075, 1099};
        for (int id : sq) {
            Node r = search(root, id);
            System.out.println(r != null ? "FOUND: " + id + " | " + r.name : "NOT FOUND: " + id);
        }

        System.out.println("\n--- Time Complexity ---");
        System.out.println("  Insert / Delete / Search : O(log n)");
        System.out.println("  Space                    : O(n)");
        System.out.println("\nConclusion: AVL Tree ensures O(log n) search for LinkedIn's");
        System.out.println("millions of user profiles via self-balancing rotations.");
    }
}
