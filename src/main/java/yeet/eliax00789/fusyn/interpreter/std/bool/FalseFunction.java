package yeet.eliax00789.fusyn.interpreter.std.bool;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class FalseFunction implements NativeFunction {
    @Override
    public String getName() {
        return "bool::false";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of();
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of();
    }

    @Override
    public String getReturnType() {
        return "Bool";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return false;
    }
}
