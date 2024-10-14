package yeet.eliax00789.fusyn.interpreter.output;

public class ConsoleInterpreterOutput implements InterpreterOutput {
    @Override
    public void println(String s) {
        System.out.println(s);
    }
}
