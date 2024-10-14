package yeet.eliax00789.fusyn.interpreter.std.list;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

@SuppressWarnings("unused")
public class ForeachFunction implements Function {
    @Override
    public String getName() {
        return "list::foreach";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("List", "String", "AST");
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        String varName = (String) arguments.get(1);
        TypedListASTNode ast = (TypedListASTNode) arguments.getLast();
        Object ret = null;
        for (Object v : (List<?>) arguments.getFirst()) {
            interpreter.interpreterContext().setVariable(varName, v);
            ret = interpreter.exec(ast);
        }
        return ret;
    }
}
