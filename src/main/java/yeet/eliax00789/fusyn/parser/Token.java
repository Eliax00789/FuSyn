package yeet.eliax00789.fusyn.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Token(@NotNull TokenType type, @Nullable String value, int position) {
    @NotNull
    @Override
    public String toString() {
        String valueString = "null";
        if (this.value != null) {
            valueString = this.value.getClass().getSimpleName() + "{" + this.value + "}";
        }
        return "Token{type=" + this.type + ", value=" + valueString + ", position=" + this.position + '}';
    }
}
