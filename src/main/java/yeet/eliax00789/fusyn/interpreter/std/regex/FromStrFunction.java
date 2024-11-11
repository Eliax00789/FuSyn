package yeet.eliax00789.fusyn.interpreter.std.regex;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class FromStrFunction implements NativeFunction {
    @Override
    public String getName() {
        return "regex::from-str";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value");
    }

    @Override
    public String getReturnType() {
        return "Regex";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        String argument = (String) arguments.getFirst();
        StringBuilder regex = new StringBuilder();
        int flags = 0;
        boolean isEscaped = false;
        boolean haveActualRegex = false;
        for (int i = 0; i < argument.length(); i++) {
            char c = argument.charAt(i);
            if (i == 0) {
                if (c != '/') {
                    break;
                }
            } else if (haveActualRegex) {
                switch (c) {
                    case 'i':
                        flags |= Pattern.CASE_INSENSITIVE;
                        break;
                    case 'm':
                        flags |= Pattern.MULTILINE;
                        break;
                    case 's':
                        flags |= Pattern.DOTALL;
                        break;
                    default:
                        interpreter.errorReporter().error(argumentPositions.getFirst(), "Invalid regex flag");
                        throw new Interpreter.InterpreterException();
                }
            } else if (c == '/') {
                haveActualRegex = true;
            } else {
                if (isEscaped) {
                    isEscaped = false;
                } else if (c == '\\') {
                    isEscaped = true;
                }
                regex.append(c);
            }
        }
        if (!haveActualRegex) {
            interpreter.errorReporter().error(argumentPositions.getFirst(), "Invalid regex");
            throw new Interpreter.InterpreterException();
        }
        return Pattern.compile(regex.toString(), flags);
    }
}
