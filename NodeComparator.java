package ResearchPaper2;

import java.util.Comparator;

class NodeComparator implements Comparator<Node> {
    public int compare(Node node1, Node node2) {
        return node2.getFrequency() - node1.getFrequency();
    }
}
