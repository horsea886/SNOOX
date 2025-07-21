package node;

public class putNode extends ASTNode implements nameNode{
    public String name;
    public TensorTypeNode type;
    public putNode next;

    private int size = 1;
    public int getSize(){
        return size;
    }

    public putNode getLast(){
        putNode cur = this;
        while (cur.next != null){
            cur = cur.next;
        }
        return cur;
    }

    public void add(putNode val){
        getLast().next = val;
        size++;
    }

    @Override
    public String accept() {
        StringBuilder sb = new StringBuilder();
        putNode cur = this;
        int idx = 1;
        while (cur != null) {
            sb.append("putNode #").append(idx++).append(" [name=").append(cur.name);
            if (cur.type != null) {
                sb.append(", type=").append(cur.type.accept());
            }
            sb.append("]");
            if (cur.next != null) sb.append(" -> ");
            cur = cur.next;
        }
        return sb.toString();
    }



    public void setName(String name) {
        this.getLast().name = name;
    }

    public void remove() {
        size--;
    }
}
