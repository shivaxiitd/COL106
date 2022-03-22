package col106.assignment6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class DualGraph {
    // private ArrayList<dualEdge>[] adj;
    public HashMap<String, ArrayList<dualEdge>> adj; // Key is parent, next
    private int w;
    public int dualNodes, dualEdges;
    public int left, right, forward;
    public ArrayList<int[]> hooks;

    public DualGraph(Digraph G, int start, int left, int right, int forward) {
        this.left = left;
        this.right = right;
        this.forward = forward;
        hooks = new ArrayList<>();
        w = G.W();
        Queue<BFSNode> q = new LinkedList<>();
        q.add(new BFSNode(start, -1));
        adj = new HashMap<>();
        adj.put("-1," + start, new ArrayList<>());
        dualNodes++;
        while (!q.isEmpty()) {
            BFSNode poll = q.poll();
            Iterator<Edge> neigh = G.adj(poll.val).iterator();
            Edge temp = null;
            String fromKey = poll.parent + "," + poll.val;
            while (neigh.hasNext()) {
                temp = neigh.next();
                int to = temp.to();
                String nodeKey = poll.val + "," + to;
                int cost = turnCost(poll.parent, poll.val, to) + (int) temp.weight();
                if (!adj.containsKey(nodeKey)) {
                    adj.put(nodeKey, new ArrayList<>());
                    dualNodes++;
                    q.add(new BFSNode(to, poll.val));
                }
                adj.get(fromKey).add(new dualEdge(fromKey, nodeKey, cost));
                hooks.add(new int[] { poll.parent == -1 ? -1 : poll.parent / w,
                        poll.parent == -1 ? -1 : poll.parent % w, poll.val / w, poll.val % w, to / w, to % w, cost });
                dualEdges++;

            }
        }
        // print(adj);
        // print(dualNodes);
        // print(dualEdges);
    }

    public int turnCost(int parent, int curr, int to) {
        if (parent == -1) {
            return forward;
        }
        int[] a = { parent / w, parent % w };
        int[] b = { curr / w, curr % w };
        int[] c = { to / w, to % w };
        if ((a[0] == b[0] && b[0] == c[0]) || (a[1] == b[1] && b[1] == c[1])) {
            return forward;
        }

        if (a[0] == b[0]) {
            if (b[1] > a[1])
                return c[0] > b[0] ? right : left;
            else
                return c[0] > b[0] ? left : right;
        } else {
            if (b[0] > a[0])
                return c[1] > b[1] ? left : right;
            else
                return c[1] > b[1] ? right : left;
        }
    }

    /**  
     * Authored By Mudit Garg 2018TT10922
    */

    public <T> void print(T a) {
        System.out.println(a.toString());
    }

    public Iterator<dualEdge> getNeighbours(String dualNode) {
        return adj.get(dualNode).iterator();
    }
}

class BFSNode {
    public int val;
    public int parent;

    public BFSNode(int val, int parent) {
        this.val = val;
        this.parent = parent;
    }
}