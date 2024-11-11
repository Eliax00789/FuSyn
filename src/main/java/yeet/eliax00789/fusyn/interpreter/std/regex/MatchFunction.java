package yeet.eliax00789.fusyn.interpreter.std.regex;

import org.jetbrains.annotations.NotNull;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class MatchFunction implements NativeFunction {
    @Override
    public String getName() {
        return "regex::match";
    }

    @Override
    public List<String> getArgumentTypes() {
        return List.of("Str", "Regex");
    }

    @Override
    public List<String> getArgumentNames() {
        return List.of("value", "regex");
    }

    @Override
    public String getReturnType() {
        return "List";
    }

    @Override
    public Object execute(Interpreter interpreter, @NotNull List<?> arguments, int position, @NotNull List<Integer> argumentPositions) {
        Matcher matcher = ((Pattern) arguments.getLast()).matcher((String) arguments.getFirst());
        List<String> ret = new ArrayList<>();
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                ret.add(matcher.group(i));
            }
        }
        return ret;
    }
}
