package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.List;

public class CodeFunction implements Function {
    private final String name;
    private final List<String> argumentTypes;
    private final List<String> argumentNames;
    private final String returnType;
    private final TypedListASTNode body;

    public CodeFunction(String name, List<String> argumentTypes, List<String> argumentNames, String returnType, TypedListASTNode body) {
        this.name = name;
        this.argumentTypes = argumentTypes;
        this.argumentNames = argumentNames;
        this.returnType = returnType;
        this.body = body;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getArgumentTypes() {
        return this.argumentTypes;
    }

    @Override
    public String getReturnType() {
        return this.returnType;
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        interpreter.interpreterContext().pushStack();
        for (int i = 0; i < arguments.size(); i++) {
            interpreter.interpreterContext().setVariable(this.argumentNames.get(i), arguments.get(i));
        }
        Object ret = interpreter.exec(this.body);
        interpreter.interpreterContext().popStack();
        return ret;
    }
}
