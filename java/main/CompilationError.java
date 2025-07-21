package main;

public class CompilationError {
    public enum ErrorType {
        LEXICAL,
        SYNTAX,
        SEMANTIC
    }

    public final ErrorType type;
    public final String message;
    public final int line;

    public CompilationError(ErrorType type, String message, int line) {
        this.type = type;
        this.message = message;
        this.line = line;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s at line %d",
                type.name(), message, line);
    }
}