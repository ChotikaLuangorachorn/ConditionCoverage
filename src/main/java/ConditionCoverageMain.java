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
        String bracket = "";

        String line;
        Stack<Node> nodes = new Stack<>();
        ArrayList<Node> drawNodes = new ArrayList<>();
        Stack<String> brackets = new Stack<>();
        int i=1;
        boolean ifFirst = true;
        int num_elseif = 0;
        Node node = null;

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
                } else if(line.indexOf("else") != -1 && brackets.peek().equals("if")){
                    brackets.pop();
                    brackets.push("else");
                    brackets.push("{");
                    brackets.push("}");
                    name = (i+1) + "";
                    node = new Node(name, nodes.peek(), "R");
                    nodes.push(node);
                    drawNodes.add(node);
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
                if (brackets.peek().equals("{")){
                    if(line.indexOf("is") != -1){
                        brackets.push("if");
                        brackets.push("{");
                        name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                        node = new Node(name, nodes.peek(), "L");
                        nodes.push(node);
                        drawNodes.add(node);
                    }else{
                        brackets.push("if");
                        brackets.push("{");
                        name = i + " " + line.subSequence(line.indexOf("(") + 1, line.indexOf(")"));
                        node = new Node(name, nodes.peek(), "L");
                        nodes.push(node);
                        drawNodes.add(node);

                        name = (i+1) + "";
                        node = new Node(name, nodes.peek(), "L");
                        nodes.push(node);
                        drawNodes.add(node);
                        brackets.push("}");
                    }
                } else if (line.indexOf("else") != -1){

                    brackets.push("else");
                    brackets.push("{");
                    brackets.push("}");
                    name = (i+1) + "";
                    node = new Node(name, nodes.peek(), "R");
                    nodes.push(node);
                    drawNodes.add(node);
                    num_elseif = num_elseif+1;
                }


            }

            i++;
            System.out.println("i = " + i);
            System.out.println(brackets);


        }
        GraphDraw frame = new GraphDraw("Test Window");
        frame.setSize(1500, 800);
        frame.setVisible(true);

        for (Node n : drawNodes) {
            frame.addNode(n);
        }
    }
}