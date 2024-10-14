package yeet.eliax00789.fusyn.interpreter.std.list;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;

import java.util.List;

@SuppressWarnings("unused")
public class AddFunction implements Function {
    @Override
    public String getName() {
        return "list::add";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("List", "Any");
    }

    @Override
    public String getReturnType() {
        return "List";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        @SuppressWarnings("unchecked") List<Object> list = (List<Object>) arguments.getFirst();
        list.add(arguments.getLast());
        return list;
    }
}
