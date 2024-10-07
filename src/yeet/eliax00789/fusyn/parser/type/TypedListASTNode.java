package yeet.eliax00789.fusyn.parser.type;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yeet.eliax00789.fusyn.parser.TokenType;

import java.util.List;

public record TypedListASTNode(int position, @NotNull List<ASTNode> value, @Nullable TokenType type) implements ASTNode {
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}
