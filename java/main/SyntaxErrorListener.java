package main;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Parser;

import java.util.ArrayList;
import java.util.List;

public class SyntaxErrorListener extends BaseErrorListener {
    private final List<CompilationError> errors = new ArrayList<>();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine,
                            String msg, RecognitionException e) {
        String errorMsg;
        if (msg.contains("missing")) {
            errorMsg = "缺少符号: " + msg.replace("missing ", "");
        } else if (msg.contains("extraneous")) {
            errorMsg = "多余符号: " + msg.replace("extraneous input ", "");
        } else {
            errorMsg = "结构不匹配: " + msg;
        }

        errors.add(new CompilationError(
                CompilationError.ErrorType.SYNTAX,
                errorMsg,
                line
        ));
    }

    public List<CompilationError> getErrors() {
        return errors;
    }
}