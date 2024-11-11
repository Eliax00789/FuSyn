package yeet.eliax00789.fusyn.interpreter.std.list;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class SizeFunction implements NativeFunction {
    @Override
    public String getName() {
        return "list::size";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("List");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("list");
    }

    @Override
    public String getReturnType() {
        return "Int";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return ((List<?>) arguments.getFirst()).size();
    }
}
