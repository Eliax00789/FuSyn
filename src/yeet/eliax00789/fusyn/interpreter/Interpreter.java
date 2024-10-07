package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.parser.type.ASTNode;
import yeet.eliax00789.fusyn.parser.type.StringASTNode;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.util.ArrayList;
import java.util.List;

public record Interpreter(ErrorReporter errorReporter, InterpreterContext interpreterContext) implements ASTNode.Visitor<Object> {
    public static class InterpreterException extends RuntimeException {
    }

    public Interpreter(@NotNull ErrorReporter errorReporter, @NotNull InterpreterContext interpreterContext) {
        this.errorReporter = errorReporter;
        this.interpreterContext = interpreterContext;
    }

    @Override
    public Object visit(StringASTNode stringASTNode) {
        return stringASTNode.value();
    }

    @Override
    public Object visit(TypedListASTNode typedListASTNode) {
        switch (typedListASTNode.type()) {
            case LEFT_PARENTHESIS: {
                List<ASTNode> typedListASTNodeValue = typedListASTNode.value();
                Object firstValue = typedListASTNodeValue.getFirst().accept(this);
                if (firstValue instanceof String) {
                    for (Function function : interpreterContext.functions) {
                        if (function.getName().equals(firstValue)) {
                            List<Class<?>> argumentTypes = function.getArgumentTypes();
                            if (argumentTypes.size() != typedListASTNodeValue.size() - 1) {
                                this.errorReporter.error(typedListASTNode.position(), "Invalid number of arguments");
                                throw new InterpreterException();
                            }
                            List<Object> arguments = new ArrayList<>();
                            for (int i = 1; i < typedListASTNodeValue.size(); i++) {
                                Class<?> argumentType = argumentTypes.get(i - 1);
                                Object argument = typedListASTNodeValue.get(i).accept(this);
                                if (!argumentType.isInstance(argument)) {
                                    int position = switch (typedListASTNodeValue.get(i)) {
                                        case StringASTNode s -> s.position();
                                        case TypedListASTNode t -> t.position();
                                        default -> throw new IllegalStateException("Unexpected");
                                    };
                                    this.errorReporter.error(position, "Invalid argument type " + argument.getClass().getSimpleName() + " expected " + argumentType.getSimpleName());
                                    throw new InterpreterException();
                                }
                                arguments.add(argument);
                            }
                            return function.execute(this, arguments, typedListASTNode.position());
                        }
                    }
                    this.errorReporter.error(typedListASTNode.position(), "Exec function not found");
                } else {
                    this.errorReporter.error(typedListASTNode.position(), "Exec invalid");
                }
                throw new InterpreterException();
            }
            case LEFT_BRACE: {
                return typedListASTNode;
            }
            case null:
            case LEFT_BRACKET: {
                List<Object> values = new ArrayList<>();
                for (ASTNode value : typedListASTNode.value()) {
                    values.add(value.accept(this));
                }
                return values;
            }
            default:
                throw new IllegalStateException("Unexpected");
        }
    }
}
