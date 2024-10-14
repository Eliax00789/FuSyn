package yeet.eliax00789.fusyn.interpreter.std.cf;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class IfElseFunction implements Function {
    @Override
    public String getName() {
        return "cf::if-else";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Bool", "AST", "AST");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        return interpreter.exec(((TypedListASTNode) arguments.get((boolean) arguments.getFirst() ? 1 : 2)));
    }
}
