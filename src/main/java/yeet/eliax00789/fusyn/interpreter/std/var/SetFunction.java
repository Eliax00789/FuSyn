package yeet.eliax00789.fusyn.interpreter.std.var;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

@SuppressWarnings("unused")
public class SetFunction implements Function {
    @Override
    public String getName() {
        return "var::set";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("String", "Any");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        interpreter.interpreterContext().setVariable((String) arguments.getFirst(), arguments.getLast());
        return arguments.getLast();
    }
}
