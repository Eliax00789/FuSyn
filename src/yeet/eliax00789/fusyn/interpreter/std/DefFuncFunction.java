package yeet.eliax00789.fusyn.interpreter.std;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.CodeFunction;
import yeet.eliax00789.fusyn.interpreter.Function;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

public class DefFuncFunction implements Function {
    @Override
    public String getName() {
        return "def-func";
    }

    @Override
    public List<Class<?>> getArgumentTypes() {
        return List.of(String.class, TypedListASTNode.class);
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<Object> arguments, int position) {
        String name = (String) arguments.getFirst();
        TypedListASTNode body = (TypedListASTNode) arguments.get(1);
        return interpreter.interpreterContext().functions.add(new CodeFunction(name, body));
    }
}
