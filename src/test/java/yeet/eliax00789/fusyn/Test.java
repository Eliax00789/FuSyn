package yeet.eliax00789.fusyn;

import yeet.eliax00789.fusyn.error.ConsoleErrorReporter;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.InterpreterContext;
import yeet.eliax00789.fusyn.interpreter.output.BufferingInterpreterOutput;
import yeet.eliax00789.fusyn.parser.Parser;
import yeet.eliax00789.fusyn.parser.Tokenizer;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Test {
    private static final String SEPARATOR = "------------------------------";

    public static void main(String[] args) throws IOException {
        ErrorReporter errorReporter = new ConsoleErrorReporter();

        BufferingInterpreterOutput interpreterOutput = new BufferingInterpreterOutput();
        InterpreterContext interpreterContext = Main.initInterpreterContext(interpreterOutput);
        Interpreter interpreter = new Interpreter(errorReporter, interpreterContext);

        Map<String, String> files = new HashMap<>();
        readTestFilesFromResources(files, Thread.currentThread().getContextClassLoader(), "std");

        int numTests = 0;
        int numCorrectTests = 0;
        for (Map.Entry<String, String> entry : files.entrySet()) {
            String[] parts = entry.getValue().split(Test.SEPARATOR + '\n');

            errorReporter.setSource(parts[1]);
            Parser parser = new Parser(errorReporter, new Tokenizer(parts[1]));
            TypedListASTNode root = parser.parse();
            if (root == null) {
                System.exit(1);
            }
            try {
                root.accept(interpreter);
            } catch (Interpreter.InterpreterException e) {
                System.out.println(interpreterOutput.output);
                System.exit(1);
            }

            numTests++;

            boolean isCorrect = interpreterOutput.output.equals(parts.length == 2 ? "" : parts[2]);
            if (isCorrect) {
                numCorrectTests++;
            } else {
                System.out.println(
                        entry.getKey() + ":\n" +
                                "Expected:\n" +
                                parts[2] + "\n" +
                                "Got\n" +
                                interpreterOutput.output
                );
            }
            interpreterOutput.output = "";
        }

        System.out.println("Testing complete (" + numCorrectTests + "/" + numTests + ")");
    }

    private static void readTestFilesFromResources(Map<String, String> result, ClassLoader classLoader, String directory) throws IOException {
        try (
                InputStream inputStream = Objects.requireNonNull(classLoader.getResourceAsStream(directory));
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String tmp = reader.readLine();
            if (tmp.equals(Test.SEPARATOR)) {
                StringBuilder content = new StringBuilder();
                do {
                    content.append(tmp).append('\n');
                } while ((tmp = reader.readLine()) != null);
                result.put(directory, content.toString());
            } else {
                do {
                    Test.readTestFilesFromResources(result, classLoader, directory + "/" + tmp);
                } while ((tmp = reader.readLine()) != null);
            }
        }
    }
}
