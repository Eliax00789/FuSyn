package yeet.eliax00789.fusyn;

import yeet.eliax00789.fusyn.error.ConsoleErrorReporter;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.interpreter.Interpreter;
import yeet.eliax00789.fusyn.interpreter.InterpreterContext;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;
import yeet.eliax00789.fusyn.interpreter.output.ConsoleInterpreterOutput;
import yeet.eliax00789.fusyn.interpreter.output.InterpreterOutput;
import yeet.eliax00789.fusyn.parser.Parser;
import yeet.eliax00789.fusyn.parser.Tokenizer;
import yeet.eliax00789.fusyn.parser.type.TypedListASTNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            String filename = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getName();
            String whitespace = " ".repeat(filename.length() + 8);
            System.out.println(
                    "Usage: " + filename + "\n"
                            + whitespace + "-f <file>" + "\n"
                            + whitespace + "-i"
            );
        } else if (args.length == 1 && args[0].equals("-i")) {
            Main.runInteractive();
        } else if (args.length == 2 && args[0].equals("-f")) {
            Main.runFile(new File(args[1]));
        }
        System.exit(0);
    }

    private static void runInteractive() {
        ErrorReporter errorReporter = new ConsoleErrorReporter();

        InterpreterContext interpreterContext = Main.initInterpreterContext(new ConsoleInterpreterOutput());
        Interpreter interpreter = new Interpreter(errorReporter, interpreterContext);

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
        String source = Files.readString(file.toPath());

        ErrorReporter errorReporter = new ConsoleErrorReporter();
        errorReporter.setSource(source);

        Parser parser = new Parser(errorReporter, new Tokenizer(source));
        TypedListASTNode root = parser.parse();
        if (root == null) {
            System.exit(1);
        }

        InterpreterContext interpreterContext = Main.initInterpreterContext(new ConsoleInterpreterOutput());
        Interpreter interpreter = new Interpreter(errorReporter, interpreterContext);
        try {
            root.accept(interpreter);
        } catch (Interpreter.InterpreterException e) {
            System.exit(1);
        }
    }

    public static InterpreterContext initInterpreterContext(InterpreterOutput output) {
        InterpreterContext interpreterContext = new InterpreterContext(output);
        Set<Class<?>> functionClasses = new HashSet<>();
        Main.findAllClassesUsingClassLoader(functionClasses, "yeet.eliax00789.fusyn.interpreter.std");
        for (Class<?> aClass : functionClasses) {
            try {
                NativeFunction function = (NativeFunction) aClass.getDeclaredConstructor().newInstance();
                interpreterContext.setVariable(function.getName(), function);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        return interpreterContext;
    }

    public static void findAllClassesUsingClassLoader(Set<Class<?>> classes, String packageName) {
        try (
                InputStream stream = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replace(".", "/"))) ;
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".class")) {
                    classes.add(Class.forName(packageName + "." + line.substring(0, line.lastIndexOf('.'))));
                } else {
                    Main.findAllClassesUsingClassLoader(classes, packageName + "." + line);
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
