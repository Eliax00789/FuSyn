package yeet.eliax00789.fusyn.interpreter.std;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class IfFunction implements NativeFunction {
    @Override
    public String getName() {
        return "if";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Bool", "AST");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("condition", "code");
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
