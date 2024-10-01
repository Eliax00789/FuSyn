package yeet.eliax00789.fusyn.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.parser.type.StringContainer;
import yeet.eliax00789.fusyn.parser.type.TypedListContainer;
import yeet.eliax00789.fusyn.parser.type.UnionType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final ErrorReporter errorReporter;
    private final Tokenizer tokenizer;

    public Parser(@NotNull ErrorReporter errorReporter, @NotNull Tokenizer tokenizer) {
        this.errorReporter = errorReporter;
        this.tokenizer = tokenizer;
    }

    @Nullable
    public TypedListContainer parse() {
        return this.parseList(null);
    }

    @Nullable
    private TypedListContainer parseList(@Nullable TokenType type) {
        List<UnionType> list = new ArrayList<>();
        while_list:
        while (true) {
            Token token = this.tokenizer.next();
            if (token == null) {
                if (type == null) {
                    break;
                }
                this.errorReporter.error(0, "Parenthesis lul");
                return null;
            }
            switch (token.type()) {
                case LEFT_PARENTHESIS:
                case LEFT_BRACE:
                case LEFT_BRACKET: {
                    TypedListContainer childList = this.parseList(token.type());
                    if (childList == null) {
                        return null;
                    }
                    list.add(childList);
                    break;
                }
                case STRING:
                    list.add(new StringContainer(token.value()));
                    break;
                case RIGHT_PARENTHESIS:
                    if (type == TokenType.LEFT_PARENTHESIS) {
                        break while_list;
                    }
                    /* FALLTHROUGH: IF ERROR */
                case RIGHT_BRACE:
                    if (type == TokenType.LEFT_BRACE) {
                        break while_list;
                    }
                    /* FALLTHROUGH: IF ERROR */
                case RIGHT_BRACKET:
                    if (type == TokenType.LEFT_BRACKET) {
                        break while_list;
                    }

                    this.errorReporter.error(token.position(), "Parenthesis lul");
                    return null;
            }
        }
        return new TypedListContainer(list, type);
    }
}
