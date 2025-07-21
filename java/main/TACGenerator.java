package main;
import node.*;
import java.util.*;

public class TACGenerator {
    private final List<String> code = new ArrayList<>();
    private int inputCount = 1;
    private int initCount = 1;
    private int outputCount = 1;
    private int tempCount = 1;
    private final Map<String, String> nameToTemp = new HashMap<>();

    public void generate(ModelProtoNode model) {
        if (model.graph != null) {
            generateGraph(model.graph);
        }
    }

    private void generateGraph(GraphNode graph) {
        // === Input 定义 ===
        for (putNode p = graph.input; p != null; p = p.next) {
            if (p.type != null) {
                String temp = "T" + inputCount++;
                nameToTemp.put(p.name, temp);
                String shape = p.type.dim_value.toString();
                String line = String.format("%s = Input(%s, %s, %s)",
                        temp,
                        p.name,
                        p.type.elem_type,
                        shape);
                code.add(line);
            }
        }

        // === 初始权重 ===
        for (TensorNode t = graph.initializer; t != null; t = t.next) {
            String temp = "W" + initCount++;
            nameToTemp.put(t.name, temp);
            String shape = t.dims.toString();
            String raw = t.rawData;
            String line = String.format("%s = Initializer(%s, %s, %s, raw_data=%s)",
                    temp, t.name, t.dataType, shape, raw);
            code.add(line);
        }

        // === 节点 ===
        for (nodeNode n = graph.node; n != null; n = n.next) {
            String result = "O" + tempCount++;
            nameToTemp.put(n.output_arr.get(0), result);
            String op = n.op_type;
            List<String> operands = new ArrayList<>();
            for (String input : n.input_arr) {
                operands.add(nameToTemp.getOrDefault(input, input));
            }
            String inputStr = String.join(", ", operands);
            Map<String, String> attrs = new LinkedHashMap<>();
            if (n.attribute != null) {
                attributeNode a = n.attribute;
                while (a != null) {
                    attrs.put(a.name, a.value);
                    a = a.next;
                }
            }
            StringBuilder attrStr = new StringBuilder();
            for (Map.Entry<String, String> e : attrs.entrySet()) {
                attrStr.append(e.getKey()).append("=").append(e.getValue()).append(", ");
            }
            if (attrStr.length() > 0) attrStr.setLength(attrStr.length() - 2);

            String line = String.format("%s = %s(%s%s%s)",
                    result,
                    op,
                    inputStr,
                    (inputStr.length() > 0 && attrStr.length() > 0) ? ", " : "",
                    attrStr.toString());
            code.add(line);
        }


        for (putNode p = graph.output; p != null; p = p.next) {
            String var = nameToTemp.getOrDefault(p.name, p.name);
            String line = String.format("Output(%s, %s)", p.name, var);
            code.add(line);
        }
    }

    public void printCode() {
        System.out.println("==== 三地址码生成 ====");
        for (String line : code) {
            System.out.println(line);
        }
    }

    public List<String> getCode() {
        return code;
    }
}
