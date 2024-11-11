package yeet.eliax00789.fusyn.interpreter.std.func;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.CodeFunction;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class CreateFunction implements NativeFunction {
    @Override
    public String getName() {
        return "func::create";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("List", "Str", "AST");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("args", "return_type", "code");
    }

    @Override
    public String getReturnType() {
        return "Func";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        List<String> fArgumentTypes = new ArrayList<>();
        List<String> fArgumentNames = new ArrayList<>();
        for (Object argument : (List<?>) arguments.getFirst()) {
            if (!(argument instanceof List<?> argumentList)
                    || argumentList.size() != 2
                    || !(argumentList.getFirst() instanceof String argumentType)
                    || !(argumentList.getLast() instanceof String argumentName)) {
                interpreter.errorReporter().error(argumentPositions.getFirst(), "Invalid arguments");
                throw new Interpreter.InterpreterException();
            }
            fArgumentTypes.add(argumentType);
            fArgumentNames.add(argumentName);
        }
        String fReturn = (String) arguments.get(1);
        TypedListASTNode fBody = (TypedListASTNode) arguments.getLast();
        return new CodeFunction(fArgumentTypes, fArgumentNames, fReturn, fBody);
    }
}
