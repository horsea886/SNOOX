package node;

public class OpsetImportNode extends ASTNode{

    public String domain;
    public int version;

    @Override
    public String accept() {
        return "OpsetImportNode [domain=" + domain + ", version=" + version + "]";
    }

}
