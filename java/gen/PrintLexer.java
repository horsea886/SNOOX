package gen;

import main.VerboseSONNXLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintLexer {
    public static void main(String[] args) throws Exception {
        // 1. 词法分析
        CharStream input = CharStreams.fromFileName("./ex1/src/main/resources/test10.txt");
        VerboseSONNXLexer lexer = new VerboseSONNXLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        tokens.fill(); // 触发词法分析

        // 打印词法分析结果
        lexer.printTokens();

    }

    private static void saveLexerOutput(VerboseSONNXLexer lexer, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("==== 词法分析结果 ====");
            writer.printf("%-20s %s %n",
                    "Token类型", "文本");
            writer.println("----------------------------------------");

            for (VerboseSONNXLexer.TokenInfo token : lexer.getTokens()) {
                writer.printf("%-20s %s ",
                        token.name,
                        truncateText(token.text)
                        );
            }
        }
    }

    private static String truncateText(String text) {
        if (text.length() > 20) {
            return text.substring(0, 17) + "...";
        }
        return text;
    }
}