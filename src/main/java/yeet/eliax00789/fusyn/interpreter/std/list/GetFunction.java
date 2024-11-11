package yeet.eliax00789.fusyn.interpreter.std.list;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class GetFunction implements NativeFunction {
    @Override
    public String getName() {
        return "list::get";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("List", "Int");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("list", "index");
    }

    @Override
    public String getReturnType() {
        return "Any";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return ((List<?>) arguments.getFirst()).get((int) arguments.getLast());
    }
}
