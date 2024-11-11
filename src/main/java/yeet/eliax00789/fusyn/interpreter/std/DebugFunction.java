package yeet.eliax00789.fusyn.interpreter.std;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;

@SuppressWarnings("unused")
public class DebugFunction implements NativeFunction {
    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Any");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value");
    }

    @Override
    public String getReturnType() {
        return "Any";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        Object firstArgument = arguments.getFirst();
        interpreter.interpreterContext().output.println(firstArgument.toString());
        return firstArgument;
    }
}
