package draw;

public class TestGraphDraw {
    //Here is some example syntax for the GraphDraw class
    public static void main(String[] args) {
        GraphDraw frame = new GraphDraw("Test Window");
        frame.setSize(1500,500);
        frame.setVisible(true);

        Node n1 = new Node("1", new Node("",null,""), "");
        Node n5m12 = new Node("5m12", n1, "");
        Node n6d31 = new Node("6d31", n5m12, "L");
        Node n7 = new Node("7", n6d31, "L");

        frame.addNode(n1);
        frame.addNode(n5m12);
        frame.addNode(n6d31);
        frame.addNode(n7);
//        frame.addNode("longNode", 200, level2,"right");
//        frame.addNode("test", 200,level3, "left");
//        frame.addEdge(0,1);
//        frame.addEdge(1,2);
//        frame.addEdge(2,3);
    }
}