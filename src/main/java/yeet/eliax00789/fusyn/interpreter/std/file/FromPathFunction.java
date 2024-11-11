package yeet.eliax00789.fusyn.interpreter.std.file;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.nio.file.Path;
import java.util.List;

@SuppressWarnings("unused")
public class FromPathFunction implements NativeFunction {
    @Override
    public String getName() {
        return "file::from-path";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Path");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("path");
    }

    @Override
    public String getReturnType() {
        return "File";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return ((Path) arguments.getFirst()).toFile();
    }
}
