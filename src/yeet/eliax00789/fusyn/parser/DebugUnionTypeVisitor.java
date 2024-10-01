package yeet.eliax00789.fusyn.parser;

import yeet.eliax00789.fusyn.parser.type.StringContainer;
import yeet.eliax00789.fusyn.parser.type.TypedListContainer;
import yeet.eliax00789.fusyn.parser.type.UnionType;

public class DebugUnionTypeVisitor implements UnionType.Visitor<String> {
    @Override
    public String visit(StringContainer stringContainer) {
        return stringContainer.value();
    }

    @Override
    public String visit(TypedListContainer typedListContainer) {
        Character start;
        Character end;
        //noinspection EnhancedSwitchMigration
        switch (typedListContainer.type()) {
            case null:
                start = null;
                end = null;
                break;
            case LEFT_PARENTHESIS:
                start = '(';
                end = ')';
                break;
            case LEFT_BRACE:
                start = '{';
                end = '}';
                break;
            case LEFT_BRACKET:
                start = '[';
                end = ']';
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typedListContainer.type());
        }

        StringBuilder sb = new StringBuilder();
        if (start != null) {
            sb.append(start);
        }
        boolean isFirst = true;
        for (UnionType value : typedListContainer.value()) {
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append(' ');
            }
            sb.append(value.accept(this));
        }
        if (end != null) {
            sb.append(end);
        }
        return sb.toString();
    }
}
