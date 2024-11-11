package yeet.eliax00789.fusyn.interpreter.std.file;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@SuppressWarnings("unused")
public class ReadStrFunction implements NativeFunction {
    @Override
    public String getName() {
        return "file::read-str";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("File");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("file");
    }

    @Override
    public String getReturnType() {
        return "Str";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        try {
            return Files.readString(((File) arguments.getFirst()).toPath());
        } catch (IOException e) {
            return null;
        }
    }
}
