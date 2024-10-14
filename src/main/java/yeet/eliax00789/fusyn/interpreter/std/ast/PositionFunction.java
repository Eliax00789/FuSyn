package yeet.eliax00789.fusyn.interpreter.std.ast;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class PositionFunction implements Function {
    @Override
    public String getName() {
        return "ast::position";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("AST");
    }

    @Override
    public String getReturnType() {
        return "Int";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return ((TypedListASTNode) arguments.getFirst()).position();
    }
}
