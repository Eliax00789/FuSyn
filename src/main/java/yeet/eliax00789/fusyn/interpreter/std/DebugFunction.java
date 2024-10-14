package yeet.eliax00789.fusyn.interpreter.std;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

@SuppressWarnings("unused")
public class DebugFunction implements Function {
    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Any");
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
