package yeet.eliax00789.fusyn.interpreter.std.func;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.CodeFunction;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SetFunction implements Function {
    @Override
    public String getName() {
        return "func::set";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("String", "List", "String", "AST");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        String name = (String) arguments.getFirst();
        List<String> argumentTypes = new ArrayList<>();
        List<String> argumentNames = new ArrayList<>();
        for (Object argument : (List<?>) arguments.get(1)) {
            if (!(argument instanceof List<?> argumentList)
                    || argumentList.size() != 2
                    || !(argumentList.getFirst() instanceof String argumentType)
                    || !(argumentList.getLast() instanceof String argumentName)) {
                interpreter.errorReporter().error(argumentPositions.get(1), "Invalid arguments");
                throw new Interpreter.InterpreterException();
            }
            argumentTypes.add(argumentType);
            argumentNames.add(argumentName);
        }
        String fReturn = (String) arguments.get(2);
        TypedListASTNode body = (TypedListASTNode) arguments.getLast();
        interpreter.interpreterContext().setFunction(new CodeFunction(name, argumentTypes, argumentNames, fReturn, body));
        return null;
    }
}
