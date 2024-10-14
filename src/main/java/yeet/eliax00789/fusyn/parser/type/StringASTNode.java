package yeet.eliax00789.fusyn.parser.type;

public record StringASTNode(int position, String value) implements ASTNode {
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}
