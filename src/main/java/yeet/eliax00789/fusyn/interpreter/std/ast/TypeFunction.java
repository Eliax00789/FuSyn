package yeet.eliax00789.fusyn.interpreter.std.ast;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class TypeFunction implements NativeFunction {
    @Override
    public String getName() {
        return "ast::type";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("AST");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("ast");
    }

    @Override
    public String getReturnType() {
        return "Str";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return switch (((TypedListASTNode) arguments.getFirst()).type) {
            case LEFT_PARENTHESIS -> "(";
            case LEFT_BRACE -> "{";
            case LEFT_BRACKET -> "[";
            case LEFT_ANGLE_BRACKET -> "<";
            case null, default -> throw new IllegalStateException("Unexpected");
        };
    }
}
