package node;

import java.util.ArrayList;
import java.util.List;

public class TensorTypeNode extends ASTNode{
    public String elem_type;
    public List<Integer> dim_value = new ArrayList<>();

    @Override
    public String accept() {
        return "TensorTypeNode [elem_type=" + elem_type +
                ", dim_value=" + dim_value + "]";
    }

}
