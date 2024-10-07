package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InterpreterContext {
    public final List<@NotNull Function> functions;

    public InterpreterContext() {
        this.functions = new ArrayList<>();
    }
}
