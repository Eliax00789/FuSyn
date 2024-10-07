package yeet.eliax00789.fusyn.error;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class ConsoleErrorReporter implements ErrorReporter {
    private String source = null;

    @Override
    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public void error(int position, String message) {
        if (source != null) {
            Queue<String> lines = new ArrayDeque<>(List.of(source.split("\r?\n")));

            String line = lines.remove();
            while (position > line.length()) {
                position -= line.length() + 1;
                line = lines.remove();
            }

            System.err.println(line);
            System.err.println(" ".repeat(position) + "^");
        }
        System.err.println(message);
    }
}
