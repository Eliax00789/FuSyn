package yeet.eliax00789.fusyn.interpreter.std;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

public class DebugFunction implements Function {
    @Override
    public String getName() {
        return "debug";
    }

    @Override
    public List<Class<?>> getArgumentTypes() {
        return List.of(List.class);
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<Object> arguments, int position) {
        System.out.println(arguments);
        return null;
    }
}
