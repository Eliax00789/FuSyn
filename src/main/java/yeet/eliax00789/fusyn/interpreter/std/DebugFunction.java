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
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        interpreter.interpreterContext().output.println(arguments.getFirst().toString());
        return null;
    }
}
