package node;

public class ModelProtoNode extends ASTNode{
    //    ir_version_def
    public int ir_version;
    //    producer_name_def
    public String producer_name;
    //    producer_version_def
    public String producer_version;
    //    domain_def
    public String domain;
    //    model_version_def
    public int model_version;
    //    doc_string_def
    public String doc_string;
    //    graph_def
    public GraphNode graph;
    //    opset_import_def
    public OpsetImportNode opset_import;

    @Override
    public String accept() {
        return "ModelProtoNode [ir_version=" + ir_version +
                ", producer_name=" + producer_name +
                ", producer_version=" + producer_version +
                ", domain=" + domain +
                ", model_version=" + model_version +
                ", doc_string=" + doc_string + "]";
    }

}
