package yeet.eliax00789.fusyn.interpreter.std.func;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CallFunction implements Function {
    @Override
    public String getName() {
        return "func::call";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Func", "List");
    }

    @Override
    public String getReturnType() {
        return "Any";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        Function function = (Function) arguments.getFirst();
        List<?> fArguments = (List<?>) arguments.getLast();
        List<Integer> fArgumentPositions = new ArrayList<>(fArguments.size());
        for (int i = 0; i < fArguments.size(); i++) {
            fArgumentPositions.add(argumentPositions.getLast());
        }
        return interpreter.exec(function, fArguments, argumentPositions.getFirst(), fArgumentPositions);
    }
}
