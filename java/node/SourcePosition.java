package node;

public class SourcePosition {
    public final int line;

    public SourcePosition(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        return line + ";" ;
    }
}