package yeet.eliax00789.fusyn;

import yeet.eliax00789.fusyn.interpreter.InterpreterContext;
import yeet.eliax00789.fusyn.interpreter.NativeFunction;
import yeet.eliax00789.fusyn.interpreter.output.ConsoleInterpreterOutput;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FunctionsDocGenerator {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Map<String, Map<String, String>> docs = new HashMap<>();

        InterpreterContext interpreterContext = Main.initInterpreterContext(new ConsoleInterpreterOutput());
        Field globalStackFrameField = interpreterContext.getClass().getDeclaredField("globalStackFrame");
        globalStackFrameField.setAccessible(true);
        //noinspection unchecked
        Map<String, Object> globalStackFrame = (Map<String, Object>) globalStackFrameField.get(interpreterContext);
        for (Object variable : globalStackFrame.values()) {
            if (!(variable instanceof NativeFunction function)) {
                continue;
            }

            String[] functionNameParts = function.getName().split("::");
            String functionNamespace = functionNameParts.length == 1 ? "" : functionNameParts[0];

            List<String> argumentTypes = function.getArgumentTypes();
            List<String> argumentNames = function.getArgumentNames();
            StringBuilder functionCall = new StringBuilder();
            if (function.getClass().isAnnotationPresent(Deprecated.class)) {
                functionCall.append("DEPRECATED | ");
            }
            functionCall.append("(").append(function.getName());
            for (int i = 0; i < argumentTypes.size(); i++) {
                String argumentType = argumentTypes.get(i);
                String argumentName = argumentNames.get(i);
                functionCall.append(" [").append(argumentType).append(" ").append(argumentName).append("]");
            }
            functionCall.append(") ").append(function.getReturnType());

            docs.putIfAbsent(functionNamespace, new HashMap<>());
            docs.get(functionNamespace).put(function.getName(), functionCall.toString());
        }

        for (Iterator<Map.Entry<String, Map<String, String>>> it = docs.entrySet().stream().sorted(Map.Entry.comparingByKey()).iterator(); it.hasNext(); ) {
            Map.Entry<String, Map<String, String>> docsEntry = it.next();
            System.out.println("- " + docsEntry.getKey());
            for (Iterator<Map.Entry<String, String>> iter = docsEntry.getValue().entrySet().stream().sorted(Map.Entry.comparingByKey()).iterator(); iter.hasNext(); ) {
                Map.Entry<String, String> doc = iter.next();
                System.out.println("  - `" + doc.getValue() + "`");
            }
        }
    }
}
