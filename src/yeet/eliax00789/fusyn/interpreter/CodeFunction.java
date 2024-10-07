package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.parser.type.ASTNode;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

public class CodeFunction implements Function {
    private final String name;
    private final TypedListASTNode body;

    public CodeFunction(String name, TypedListASTNode body) {
        this.name = name;
        this.body = body;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Class<?>> getArgumentTypes() {
        return List.of();
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<Object> arguments, int position) {
        Object ret = null;
        for (ASTNode value : this.body.value()) {
            ret = value.accept(interpreter);
        }
        return ret;
    }
}
