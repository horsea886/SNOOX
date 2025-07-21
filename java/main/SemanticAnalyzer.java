package main;

import node.*;
import java.util.*;

public class SemanticAnalyzer {
    private final Set<String> tensorNames = new HashSet<>();
    private final Set<String> nodeNames = new HashSet<>();
    private final Set<String> outputNames = new HashSet<>();
    private final Map<String, String> tensorTypes = new HashMap<>();
    private final List<String> errors = new ArrayList<>();

    public void analyze(ModelProtoNode model) {
        if (model.graph != null) analyzeGraph(model.graph);
    }

    private void analyzeGraph(GraphNode graph) {
        // 注册 input/output/initializer
        registerPutNodes(graph.input);
        registerPutNodes(graph.output);
        registerInitializers(graph.initializer);

        // 分析节点
        nodeNode node = graph.node;
        while (node != null) {
            checkNodeName(node);
            checkInputsDefined(node);
            checkOutputUniqueness(node);
            checkInputTypeConsistency(node);
            node = node.next;
        }
    }

    private void registerPutNodes(putNode node) {
        while (node != null) {
            if (!tensorNames.add(node.name)) {
                errors.add("张量重复定义: " + node.name);
            }
            if (node.type != null) {
                tensorTypes.put(node.name, node.type.elem_type);
            }
            node = node.next;
        }
    }

    private void registerInitializers(TensorNode node) {
        while (node != null) {
            if (!tensorNames.add(node.name)) {
                errors.add("张量重复定义: " + node.name);
            }
            if (node.dataType != null) {
                tensorTypes.put(node.name, node.dataType);
            }
            node = node.next;
        }
    }

    private void checkNodeName(nodeNode node) {
        if (!nodeNames.add(node.name)) {
            errors.add("节点名称重复: " + node.name);
        }
    }

    private void checkInputsDefined(nodeNode node) {
        for (String input : node.input_arr) {
            if (!tensorNames.contains(input)) {
                errors.add("使用未定义张量: " + input + " in node " + node.name);
            }
        }
    }

    private void checkOutputUniqueness(nodeNode node) {
        for (String output : node.output_arr) {
            if (!outputNames.add(output)) {
                errors.add("输出张量名称冲突: " + output + " in node " + node.name);
            }
            tensorNames.add(output); // 标记为已定义
        }
    }

    private void checkInputTypeConsistency(nodeNode node) {
        Set<String> types = new HashSet<>();
        for (String input : node.input_arr) {
            String type = tensorTypes.get(input);
            if (type != null) types.add(type);
        }
        if (types.size() > 1) {
            errors.add("输入张量类型不一致 in node " + node.name + ": " + types);
        }
    }


    private void addError(String message, ASTNode node) {
        String pos = node.getPosition() != null ?
                " at " + node.getPosition().toString() : "";
        errors.add(message + pos);
    }
    public List<String> getErrors() {
        return errors;
    }
}

