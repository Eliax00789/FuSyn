package yeet.eliax00789.fusyn.interpreter.std.string;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class ConcatFunction implements NativeFunction {
    @Override
    public String getName() {
        return "str::concat";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str", "Str");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("a", "b");
    }

    @Override
    public String getReturnType() {
        return "Str";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return arguments.getFirst() + (String) arguments.getLast();
    }
}
