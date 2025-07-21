package node;

public abstract class ASTNode {
    protected SourcePosition position;

    public abstract String accept();

    public void setPosition(SourcePosition pos) {
        this.position = pos;
    }

    public SourcePosition getPosition() {
        return position;
    }
}