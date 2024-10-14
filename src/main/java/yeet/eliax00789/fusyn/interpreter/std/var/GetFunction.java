package yeet.eliax00789.fusyn.interpreter.std.var;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

@SuppressWarnings("unused")
public class GetFunction implements Function {
    @Override
    public String getName() {
        return "var::get";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("String");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        Object value = interpreter.interpreterContext().getVariable((String) arguments.getFirst());
        if (value == null) {
            interpreter.errorReporter().error(argumentPositions.getFirst(), "Variable not found");
            throw new Interpreter.InterpreterException();
        }
        return value;
    }
}
