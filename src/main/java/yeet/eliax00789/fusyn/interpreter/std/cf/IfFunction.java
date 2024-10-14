package yeet.eliax00789.fusyn.interpreter.std.cf;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class IfFunction implements Function {
    @Override
    public String getName() {
        return "cf::if";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Bool", "AST");
    }

    @Override
    public String getReturnType() {
        return "Any";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return (boolean) arguments.getFirst()
                ? interpreter.exec((TypedListASTNode) arguments.getLast())
                : null;
    }
}
