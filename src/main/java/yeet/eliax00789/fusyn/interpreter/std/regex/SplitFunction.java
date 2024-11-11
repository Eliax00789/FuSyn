package yeet.eliax00789.fusyn.interpreter.std.regex;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class SplitFunction implements NativeFunction {
    @Override
    public String getName() {
        return "regex::split";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str", "Regex");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value", "regex");
    }

    @Override
    public String getReturnType() {
        return "List";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        String[] split = ((Pattern) arguments.getLast()).split((String) arguments.getFirst());
        return new ArrayList<>(List.of(split));
    }
}
