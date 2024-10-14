package yeet.eliax00789.fusyn.interpreter.std.ast;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class TypeFunction implements Function {
    @Override
    public String getName() {
        return "ast::type";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("AST");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return switch (((TypedListASTNode) arguments.getFirst()).type) {
            case LEFT_PARENTHESIS -> "(";
            case LEFT_BRACE -> "{";
            case LEFT_BRACKET -> "[";
            case LEFT_ANGLE_BRACKET -> '<';
            case null, default -> throw new IllegalStateException("Unexpected");
        };
    }
}
