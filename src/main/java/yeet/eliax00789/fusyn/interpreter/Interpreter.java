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
        return switch (typedListASTNode.type) {
            case null -> this.exec(typedListASTNode);
            case LEFT_PARENTHESIS -> {
                Object firstValue = typedListASTNode.getFirst().accept(this);
                if (!(firstValue instanceof String firstValueStr)) {
                    this.errorReporter.error(typedListASTNode.position(), "Exec invalid");
                    throw new InterpreterException();
                }
                Function function = this.interpreterContext.getFunction(firstValueStr);
                if (function == null) {
                    this.errorReporter.error(typedListASTNode.position(), "Exec function not found");
                    throw new InterpreterException();
                }
                int argumentsSize = typedListASTNode.size() - 1;
                List<Object> arguments = new ArrayList<>(argumentsSize);
                List<Integer> argumentPositions = new ArrayList<>(argumentsSize);
                for (int i = 0; i < argumentsSize; i++) {
                    ASTNode node = typedListASTNode.get(i + 1);
                    arguments.add(node.accept(this));
                    argumentPositions.add(node.position());
                }
                yield this.exec(function, arguments, typedListASTNode.position(), argumentPositions);
            }
            case LEFT_BRACE -> typedListASTNode;
            case LEFT_BRACKET -> {
                List<Object> values = new ArrayList<>();
                for (ASTNode value : typedListASTNode) {
                    values.add(value.accept(this));
                }
                yield values;
            }
            case LEFT_ANGLE_BRACKET -> {
                int size = typedListASTNode.size();
                if (size == 0 || size > 2) {
                    this.errorReporter.error(typedListASTNode.position(), "Var invalid");
                    throw new InterpreterException();
                }
                Object firstValue = typedListASTNode.getFirst().accept(this);
                if (!(firstValue instanceof String firstValueStr)) {
                    this.errorReporter.error(typedListASTNode.getFirst().position(), "Var name invalid");
                    throw new InterpreterException();
                }
                Object value;
                if (size == 1) {
                    value = this.interpreterContext.getVariable(firstValueStr);
                    if (value == null) {
                        this.errorReporter.error(typedListASTNode.getFirst().position(), "Variable not found");
                        throw new InterpreterException();
                    }
                } else {
                    value = typedListASTNode.getLast().accept(this);
                    this.interpreterContext.setVariable(firstValueStr, value);
                }
                yield value;
            }
            default -> throw new IllegalStateException("Unexpected");
        };
    }

    public Object exec(Function function, List<?> arguments, int position, List<Integer> argumentPositions) {
        int numArguments = arguments.size();
        List<String> argumentTypes = function.getArgumentTypes();

        if (argumentTypes.size() != numArguments) {
            this.errorReporter.error(position, "Invalid number of arguments " + arguments.size() + " expected " + argumentTypes.size());
            throw new InterpreterException();
        }

        for (int i = 0; i < numArguments; i++) {
            int argumentPosition = argumentPositions.get(i);
            String argumentType = argumentTypes.get(i);
            Class<?> argumentTypeClass = this.getClassFromName(argumentType, argumentPosition);
            assert argumentTypeClass != null;
            Object argument = arguments.get(i);
            if (!argumentTypeClass.isInstance(argument)) {
                this.errorReporter.error(argumentPosition, "Invalid argument type " + this.getNameFromClass(argument == null ? null : argument.getClass(), argumentPosition) + " expected " + argumentType);
                throw new InterpreterException();
            }
        }
        return function.execute(this, arguments, position, argumentPositions);
    }

    public Class<?> getClassFromName(String name, int position) {
        if (name.equals("String")) {
            return String.class;
        } else if (name.equals("List")) {
            return List.class;
        } else if (name.equals("Bool")) {
            return Boolean.class;
        } else if (name.equals("Function")) {
            return Function.class;
        } else if (name.equals("Any")) {
            return Object.class;
        } else if (name.equals("AST")) {
            return TypedListASTNode.class;
        } else if (name.equals("Null")) {
            return null;
        }
        this.errorReporter.error(position, "Unknown Type");
        throw new InterpreterException();
    }

    public String getNameFromClass(Class<?> aClass, int position) {
        if (aClass == String.class) {
            return "String";
        } else if (aClass == List.class) {
            return "List";
        } else if (aClass == Boolean.class) {
            return "Bool";
        } else if (aClass == Function.class) {
            return "Function";
        } else if (aClass == Object.class) {
            return "Any";
        } else if (aClass == TypedListASTNode.class) {
            return "TypedListASTNode";
        } else if (aClass == null) {
            return "Null";
        }
        this.errorReporter.error(position, "Unknown Type");
        throw new InterpreterException();
    }

    public Object exec(List<ASTNode> values) {
        Object ret = null;
        for (ASTNode value : values) {
            ret = value.accept(this);
        }
        return ret;
    }
}
