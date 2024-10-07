package yeet.eliax00789.fusyn.error;

public interface ErrorReporter {
    void setSource(String source);
    void error(int position, String message);
}
