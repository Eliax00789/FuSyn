package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.parser.type.ASTNode;
import yeet.eliax00789.fusyn.parser.type.StringASTNode;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public record Interpreter(ErrorReporter errorReporter, InterpreterContext interpreterContext) implements ASTNode.Visitor<Object> {
    public static class InterpreterException extends RuntimeException {
    }

    public Interpreter(@NotNull ErrorReporter errorReporter, @NotNull InterpreterContext interpreterContext) {
        this.errorReporter = errorReporter;
        this.interpreterContext = interpreterContext;
    }

    @Override
    public Object visit(StringASTNode stringASTNode) {
        String value = stringASTNode.value();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException _) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException _) {
                return value;
            }
        }
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
                Object variable = this.interpreterContext.getVariable(firstValueStr);
                if (variable == null) {
                    this.errorReporter.error(typedListASTNode.position(), "Exec function not found");
                    throw new InterpreterException();
                }
                if (!(variable instanceof Function function)) {
                    this.errorReporter.error(typedListASTNode.position(), "Cant execute " + this.getNameFromClass(variable.getClass(), typedListASTNode.position()));
                    throw new InterpreterException();
                }
                int argumentsSize = typedListASTNode.size() - 1;
                List<ASTNode> arguments = new ArrayList<>(argumentsSize);
                List<Integer> argumentPositions = new ArrayList<>(argumentsSize);
                for (int i = 0; i < argumentsSize; i++) {
                    ASTNode node = typedListASTNode.get(i + 1);
                    arguments.add(node);
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

    public Object exec(Function function, List<ASTNode> arguments, int position, List<Integer> argumentPositions) {
        int numArguments = arguments.size();
        List<String> argumentTypes = function.getArgumentTypes();

        if (argumentTypes.size() != numArguments) {
            this.errorReporter.error(position, "Invalid number of arguments " + arguments.size() + " expected " + argumentTypes.size());
            throw new InterpreterException();
        }

        List<Object> argumentValues = new ArrayList<>(numArguments);

        for (int i = 0; i < numArguments; i++) {
            int argumentPosition = argumentPositions.get(i);
            String argumentType = argumentTypes.get(i);
            Class<?> argumentTypeClass = this.getClassFromName(argumentType, argumentPosition);
            ASTNode argument = arguments.get(i);
            Object argumentValue = argumentType.equals("AST") ? argument : argument.accept(this);
            if (!argumentType.equals("Any")) {
                if (!(argumentTypeClass == null ? argumentValue == null : argumentTypeClass.isInstance(argumentValue))) {
                    this.errorReporter.error(argumentPosition, "Invalid argument type " + this.getNameFromClass(argumentValue == null ? null : argumentValue.getClass(), argumentPosition) + " expected " + argumentType);
                    throw new InterpreterException();
                }
            }
            argumentValues.add(argumentValue);
        }

        Object ret = function.execute(this, argumentValues, position, argumentPositions);
        String returnType = function.getReturnType();
        if (!returnType.equals("Any")) {
            Class<?> returnTypeClass = this.getClassFromName(returnType, position);
            if (!(returnTypeClass == null ? ret == null : returnTypeClass.isInstance(ret))) {
                this.errorReporter().error(position, "Invalid return type " + this.getNameFromClass(ret == null ? null : ret.getClass(), position) + " expected " + returnType);
                throw new InterpreterException();
            }
        }
        return ret;
    }

    public Class<?> getClassFromName(String name, int position) {
        return switch (name) {
            case "Str" -> String.class;
            case "List" -> List.class;
            case "Iterable" -> Iterable.class;
            case "Bool" -> Boolean.class;
            case "Int" -> Integer.class;
            case "Func" -> Function.class;
            case "Any" -> Object.class;
            case "AST" -> TypedListASTNode.class;
            case "File" -> File.class;
            case "Path" -> Path.class;
            case "Regex" -> Pattern.class;
            case "Null" -> null;
            default -> {
                this.errorReporter.error(position, "Unknown Type: " + name);
                throw new InterpreterException();
            }
        };
    }

    public String getNameFromClass(Class<?> aClass, int position) {
        if (aClass == String.class) {
            return "Str";
        } else if (aClass == List.class) {
            return "List";
        } else if (aClass == Iterable.class) {
            return "Iterable";
        } else if (aClass == Boolean.class) {
            return "Bool";
        } else if (aClass == Integer.class) {
            return "Int";
        } else if (aClass == Function.class) {
            return "Func";
        } else if (aClass == Object.class) {
            return "Any";
        } else if (aClass == TypedListASTNode.class) {
            return "AST";
        } else if (aClass == File.class) {
            return "File";
        } else if (aClass == Path.class) {
            return "Path";
        } else if (aClass == Pattern.class) {
            return "Regex";
        } else if (aClass == null) {
            return "Null";
        }
        this.errorReporter.error(position, "Unknown Type: " + aClass);
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
