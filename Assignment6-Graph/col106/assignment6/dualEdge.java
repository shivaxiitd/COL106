package col106.assignment6;

class dualEdge {
    public String from;
    public String to;
    public int weight;

    public dualEdge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String toString() {
        return "(" + from + "," + to + "):" + weight + "\n";
    }
}