package yeet.eliax00789.fusyn;

import org.jetbrains.annotations.Nullable;
import yeet.eliax00789.fusyn.error.ConsoleErrorReporter;
import yeet.eliax00789.fusyn.error.ErrorReporter;
import yeet.eliax00789.fusyn.parser.DebugUnionTypeVisitor;
import yeet.eliax00789.fusyn.parser.Parser;
import yeet.eliax00789.fusyn.parser.Tokenizer;
import yeet.eliax00789.fusyn.parser.type.TypedListContainer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("code.fsy");
        ErrorReporter errorReporter = new ConsoleErrorReporter();

        TypedListContainer root = Main.parseFile(errorReporter, file);
        if (root == null) {
            return;
        }
        System.out.println(root.accept(new DebugUnionTypeVisitor()));
    }

    @Nullable
    private static TypedListContainer parseFile(ErrorReporter errorReporter, File file) throws IOException {
        String source;
        try (FileInputStream reader = new FileInputStream(file)) {
            source = new String(reader.readAllBytes());
        }

        errorReporter.setFile(file);

        Parser parser = new Parser(errorReporter, new Tokenizer(source));
        return parser.parse();
    }
}
