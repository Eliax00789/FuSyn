package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Function {
    String getName();
    List<String> getArgumentTypes();
    String getReturnType();
    Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions);
}
