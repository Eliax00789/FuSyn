package yeet.eliax00789.fusyn.interpreter.std.string;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class FromIntFunction implements NativeFunction {
    @Override
    public String getName() {
        return "str::from-int";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Int");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value");
    }

    @Override
    public String getReturnType() {
        return "Str";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return Integer.toString((Integer) arguments.getFirst());
    }
}
