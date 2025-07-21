package main;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;

import java.util.ArrayList;
import java.util.List;

public class LexicalErrorListener extends BaseErrorListener {
    private final List<CompilationError> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        errors.add(new CompilationError(
                CompilationError.ErrorType.LEXICAL,
                "非法字符: " + msg,
                line
        ));
    }

    public List<CompilationError> getErrors() {
        return errors;
    }
}