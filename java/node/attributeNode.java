package node;

public class attributeNode extends ASTNode implements nameNode{
    public String name;
    public String value;

    public attributeNode next;

    private boolean isNil;

    public attributeNode(){
        this.isNil = false;
    }

    public boolean getIsNil(){
        boolean b = this.isNil;
        change();
        return b;
    }

    private void change() {
        this.isNil = true;
    }

    public void add(attributeNode att){
        getLast().next = att;
    }

    public attributeNode getLast(){
        attributeNode cur = this;
        while (cur.next != null){
            cur = cur.next;
        }
        return cur;
    }

    @Override
    public String accept() {
        StringBuilder sb = new StringBuilder();
        attributeNode cur = this;
        int idx = 1;
        while (cur != null) {
            sb.append("attributeNode #").append(idx++).append(" [name=")
                    .append(cur.name).append(", value=").append(cur.value).append("]");
            if (cur.next != null) sb.append(" -> ");
            cur = cur.next;
        }
        return sb.toString();
    }


    @Override
    public void setName(String name) {
        this.getLast().name = name;
    }
}
