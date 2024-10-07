package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Function {
    String getName();
    List<Class<?>> getArgumentTypes();
    Object execute(Interpreter interpreter, @NotNull List<Object> arguments, int position);
}
