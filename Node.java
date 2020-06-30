package ResearchPaper2;

public class Node {
    protected String storedChars;
    protected Node parent;
    protected Node leftChild;
    protected Node rightChild;
    protected int frequency;
    protected String newBinary;

    public Node(String listChar) {
        this.storedChars = listChar;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.frequency = 0;
        this.newBinary = "";
    }

    public Node(String listChar, int freqNum) {
        this.storedChars = listChar;
        this.parent = null;
        this.leftChild = null;
        this.rightChild = null;
        this.frequency = freqNum;
        this.newBinary = "";
    }

    public Node(String listChar, int freqNum, Node leftChild, Node rightChild) {
        this.storedChars = listChar;
        this.parent = null;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.frequency = freqNum;
        this.newBinary = "";
    }

    public void setStoredChars(String storedChars) {
        this.storedChars = storedChars;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setNewBinary(String biNum) {
        this.newBinary = biNum;
    }

    public String getStoredChars() {
        return this.storedChars;
    }

    public Node getParent() {
        return this.parent;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public String getNewBinary() {
        return this.newBinary;
    }
}
