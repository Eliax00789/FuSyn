package yeet.eliax00789.fusyn;

import yeet.eliax00789.fusyn.error.ConsoleErrorReporter;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.InterpreterContext;
import yeet.eliax00789.fusyn.interpreter.std.DebugFunction;
import yeet.eliax00789.fusyn.interpreter.std.DefFuncFunction;
import yeet.eliax00789.fusyn.parser.Parser;
import yeet.eliax00789.fusyn.parser.Tokenizer;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            String filename = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getName();
            System.out.println("Usage: " + filename);
            String whitespace = " ".repeat(filename.length() + 8);
            System.out.println(whitespace + "-f <file>");
            System.out.println(whitespace + "-i");
        } else if (args.length == 1 && args[0].equals("-i")) {
            Main.runInteractive();
        } else if (args.length == 2 && args[0].equals("-f")) {
            Main.runFile(new File(args[1]));
        }
        System.exit(0);
    }

    private static void runInteractive() {
        ErrorReporter errorReporter = new ConsoleErrorReporter();
        Interpreter interpreter = new Interpreter(errorReporter, Main.initInterpreterContext());

        while (true) {
            System.out.print("Source: ");
            String source = System.console().readLine();
            errorReporter.setSource(source);
            Parser parser = new Parser(errorReporter, new Tokenizer(source));
            TypedListASTNode root = parser.parse();
            if (root == null) {
                System.exit(1);
            }
            try {
                root.accept(interpreter);
            } catch (Interpreter.InterpreterException e) {
                System.exit(1);
            }
            System.out.println();
        }
    }

    private static void runFile(File file) throws IOException {
        String source;
        try (FileInputStream reader = new FileInputStream(file)) {
            source = new String(reader.readAllBytes());
        }

        ErrorReporter errorReporter = new ConsoleErrorReporter();
        errorReporter.setSource(source);

        Parser parser = new Parser(errorReporter, new Tokenizer(source));
        TypedListASTNode root = parser.parse();
        if (root == null) {
            System.exit(1);
        }

        Interpreter interpreter = new Interpreter(errorReporter, Main.initInterpreterContext());
        try {
            root.accept(interpreter);
        } catch (Interpreter.InterpreterException e) {
            System.exit(1);
        }
    }

    private static InterpreterContext initInterpreterContext() {
        InterpreterContext interpreterContext = new InterpreterContext();
        interpreterContext.functions.add(new DebugFunction());
        interpreterContext.functions.add(new DefFuncFunction());
        return interpreterContext;
    }
}
