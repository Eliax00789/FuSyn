package yeet.eliax00789.fusyn.interpreter.std.cf;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class WhileFunction implements Function {
    @Override
    public String getName() {
        return "cf::while";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("AST", "AST");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        TypedListASTNode conditionTypedListASTNode = (TypedListASTNode) arguments.getFirst();
        TypedListASTNode bodyTypedListASTNode = (TypedListASTNode) arguments.getLast();
        Object ret = null;
        while (true) {
            Object condition = interpreter.exec(conditionTypedListASTNode);
            if (!(condition instanceof Boolean conditionBool)) {
                interpreter.errorReporter().error(argumentPositions.getFirst(), "Result is not a boolean");
                throw new Interpreter.InterpreterException();
            }
            if (!conditionBool) {
                break;
            }
            ret = interpreter.exec(bodyTypedListASTNode);
        }
        return ret;
    }
}
