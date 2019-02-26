public class Node {
    public int key;
    public String name;
    public Node leftChild, rightChild;

    public Node(int key, String name)
    {
        this.key = key;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " has a key " + key;
    }
}
