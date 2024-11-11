package yeet.eliax00789.fusyn.interpreter.std.bool;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class NotFunction implements NativeFunction {
    @Override
    public String getName() {
        return "bool::not";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Bool");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value");
    }

    @Override
    public String getReturnType() {
        return "Bool";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return !((boolean) arguments.getLast());
    }
}
