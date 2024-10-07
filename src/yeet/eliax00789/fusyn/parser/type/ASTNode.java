package yeet.eliax00789.fusyn.parser.type;

public interface ASTNode {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visit(StringASTNode stringASTNode);
        R visit(TypedListASTNode typedListASTNode);
    }
}
