package yeet.eliax00789.fusyn.parser.type;

public record StringContainer(String value) implements UnionType {
    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visit(this);
    }
}
