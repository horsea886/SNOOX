package main;

import node.*;

import java.util.ArrayList;
import java.util.List;

public class ASTNodeUtil {

    public static List<ASTNode> getChildren(ASTNode node) {
        List<ASTNode> children = new ArrayList<>();

        if (node instanceof ModelProtoNode) {
            ModelProtoNode m = (ModelProtoNode) node;
            if (m.graph != null) children.add(m.graph);
            if (m.opset_import != null) children.add(m.opset_import);
        }

        else if (node instanceof GraphNode) {
            GraphNode g = (GraphNode) node;
            if (g.node != null) {
                nodeNode cur = g.node;
                while (cur != null) {
                    children.add(cur);
                    cur = cur.next;
                }
            }

            if (g.input != null) {
                children.add(g.input);
            }
            if (g.output != null) {
                children.add(g.output);
            }

            if (g.initializer != null) {
                TensorNode cur = g.initializer;
                while (cur != null) {
                    children.add(cur);
                    cur = cur.next;
                }
            }
        }

        else if (node instanceof nodeNode) {
            nodeNode n = (nodeNode) node;
            if (n.attribute != null) {
                attributeNode cur = n.attribute;
                while (cur != null) {
                    children.add(cur);
                    cur = cur.next;
                }
            }
        }

        else if (node instanceof attributeNode) {
            attributeNode a = (attributeNode) node;
            attributeNode cur = a.next;
            while (cur != null) {
                children.add(cur);
                cur = cur.next;
            }
        }

        else if (node instanceof putNode) {
            return new ArrayList<ASTNode>();
        }

        else if (node instanceof TensorNode) {
            TensorNode t = (TensorNode) node;
            TensorNode cur = t.next;
            while (cur != null) {
                children.add(cur);
                cur = cur.next;
            }
        }

        return children;
    }
}
