package draw;


public class Node {
    public int x, y;
    public String name, position;
    public Node parent;

    public Node(String myName, Node parent, String position) {
        this.name = myName;
        this.position = position;
        this.parent = parent;
        if (parent == null){
            this.x = 400;
            this.y = 50;
        }
        else {
            if (this.position.equals("L")) {
                this.x = parent.x - 50;
                this.y = parent.y + 50;
            } else if(this.position.equals("R")){
                this.x = parent.x + 50;
                this.y = parent.y + 50;
            } else {
                this.x = parent.x;
                this.y = parent.y + 50;
            }
        }


    }
}
