package yeet.eliax00789.fusyn.error;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class ConsoleErrorReporter implements ErrorReporter {
    private File file = null;

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void error(int position, String message) {
        Queue<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(this.file))) {
            lines = new LinkedList<>(reader.lines().toList());
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unexpected");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String line = lines.remove();
        while (position > line.length()) {
            position -= line.length() + 1;
            line = lines.remove();
        }

        System.err.println(line);
        System.err.println(" ".repeat(position) + "^");
        System.err.println(message);
    }
}
