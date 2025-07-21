package node;

import java.util.ArrayList;
import java.util.List;

public class TensorNode extends ASTNode implements nameNode{
    public String name;
    public String dataType;
    public List<Integer> dims = new ArrayList<>();
    public String rawData;
    public TensorNode next;
    private int size = 1;

    public int getSize(){
        return size;
    }

    public TensorNode getLast(){
        TensorNode cur = this;
        while (cur.next != null){
            cur = cur.next;
        }
        return cur;
    }

    public void add(TensorNode init){
        getLast().next = init;
        size++;
    }
    public void remove(){
        size--;
    }

    @Override
    public String accept() {
        StringBuilder sb = new StringBuilder();
        TensorNode cur = this;
        int idx = 1;
        while (cur != null) {
            sb.append("TensorNode #").append(idx++).append(" [name=").append(cur.name)
                    .append(", dataType=").append(cur.dataType)
                    .append(", dims=").append(cur.dims)
                    .append(", rawData=").append(cur.rawData).append("]");
            if (cur.next != null) sb.append(" -> ");
            cur = cur.next;
        }
        return sb.toString();
    }




    public void setName(String name) {
        this.getLast().name = name;
    }
}
