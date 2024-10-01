package yeet.eliax00789.fusyn.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Tokenizer {
    private final String source;
    private int start;
    private int current;

    public Tokenizer(@NotNull String source) {
        this.source = source;
        this.start = 0;
        this.current = 0;
    }

    @Nullable
    public Token next() {
        if (this.isEnd()) {
            return null;
        }
        this.start = this.current;
        char startChar = this.source.charAt(this.current++);
        return switch (startChar) {
            case ' ', '\r', '\n', '\t' -> this.next();
            case '(' -> new Token(TokenType.LEFT_PARENTHESIS, null, this.start);
            case ')' -> new Token(TokenType.RIGHT_PARENTHESIS, null, this.start);
            case '{' -> new Token(TokenType.LEFT_BRACE, null, this.start);
            case '}' -> new Token(TokenType.RIGHT_BRACE, null, this.start);
            case '[' -> new Token(TokenType.LEFT_BRACKET, null, this.start);
            case ']' -> new Token(TokenType.RIGHT_BRACKET, null, this.start);
            default -> this.handleString(startChar);
        };
    }

    @NotNull
    private Token handleString(char startChar) {
        StringBuilder text = new StringBuilder();
        text.append(startChar);

        boolean isEscaped = false;

        str_loop:
        while (!this.isEnd()) {
            char currentChar = this.source.charAt(this.current++);
            if (isEscaped) {
                currentChar = switch (currentChar) {
                    case 'b' -> '\b';
                    case 'f' -> '\f';
                    case 'n' -> '\n';
                    case 'r' -> '\r';
                    case 't' -> '\t';
                    default -> currentChar;
                };
            } else {
                switch (currentChar) {
                    case '\\':
                        isEscaped = true;
                        continue;
                    case ' ', '\r', '\n', '\t', '(', ')', '{', '}', '[', ']':
                        this.current--;
                        break str_loop;
                }
            }

            text.append(currentChar);
            isEscaped = false;
        }

        return new Token(TokenType.STRING, text.toString(), this.start);
    }

    private boolean isEnd() {
        return this.current >= this.source.length();
    }
}
