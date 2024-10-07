package yeet.eliax00789.fusyn.parser;

import yeet.eliax00789.fusyn.parser.type.StringASTNode;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;
import yeet.eliax00789.fusyn.parser.type.ASTNode;

public class DebugASTNodeVisitor implements ASTNode.Visitor<String> {
    @Override
    public String visit(StringASTNode stringASTNode) {
        return stringASTNode.value();
    }

    @Override
    public String visit(TypedListASTNode typedListASTNode) {
        Character start;
        Character end;
        //noinspection EnhancedSwitchMigration
        switch (typedListASTNode.type()) {
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
                throw new IllegalStateException();
        }

        StringBuilder sb = new StringBuilder();
        if (start != null) {
            sb.append(start);
        }
        boolean isFirst = true;
        for (ASTNode value : typedListASTNode.value()) {
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
