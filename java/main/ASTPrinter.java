package main;

import node.ASTNode;
import node.GraphNode;
import node.putNode;
import java.util.List;

public class ASTPrinter {
    private static GraphNode lastGraphContext = null;


    public static void print(ASTNode root) {
        printNode(root, 0);
    }

    private static void printNode(ASTNode node, int indent) {
        if (node == null) return;

        StringBuilder indentStr = new StringBuilder();
        for (int i = 0; i < indent; i++) indentStr.append("  ");

        // 特别处理 input/output putNode
        if (node instanceof putNode && indentStr.length() >= 2) {
            // 使用调用栈向上一层推断类型
            if (lastGraphContext != null) {
                if (node == lastGraphContext.input) {
                    System.out.println(indentStr + "[Input]");
                } else if (node == lastGraphContext.output) {
                    System.out.println(indentStr + "[Output]");
                }
            }
        }

        System.out.println(indentStr + node.accept());

        List<ASTNode> children = ASTNodeUtil.getChildren(node);
        if (children != null) {
            if (node instanceof GraphNode) {
                lastGraphContext = (GraphNode) node;  // 用于识别 input/output
            }
            for (ASTNode child : children) {
                printNode(child, indent + 1);
            }
        }
    }

}
