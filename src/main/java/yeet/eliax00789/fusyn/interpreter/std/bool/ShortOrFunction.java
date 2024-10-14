package yeet.eliax00789.fusyn.interpreter.std.bool;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class ShortOrFunction implements Function {
    @Override
    public String getName() {
        return "bool::short-or";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Bool", "AST");
    }

    @Override
    public String getReturnType() {
        return "Bool";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        if ((boolean) arguments.getFirst()) {
            return true;
        }
        Object rhsValue = interpreter.exec((TypedListASTNode) arguments.getLast());
        if (!(rhsValue instanceof Boolean rhsValueBool)) {
            interpreter.errorReporter().error(argumentPositions.getLast(), "Result is not a boolean");
            throw new Interpreter.InterpreterException();
        }
        return rhsValueBool;
    }
}
