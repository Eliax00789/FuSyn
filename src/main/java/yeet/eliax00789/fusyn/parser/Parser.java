package yeet.eliax00789.fusyn.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.parser.type.StringASTNode;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;
import yeet.eliax00789.fusyn.parser.type.ASTNode;

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
    public TypedListASTNode parse() {
        return this.parseList(0, null);
    }

    @Nullable
    private TypedListASTNode parseList(int position, @Nullable TokenType type) {
        List<ASTNode> list = new ArrayList<>();
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
                    TypedListASTNode childList = this.parseList(token.position(), token.type());
                    if (childList == null) {
                        return null;
                    }
                    list.add(childList);
                    break;
                }
                case STRING:
                    list.add(new StringASTNode(token.position(), token.value()));
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
        TypedListASTNode typedListASTNode = new TypedListASTNode(position, type);
        typedListASTNode.addAll(list);
        return typedListASTNode;
    }
}
