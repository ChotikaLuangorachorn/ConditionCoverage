package draw;/* Simple graph drawing class
Bert Huang
COMS 3137 Data Structures and Algorithms, Spring 2009

This class is really elementary, but lets you draw
reasonably nice graphs/trees/diagrams. Feel free to
improve upon it!
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphDraw extends JFrame {
    int width;
    int height;

    ArrayList<Node> nodes;
    ArrayList<Edge> edges;

    public GraphDraw() { //Constructor
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        width = 30;
        height = 30;
    }

    public GraphDraw(String name) { //Construct with label
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();
        width = 30;
        height = 30;
    }


    public void addNode(Node node) {
        //add a node at pixel (x,y)
        nodes.add(node);
        this.repaint();
    }
//    public void addEdge(int i, int j) {
//        //add an edge between nodes i and j
//        edges.add(new Edge(i,j));
//        this.repaint();
//    }

    public void paint(Graphics g) { // draw the nodes and edges
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());


        // Draw Edge
//        g.setColor(Color.black);
//        for (Edge e : edges) {
//            g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y,
//                    nodes.get(e.j).x, nodes.get(e.j).y);
//        }
        Node node;
        for (int index = 1; index < nodes.size(); index++){
            node = nodes.get(index);
            g.setColor(Color.black);
            g.drawLine(node.parent.x, node.parent.y, node.x, node.y);
            int centerX = (node.parent.x + node.x) / 2;
            int centerY = (node.parent.y + node.y) / 2;
            g.fillPolygon(new int[] {centerX-2,centerX+2,node.x}, new int[]{centerY-1,centerY+1,node.y}, 3);

        }



        // Draw Node
        for (Node n : nodes) {

            int nodeWidth = Math.max(width, f.stringWidth(n.name)+width/2);
            g.setColor(Color.white);
            g.fillOval(n.x-nodeWidth/2, n.y-nodeHeight/2,
                    nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x-nodeWidth/2, n.y-nodeHeight/2,
                    nodeWidth, nodeHeight);
            g.drawString(n.name, n.x-f.stringWidth(n.name)/2,
                    n.y+f.getHeight()/2);

        }
    }
}

