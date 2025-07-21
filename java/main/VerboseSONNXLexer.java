package main;

import gen.SONNXLexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.Vocabulary;
import java.util.ArrayList;
import java.util.List;


public class VerboseSONNXLexer extends SONNXLexer {
    private final Vocabulary vocabulary;
    private final List<TokenInfo> tokens = new ArrayList<>();

    public VerboseSONNXLexer(CharStream input) {
        super(input);
        vocabulary = getVocabulary();
    }

    @Override
    public Token nextToken() {
        Token token = super.nextToken();
        if (token.getType() != EOF) {
            String tokenName = vocabulary.getSymbolicName(token.getType());
            tokens.add(new TokenInfo(tokenName, token.getText()));
        }
        return token;
    }

    public List<TokenInfo> getTokens() {
        return tokens;
    }

    public void printTokens() {
        System.out.println("==== 词法分析结果 ====");
        System.out.printf("%-20s %s %n",
                "Token类型", "文本");
        System.out.println("----------------------------------------------------");

        for (TokenInfo token : tokens) {
            System.out.printf("%-20s %s %n",
                    token.name,
                    truncateText(token.text)
                    );
        }
    }

    private String truncateText(String text) {
        if (text.length() > 20) {
            return text.substring(0, 17) + "...";
        }
        return text;
    }

    public static class TokenInfo {
        public final String name;
        public final String text;


        public TokenInfo(String name, String text) {
            this.name = name;
            this.text = text;

        }
    }
}