package node;

public class GraphNode extends ASTNode implements nameNode{
//    name_def
    public String name;
//    node_list
    public nodeNode node;
//    input_list
    public putNode input;
//    output_list
    public putNode output;
//    initializer_list?
    public TensorNode initializer;

    @Override
    public String accept() {
        return "GraphNode [name=" + (name != null ? name : "null") + "]";
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
