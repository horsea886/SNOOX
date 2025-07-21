package main;

import node.*;
import gen.SONNXParser;
import gen.SONNXParserListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SONNXListener implements SONNXParserListener {

    private final Stack<ASTNode> stack = new Stack<>();
    private ASTNode root;
    private static final int KEY_VAL = 2;
    private List<Integer> dimList;

    public ASTNode getRoot(){
        return this.root;
    }

    private SourcePosition getPosition(ParserRuleContext ctx) {
        return new SourcePosition(
                ctx.getStart().getLine()
        );
    }

    @Override
    public void enterModel(SONNXParser.ModelContext ctx) {

        ModelProtoNode model = new ModelProtoNode();
        model.setPosition(getPosition(ctx));
        if(stack.isEmpty()){
            this.root = model;
        }
        stack.push(model);

    }

    @Override
    public void exitModel(SONNXParser.ModelContext ctx) {
        stack.pop();
    }

    @Override
    public void enterModel_body_def(SONNXParser.Model_body_defContext ctx) {

    }

    @Override
    public void exitModel_body_def(SONNXParser.Model_body_defContext ctx) {

    }

    @Override
    public void enterIr_version_def(SONNXParser.Ir_version_defContext ctx) {

        //赋值给上一层
        int irVersion = Integer.parseInt(ctx.children.get(KEY_VAL).getText());
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.ir_version = irVersion;

    }

    @Override
    public void exitIr_version_def(SONNXParser.Ir_version_defContext ctx) {

    }

    @Override
    public void enterProducer_name_def(SONNXParser.Producer_name_defContext ctx) {
        String producerName = ctx.children.get(KEY_VAL).getText();
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.producer_name = producerName;
    }

    @Override
    public void exitProducer_name_def(SONNXParser.Producer_name_defContext ctx) {

    }

    @Override
    public void enterProducer_version_def(SONNXParser.Producer_version_defContext ctx) {

        String producerVersion = ctx.children.get(KEY_VAL).getText();
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.producer_version = producerVersion;

    }

    @Override
    public void exitProducer_version_def(SONNXParser.Producer_version_defContext ctx) {

    }

    @Override
    public void enterDomain_def(SONNXParser.Domain_defContext ctx) {

        String domain = ctx.children.get(KEY_VAL).getText();
        //domain可以在
        ASTNode a = stack.peek();
        if(a instanceof ModelProtoNode){
            ((ModelProtoNode) a).domain = domain;
        }else {
            ((OpsetImportNode) a).domain = domain;
        }

    }

    @Override
    public void exitDomain_def(SONNXParser.Domain_defContext ctx) {

    }

    @Override
    public void enterModel_version_def(SONNXParser.Model_version_defContext ctx) {

        int modelVersion = Integer.parseInt(ctx.children.get(KEY_VAL).getText());
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.model_version = modelVersion;

    }

    @Override
    public void exitModel_version_def(SONNXParser.Model_version_defContext ctx) {

    }

    @Override
    public void enterDoc_string_def(SONNXParser.Doc_string_defContext ctx) {

        String docString = ctx.children.get(KEY_VAL).getText();
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.doc_string = docString;

    }

    @Override
    public void exitDoc_string_def(SONNXParser.Doc_string_defContext ctx) {

    }

    @Override
    public void enterGraph_def(SONNXParser.Graph_defContext ctx) {

        GraphNode graph = new GraphNode();
        graph.setPosition(getPosition(ctx));
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.graph = graph;
        stack.push(graph);

    }

    @Override
    public void exitGraph_def(SONNXParser.Graph_defContext ctx) {
        stack.pop();
    }

    @Override
    public void enterGraph_body_def(SONNXParser.Graph_body_defContext ctx) {

    }

    @Override
    public void exitGraph_body_def(SONNXParser.Graph_body_defContext ctx) {

    }

    @Override
    public void enterNode_list(SONNXParser.Node_listContext ctx) {

        nodeNode node = new nodeNode();
        node.setPosition(getPosition(ctx));
        GraphNode graphNode = (GraphNode) stack.peek();
        graphNode.node = node;
        stack.push(node);

    }

    @Override
    public void exitNode_list(SONNXParser.Node_listContext ctx) {
        stack.pop();
    }

    @Override
    public void enterNode_def(SONNXParser.Node_defContext ctx) {

        //是不是第一个
        nodeNode node = (nodeNode) stack.peek();
        if(node.getIsNil()){
            nodeNode newNode = new nodeNode();
            newNode.setPosition(getPosition(ctx));
            node.add(newNode);
        }

    }

    @Override
    public void exitNode_def(SONNXParser.Node_defContext ctx) {

    }

    @Override
    public void enterInput_arr(SONNXParser.Input_arrContext ctx) {

        ArrayList<String> inputs = new ArrayList<>();
        int childCount = ctx.getChildCount();
        childCount = (childCount - 3) / 2;
        for(int i = 0; i < childCount; i++){
            String in = ctx.children.get(3 + 2 * i).getText();
            inputs.add(in);
        }
        nodeNode node = (nodeNode) stack.peek();
        node.getLast().input_arr = inputs;

    }

    @Override
    public void exitInput_arr(SONNXParser.Input_arrContext ctx) {

    }

    @Override
    public void enterOutput_arr(SONNXParser.Output_arrContext ctx) {
        ArrayList<String> outputs = new ArrayList<>();
        int childCount = ctx.getChildCount();
        childCount = (childCount - 3) / 2;
        for(int i = 0; i < childCount; i++){
            String in = ctx.children.get(3 + 2 * i).getText();
            outputs.add(in);
        }
        nodeNode node = (nodeNode) stack.peek();
        node.getLast().output_arr = outputs;
    }

    @Override
    public void exitOutput_arr(SONNXParser.Output_arrContext ctx) {

    }

    @Override
    public void enterAttribute_list(SONNXParser.Attribute_listContext ctx) {

        attributeNode attribute = new attributeNode();
        attribute.setPosition(getPosition(ctx));
        nodeNode node = (nodeNode) stack.peek();
        node.attribute = attribute;
        stack.push(attribute);

    }

    @Override
    public void exitAttribute_list(SONNXParser.Attribute_listContext ctx) {
        stack.pop();
    }

    @Override
    public void enterAttribute_def(SONNXParser.Attribute_defContext ctx) {

        attributeNode attribute = (attributeNode) stack.peek();
        if(attribute.getIsNil()){
            attributeNode newAttr = new attributeNode();
            newAttr.setPosition(getPosition(ctx));
            attribute.add(newAttr);
        }

    }

    @Override
    public void exitAttribute_def(SONNXParser.Attribute_defContext ctx) {

    }

    @Override
    public void enterValue_def(SONNXParser.Value_defContext ctx) {


        String value = ctx.children.get(KEY_VAL).getText();
        attributeNode attribute = (attributeNode) stack.peek();
        attribute.setPosition(getPosition(ctx));
        attribute.value = value;

    }

    @Override
    public void exitValue_def(SONNXParser.Value_defContext ctx) {

    }

    @Override
    public void enterInput_list(SONNXParser.Input_listContext ctx) {

        putNode input = new putNode();
        input.setPosition(getPosition(ctx));
        ASTNode a = stack.peek();

        if (a instanceof GraphNode) {
            ((GraphNode) a).input = input;
            stack.push(input);
        } else if (a instanceof putNode) {
            ((putNode) a).next = input;
            stack.push(input);
        }

    }

    @Override
    public void exitInput_list(SONNXParser.Input_listContext ctx) {

        if (stack.peek() instanceof putNode) {
            stack.pop();
        }
    }

    @Override
    public void enterOutput_list(SONNXParser.Output_listContext ctx) {

        putNode output = new putNode();
        output.setPosition(getPosition(ctx));
        ASTNode a = stack.peek();
        if(a instanceof GraphNode){
            ((GraphNode) a).output = output;
            stack.push(output);
        }else {
            ((putNode) a).add(output);
        }

    }

    @Override
    public void exitOutput_list(SONNXParser.Output_listContext ctx) {

        putNode output = (putNode) stack.peek();
        if(output.getSize() > 1){
            output.remove();
        }else{
            stack.pop();
        }

    }

    @Override
    public void enterValue_info_def(SONNXParser.Value_info_defContext ctx) {

        putNode putNode = (putNode) stack.peek();
        putNode.setPosition(getPosition(ctx));
    }

    @Override
    public void exitValue_info_def(SONNXParser.Value_info_defContext ctx) {

    }

    @Override
    public void enterType_def(SONNXParser.Type_defContext ctx) {

        TensorTypeNode typeNode = new TensorTypeNode();
        typeNode.setPosition(getPosition(ctx));
        putNode out = (putNode) stack.peek();
        out.getLast().type = typeNode;
        stack.push(typeNode);

    }

    @Override
    public void exitType_def(SONNXParser.Type_defContext ctx) {
        stack.pop();
    }

    @Override
    public void enterTensor_type_def(SONNXParser.Tensor_type_defContext ctx) {

    }

    @Override
    public void exitTensor_type_def(SONNXParser.Tensor_type_defContext ctx) {

    }

    @Override
    public void enterElem_type_def(SONNXParser.Elem_type_defContext ctx) {

        String elemType = ctx.children.get(KEY_VAL).getText();
        TensorTypeNode typeNode = (TensorTypeNode) stack.peek();
        typeNode.elem_type = elemType;

    }

    @Override
    public void exitElem_type_def(SONNXParser.Elem_type_defContext ctx) {

    }

    @Override
    public void enterShape_def(SONNXParser.Shape_defContext ctx) {
        dimList = new ArrayList<>();
    }

    @Override
    public void exitShape_def(SONNXParser.Shape_defContext ctx) {

        TensorTypeNode typeNode = (TensorTypeNode) stack.peek();
        typeNode.dim_value = dimList;

    }

    @Override
    public void enterDim_list(SONNXParser.Dim_listContext ctx) {

    }

    @Override
    public void exitDim_list(SONNXParser.Dim_listContext ctx) {

    }

    @Override
    public void enterDim_def(SONNXParser.Dim_defContext ctx) {

    }

    @Override
    public void exitDim_def(SONNXParser.Dim_defContext ctx) {

    }

    @Override
    public void enterDim_value_def(SONNXParser.Dim_value_defContext ctx) {

        dimList.add(Integer.valueOf(ctx.children.get(KEY_VAL).getText()));

    }

    @Override
    public void exitDim_value_def(SONNXParser.Dim_value_defContext ctx) {

    }

    @Override
    public void enterDim_param_def(SONNXParser.Dim_param_defContext ctx) {

    }

    @Override
    public void exitDim_param_def(SONNXParser.Dim_param_defContext ctx) {

    }

    @Override
    public void enterInitializer_list(SONNXParser.Initializer_listContext ctx) {

        ASTNode a = stack.peek();
        TensorNode init = new TensorNode();
        init.setPosition(getPosition(ctx));
        if(a instanceof GraphNode){
            ((GraphNode) a).initializer= init;
            stack.push(init);
        }else {
            ((TensorNode) a).add(init);
        }

    }

    @Override
    public void exitInitializer_list(SONNXParser.Initializer_listContext ctx) {

        TensorNode t = (TensorNode) stack.peek();
        if(t.getSize() > 1){
            t.remove();
        }else {
            stack.pop();
        }

    }

    @Override
    public void enterTensor_def(SONNXParser.Tensor_defContext ctx) {

        TensorNode tensor = (TensorNode) stack.peek();
        tensor.setPosition(getPosition(ctx));
    }

    @Override
    public void exitTensor_def(SONNXParser.Tensor_defContext ctx) {

    }

    @Override
    public void enterData_type_def(SONNXParser.Data_type_defContext ctx) {

        String dataType = ctx.children.get(KEY_VAL).getText();
        TensorNode init = (TensorNode) stack.peek();
        init.getLast().dataType = dataType;

    }

    @Override
    public void exitData_type_def(SONNXParser.Data_type_defContext ctx) {

    }

    @Override
    public void enterDims_def(SONNXParser.Dims_defContext ctx) {

        List<Integer> dims = new ArrayList<>();
        int size = ctx.children.size();
        for(int i = 2; i < size; i++){
            int a = Integer.parseInt(ctx.children.get(i).getText());
            dims.add(a);
        }
        TensorNode init = (TensorNode) stack.peek();
        init.getLast().dims = dims;

    }

    @Override
    public void exitDims_def(SONNXParser.Dims_defContext ctx) {

    }

    @Override
    public void enterRaw_data_def(SONNXParser.Raw_data_defContext ctx) {

        String rawData = ctx.children.get(KEY_VAL).getText();
        TensorNode init = (TensorNode) stack.peek();
        init.getLast().rawData = rawData;

    }

    @Override
    public void exitRaw_data_def(SONNXParser.Raw_data_defContext ctx) {

    }

    @Override
    public void enterOpset_import_def(SONNXParser.Opset_import_defContext ctx) {

        OpsetImportNode op = new OpsetImportNode();
        op.setPosition(getPosition(ctx));
        ModelProtoNode model = (ModelProtoNode) stack.peek();
        model.opset_import = op;
        stack.push(op);

    }

    @Override
    public void exitOpset_import_def(SONNXParser.Opset_import_defContext ctx) {
        stack.pop();
    }

    @Override
    public void enterVersion_def(SONNXParser.Version_defContext ctx) {

        int version = Integer.parseInt(ctx.children.get(KEY_VAL).getText());
        OpsetImportNode opst = (OpsetImportNode) stack.peek();
        opst.version = version;

    }

    @Override
    public void exitVersion_def(SONNXParser.Version_defContext ctx) {

    }

    @Override
    public void enterName_def(SONNXParser.Name_defContext ctx) {

        String nameText = ctx.children.get(2).getText();  // KEY_VAL = 2
        ASTNode top = stack.peek();

        if (top instanceof GraphNode) {
            ((GraphNode) top).setName(nameText);
        } else if (top instanceof nameNode) {
            ((nameNode) top).setName(nameText);
        }

    }

    @Override
    public void exitName_def(SONNXParser.Name_defContext ctx) {

    }

    @Override
    public void enterOp_type_def(SONNXParser.Op_type_defContext ctx) {

        String opType = ctx.children.get(KEY_VAL).getText();
        nodeNode node = (nodeNode) stack.peek();
        node.op_type = opType;

    }

    @Override
    public void exitOp_type_def(SONNXParser.Op_type_defContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {
        System.err.println(errorNode.getText());
    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}
