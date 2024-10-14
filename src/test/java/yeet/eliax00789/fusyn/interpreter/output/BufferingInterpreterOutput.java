package yeet.eliax00789.fusyn.interpreter.output;

public class BufferingInterpreterOutput implements InterpreterOutput {
    public String output = "";

    @Override
    public void println(String s) {
        this.output += s + "\n";
    }
}
