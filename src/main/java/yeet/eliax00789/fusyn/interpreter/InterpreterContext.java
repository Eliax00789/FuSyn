package yeet.eliax00789.fusyn.interpreter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yeet.eliax00789.fusyn.interpreter.output.InterpreterOutput;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class InterpreterContext {
    public final InterpreterOutput output;
    private final Map<@NotNull String, @NotNull Object> globalStackFrame;
    private final Queue<@NotNull Map<@NotNull String, @NotNull Object>> variableStack;

    public InterpreterContext(InterpreterOutput output) {
        this.output = output;
        this.globalStackFrame = new HashMap<>();
        this.variableStack = Collections.asLifoQueue(new LinkedList<>());
        this.variableStack.add(this.globalStackFrame);
    }

    @Nullable
    public Object getVariable(@NotNull String name) {
        return this.variableStack.peek().getOrDefault(name, this.globalStackFrame.get(name));
    }

    public void setVariable(@NotNull String name, @NotNull Object value) {
        this.variableStack.peek().put(name, value);
    }

    public void pushStack() {
        this.variableStack.add(new HashMap<>());
    }

    public void popStack() {
        this.variableStack.remove();
    }
}
