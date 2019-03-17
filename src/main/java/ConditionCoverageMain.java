import draw.GraphDraw;
import draw.Node;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

class ConditionCoverageMain{
    public static void main(String[] args) throws IOException {
        String fileName = "./pseudoCode.txt";
        File file = new File(fileName);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
        BufferedReader br = new BufferedReader(fr);
        String line;
        Stack<Node> nodes = new Stack<>();
        ArrayList<Node> drawNodes = new ArrayList<>();
        Stack<String> brackets = new Stack<>();
        int i=1;
        boolean ifFirst = true;
        int num_elseif = 0;
        Node node = null;
        String[] line_or = null;
        Node last_node_else_if =null;
        Stack<Node> last_nodes = new Stack<>();
        while((line = br.readLine()) != null) {
            //process the line
            System.out.print(i + "\t");
            System.out.println(line);
            if (i == 1) {
                node = new Node("1", new Node("", null, ""), "");
                nodes.push(node);
                drawNodes.add(node);
            }
            String name;

            if (!brackets.empty() && brackets.peek().equals("}")) {
                brackets.pop();
                brackets.pop();
                nodes.pop();
            }

            if (line.indexOf("if") != -1 && ifFirst) {
                name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                node = new Node(name, nodes.get(0), "");
                nodes.push(node);
                drawNodes.add(node);
                ifFirst = false;
                brackets.push("if");
                brackets.push("{");

            }
            else if(num_elseif==0){
                if (line.indexOf("if") != -1 && brackets.peek().equals("{") && num_elseif==0){
                    brackets.push("if");
                    brackets.push("{");
                    brackets.push("}");
                    name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                    node = new Node(name, nodes.peek(), "L");
                    node.x = node.x-100;

                    nodes.push(node);
                    drawNodes.add(node);
                    name = (i + 1) + "";
                    node = new Node(name, nodes.peek(), "L");
                    nodes.push(node);
                    drawNodes.add(node);
                    last_nodes.push(node);
                } else if(line.indexOf("else") != -1 && brackets.peek().equals("if")){
                    brackets.pop();
                    brackets.push("else");
                    brackets.push("{");
                    brackets.push("}");
                    name = (i+1) + "";
                    node = new Node(name, nodes.peek(), "R");
                    nodes.push(node);
                    drawNodes.add(node);
                    last_nodes.push(node);
                }

            }
            if (line.indexOf("else if") != -1 && num_elseif == 0 && brackets.peek().equals("else")){
                brackets.pop();
                brackets.push("}");
                nodes.pop();
                brackets.pop();
                brackets.pop();
                brackets.pop();
                brackets.push("else if");
                brackets.push("{");
                name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                node = new Node(name, nodes.peek(), "R");
                node.x =node.x+100;
                nodes.push(node);
                drawNodes.add(node);
                num_elseif = num_elseif +1;
//                brackets.pop();
            }else if (num_elseif==1){
                boolean leap = false;
                if (brackets.peek().equals("{") & !leap){
                    if(line.indexOf("is") != -1){
                        brackets.push("if");
                        brackets.push("{");
                        name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                        node = new Node(name, nodes.peek(), "L");
                        node.x = node.x -50;
                        nodes.push(node);
                        drawNodes.add(node);
                    }else{
//                        brackets.push("if");
//                        brackets.push("{");
                        name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                        node = new Node(name, nodes.peek(), "L");
                        node.x = node.x -50;
                        nodes.push(node);
                        drawNodes.add(node);

                        name = (i+1) + "";
                        node = new Node(name, nodes.peek(), "L");
                        drawNodes.add(node);
                        last_nodes.push(node);

                        name = (i+3) + "";
                        node = new Node(name, nodes.peek(), "R");
                        drawNodes.add(node);
                        last_nodes.push(node);
                        brackets.pop();
                        brackets.pop();
                        brackets.push("else");
                        leap = true;

                    }
                }else if (i>18){
                    nodes.pop();
                    brackets.push("{");
                    name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                    node = new Node(name, nodes.peek(), "R");
                    drawNodes.add(node);
                    nodes.push(node);
                    name = (i+1) + "";
                    node = new Node(name, nodes.peek(), "L");
                    drawNodes.add(node);
                    last_nodes.push(node);

                    name = (i+3) + "";
                    node = new Node(name, nodes.peek(), "R");
                    drawNodes.add(node);
                    last_nodes.push(node);


                    num_elseif=num_elseif+1;
                    brackets.pop();
                    brackets.pop();

                }

            }

            if(num_elseif==2){

                if(line.indexOf("||") != -1){
                    nodes.pop();
                    nodes.pop();
                    line = line.subSequence(line.indexOf("(") + 1, line.indexOf(")"))+"";
                    System.out.println(line);
                    line_or = line.split("\\|\\|");
                    System.out.println(line_or.length);
                    for (int s =0 ; s<line_or.length;s++) {
                        name = i + line_or[s];
                        node = new Node(name, nodes.peek(), "R");
                        node.x = node.x +50;
                        drawNodes.add(node);
                        nodes.push(node);
                        last_node_else_if = node;

                    }
                }else if (i>25){
                    Node[] n =  new Node[line_or.length];

                    for (int s = 0 ; s<line_or.length; s++) {
                        name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                        node = new Node( name, nodes.peek(), "L");
                        node.x = node.x - 350;
                        n[s] = node;
                    }
                    for ( Node no: n) {
                        drawNodes.add(no);
                        no.parent = nodes.pop();
                        System.out.println(no);
                    }
                    num_elseif++;

                    name = (i+1) + "";
                    drawNodes.add(new Node( name, node, "L"));
                    last_nodes.push(new Node( name, node, "L"));
                    name = (i+3) + "";
                    drawNodes.add(new Node( name, node, "R"));
                    last_nodes.push(new Node( name, node, "R"));
                    brackets.pop();
                    brackets.pop();
                    line = br.readLine();
                    line = br.readLine();
                    line = br.readLine();
                    line = br.readLine();
                    line = br.readLine();
                    line =  br.readLine();
                    i = i+6;

                }


            }
            if (num_elseif > 2 && brackets.empty()){
                brackets.push("else");
                brackets.push("{");
                name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                System.out.println(last_node_else_if);
                node = new Node(name, last_node_else_if, "R");
                drawNodes.add(node);
                nodes.push(node);

                name = (i+1) + "";
                node = new Node(name, nodes.peek(), "L");
                drawNodes.add(node);
                last_nodes.push(node);

                name = (i+3) + "";
                node = new Node(name, nodes.peek(), "R");
                drawNodes.add(node);
                last_nodes.push(node);

            }
            if (line.indexOf("return")!=-1){
                break;
            }
            i++;
            System.out.println("i = " + i);
            System.out.println(brackets);


        }
        Node node_return = new Node(i+"",last_nodes.pop(),"L");
        node_return.x = node_return.x - 1000;
        node_return.y = node_return.y + 50;
        drawNodes.add(node_return);
        while (!last_nodes.empty()){
            node = new Node(i+"",last_nodes.pop(),"L");
            node.x= node_return.x;
            node.y = node_return.y;


            drawNodes.add(node);
        }

        GraphDraw frame = new GraphDraw("Test Window");
        frame.setSize(1500, 800);
        frame.setVisible(true);

        for (Node n : drawNodes) {
            frame.addNode(n);
        }
    }
}