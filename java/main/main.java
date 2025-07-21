package main;
import node.*;
import gen.SONNXLexer;
import gen.SONNXParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;
import java.util.List;

public class main {
    static String filename;
    public static void main(String[] args) throws Exception {
        // 1. 初始化错误收集器
        LexicalErrorListener lexErrorListener = new LexicalErrorListener();
        SyntaxErrorListener parseErrorListener = new SyntaxErrorListener();
        List<CompilationError> allErrors = new ArrayList<>();

        // 2. 词法分析
        CharStream input = CharStreams.fromFileName("./ex1/src/main/resources/test1.txt");//testn.txt---n是1~10,自行修改n的值
        filename=input.getSourceName();
        SONNXLexer lexer = new SONNXLexer(input);
        lexer.removeErrorListeners();
        lexer.addErrorListener(lexErrorListener);


        // 3. 语法分析
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        SONNXParser parser = new SONNXParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(parseErrorListener);
        ParseTree tree = parser.model();

        // 4. 收集词法/语法错误
        allErrors.addAll(lexErrorListener.getErrors());
        allErrors.addAll(parseErrorListener.getErrors());

        // 5. 如果有词法/语法错误则提前终止
        if (!allErrors.isEmpty()) {
            reportErrors(allErrors);
            return;
        }

        // 6. 构建AST
        SONNXListener listener = new SONNXListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        ASTNode astRoot = listener.getRoot();

        // 7. 语义分析
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        analyzer.analyze((ModelProtoNode) astRoot);

        // 8. 收集语义错误
        for (String error : analyzer.getErrors()) {
            // 语义错误暂时没有精确位置
            allErrors.add(new CompilationError(
                    CompilationError.ErrorType.SEMANTIC,
                    error,
                    0
            ));
        }

        // 9. 报告所有错误
        if (!allErrors.isEmpty()) {
            reportErrors(allErrors);
            return;
        }

        // 10. 打印AST
        System.out.println("==== 抽象语法树结构 ====");
        ASTPrinter.print(astRoot);

        // 11. 中间代码生成
        TACGenerator tacGen = new TACGenerator();
        tacGen.generate((ModelProtoNode) astRoot);
        tacGen.printCode();
    }

    private static void reportErrors(List<CompilationError> errors) {
        System.err.println("文件"+filename+"有误");
        System.err.println("编译失败，发现 " + errors.size() + " 个错误:");
        for (CompilationError error : errors) {
            System.err.println("  - " + error);
        }
    }
}