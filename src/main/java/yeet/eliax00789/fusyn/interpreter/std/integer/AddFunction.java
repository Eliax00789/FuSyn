package yeet.eliax00789.fusyn.interpreter.std.integer;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class AddFunction implements NativeFunction {
    @Override
    public String getName() {
        return "int::add";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Int", "Int");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("a", "b");
    }

    @Override
    public String getReturnType() {
        return "Int";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return (Integer) arguments.getFirst() + (Integer) arguments.getLast();
    }
}
