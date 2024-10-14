package yeet.eliax00789.fusyn.interpreter.std.func;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

@SuppressWarnings("unused")
public class GetFunction implements Function {
    @Override
    public String getName() {
        return "func::get";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str");
    }

    @Override
    public String getReturnType() {
        return "Func";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        Function function = interpreter.interpreterContext().getFunction((String) arguments.getFirst());
        if (function == null) {
            interpreter.errorReporter().error(argumentPositions.getFirst(), "Function not found");
            throw new Interpreter.InterpreterException();
        }
        return function;
    }
}
