package yeet.eliax00789.fusyn.interpreter.std.bool;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

@SuppressWarnings("unused")
public class AndFunction implements Function {
    @Override
    public String getName() {
        return "bool::and";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Bool", "Bool");
    }

    @Override
    public String getReturnType() {
        return "Bool";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return (boolean) arguments.getFirst() && (boolean) arguments.getLast();
    }
}
