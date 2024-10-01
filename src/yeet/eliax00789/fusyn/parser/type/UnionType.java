package yeet.eliax00789.fusyn.parser.type;

public interface UnionType {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {
        R visit(StringContainer stringContainer);
        R visit(TypedListContainer typedListContainer);
    }
}
