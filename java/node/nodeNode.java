package node;

import java.util.ArrayList;

public class nodeNode extends ASTNode implements nameNode{
//    op_type_def
    public String op_type;
//    name_def
    public String name;
//    input_arr
    public ArrayList<String> input_arr = new ArrayList<>();
//    output_arr
    public ArrayList<String> output_arr = new ArrayList<>();
//    attribute_list?
    public attributeNode attribute;

    public nodeNode next;

    private boolean isNil;

    public void add(nodeNode node){
        getLast().next = node;
    }

    public boolean getIsNil(){
        boolean b = this.isNil;
        change();
        return b;
    }

    public void change(){
        this.isNil = true;
    }

    public nodeNode getLast(){
        nodeNode cur = this;
        while(cur.next != null){
            cur = cur.next;
        }
        return cur;
    }

    public nodeNode(){
        this.isNil = false;
    }

    @Override
    public String accept() {
        return "nodeNode [op_type=" + op_type +
                ", name=" + name +
                ", input_arr=" + input_arr +
                ", output_arr=" + output_arr + "]";
    }

    @Override
    public void setName(String name) {
        this.getLast().name = name;
    }

}
