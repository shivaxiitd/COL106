package col106.assignment6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ShortestPathFinder implements ShortestPathInterface {

    int w, h;
    public ArrayList<int[]> hooks;
    public int dualNodes, dualEdges;
    public HashMap<String, PathNode> done;
    int shortestPathCost;
    ArrayList<int[]> shortestPath;
    boolean hasPath = false;

    /**
     * Computes shortest-path from the source vertex s to destination vertex t in
     * graph G. DO NOT MODIFY THE ARGUMENTS TO THIS CONSTRUCTOR
     *
     * @param G       the graph
     * @param s       the source vertex
     * @param t       the destination vertex
     * @param left    the cost of taking a left turn
     * @param right   the cost of taking a right turn
     * @param forward the cost of going forward
     * @throws IllegalArgumentException unless 0 <= s < V
     * @throws IllegalArgumentException unless 0 <= t < V where V is the number of
     *                                  vertices in the graph G.
     */
    public ShortestPathFinder(final Digraph G, final int[] s, final int[] t, final int left, final int right,
            final int forward) {
        // YOUR CODE GOES HERE
        // BFS
        w = G.W();
        int start = s[0] * w + s[1];
        int target = t[0] * w + t[1];
        PriorityQueue<PathNode> q = new PriorityQueue<>(new Comparator<PathNode>() {
            @Override
            public int compare(PathNode a, PathNode b) {
                return Integer.compare(a.cost, b.cost);
            }
        });

        q.add(new PathNode(-1 + "," + start, null, 0));
        DualGraph dualGraph = new DualGraph(G, start, left, right, forward);
        dualNodes = dualGraph.dualNodes;
        dualEdges = dualGraph.dualEdges;
        hooks = dualGraph.hooks;
        // print(dualNodes + " " + dualEdges);
        // printArrayList(hooks);
        done = new HashMap<>();
        while (!q.isEmpty()) {
            PathNode poll = q.poll();
            if (done.containsKey(poll.key)) {
                continue;
            }
            done.put(poll.key, poll);
            if (Integer.parseInt(poll.key.split(",")[1]) == target) {
                // print("reached target");
                generateShortestPath(poll, start);
                break;
            }
            Iterator<dualEdge> iter = dualGraph.getNeighbours(poll.key);
            dualEdge temp = null;
            while (iter.hasNext()) {
                temp = iter.next();
                int cost = temp.weight + poll.cost;
                q.add(new PathNode(temp.to, poll.key, cost));
            }
            // print("------\n" + q + "\n");
            // print(done + "\n----------\n");
        }
        // print("Done");
        // print(done);
    }

    public void generateShortestPath(PathNode target, int start) {
        hasPath = true;
        shortestPathCost = target.cost;
        PathNode temp = target;
        shortestPath = new ArrayList<>();
        while (temp.parent != null) {
            String[] s = temp.key.split(",");
            int par = Integer.parseInt(s[1]);
            // print("" + (par / w) + " " +  (par % w));
            shortestPath.add(new int[] { par / w, par % w });
            temp = done.get(temp.parent);
        }
        shortestPath.add(new int[] {start / w, start % w});
        Collections.reverse(shortestPath);
        // printArrayList(shortestPath);
    }

    // public static void main(String[] args) {
    //     String infile = "test.csv";
    //     int[] s = new int[] { 0, 0 };
    //     int[] t = new int[] { 0, 1 };
    //     int left = 8;
    //     int right = 1;
    //     int forward = 0;

    //     col106.assignment6.Digraph G = new col106.assignment6.Digraph(infile);
    //     ShortestPathFinder sp = new ShortestPathFinder(G, s, t, left, right, forward);
    // }

    /**  
     * Authored By Mudit Garg 2018TT10922
     */

    // Return number of nodes in dual graph
    public int numDualNodes() {
        // YOUR CODE GOES HERE
        return dualNodes;
    }

    // Return number of edges in dual graph
    public int numDualEdges() {
        // YOUR CODE GOES HERE
        return dualEdges;
    }

    // Return hooks in dual graph
    // A hook (0,0) - (1,0) - (1,2) with weight 8 should be represented as
    // the integer array {0, 0, 1, 0, 1, 2, 8}
    public ArrayList<int[]> dualGraph() {
        // YOUR CODE GOES HERE
        return hooks;
    }

    // Return true if there is a path from s to t.
    public boolean hasValidPath() {
        // YOUR CODE GOES HERE
        return hasPath;
    }

    // Return the length of the shortest path from s to t.
    public int ShortestPathValue() {
        // YOUR CODE GOES HERE
        return shortestPathCost;
    }

    // Return the shortest path computed from s to t as an ArrayList of nodes,
    // where each node is represented by its location on the grid.
    public ArrayList<int[]> getShortestPath() {
        // YOUR CODE GOES HERE
        return shortestPath;
    }

    public void printArrayList(ArrayList<int[]> arr) {
        for (int i = 0; i < arr.size(); i++) {
            print(Arrays.toString(arr.get(i)));
        }
    }

    public <T> void print(T a) {
        System.out.println(a.toString());
    }
}

class PathNode {
    String key;
    String parent;
    int cost;

    public PathNode(String key, String parent, int cost) {
        this.key = key;
        this.parent = parent;
        this.cost = cost;
    }

    public String toString() {
        return "(" + key + "-" + parent + "):" + cost + "\n";
    }
}