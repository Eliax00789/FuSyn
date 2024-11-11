package yeet.eliax00789.fusyn.interpreter.std.integer;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class FromStrFunction implements NativeFunction {
    @Override
    public String getName() {
        return "int::from-str";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value");
    }

    @Override
    public String getReturnType() {
        return "Int";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return Integer.parseInt((String) arguments.getFirst());
    }
}
