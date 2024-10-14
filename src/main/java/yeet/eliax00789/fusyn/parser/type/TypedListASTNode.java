package yeet.eliax00789.fusyn.parser.type;

import org.jetbrains.annotations.Nullable;
import yeet.eliax00789.fusyn.parser.TokenType;

import java.util.ArrayList;

public final class TypedListASTNode extends ArrayList<ASTNode> implements ASTNode {
    private final int position;
    public final TokenType type;

    public TypedListASTNode(int position, @Nullable TokenType type) {
        this.position = position;
        this.type = type;
    }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int position() {
        return position;
    }
}
