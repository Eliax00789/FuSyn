package yeet.eliax00789.fusyn.interpreter.std.path;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.nio.file.Path;
import java.util.List;

@SuppressWarnings("unused")
public class FromStrFunction implements NativeFunction {
    @Override
    public String getName() {
        return "path::from-str";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("path");
    }

    @Override
    public String getReturnType() {
        return "Path";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return Path.of((String) arguments.getFirst());
    }
}
