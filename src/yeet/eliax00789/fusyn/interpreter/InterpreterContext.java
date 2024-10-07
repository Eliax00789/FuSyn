package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class InterpreterContext {
    private final Map<@NotNull String, @NotNull Function> functions;

    public InterpreterContext() {
        this.functions = new HashMap<>();
    }

    @Nullable
    public Function getFunction(@NotNull String name) {
        return this.functions.get(name);
    }

    public void addFunction(@NotNull Function function) {
        this.functions.put(function.getName(), function);
    }
}
