package main;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TACInstruction {
    public String result;
    public String op;
    public List<String> operands;
    public Map<String, String> attributes;

    public TACInstruction(String result, String op, List<String> operands, Map<String, String> attrs) {
        this.result = result;
        this.op = op;
        this.operands = operands;
        this.attributes = attrs;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (result != null && !result.isEmpty()) {
            sb.append(result).append(" = ");
        }
        sb.append(op).append("(");
        if (operands != null) sb.append(String.join(", ", operands));
        if (attributes != null && !attributes.isEmpty()) {
            if (operands != null && !operands.isEmpty()) sb.append(", ");
            sb.append(attributes.entrySet().stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining(", ")));
        }
        sb.append(")");
        return sb.toString();
    }
}
