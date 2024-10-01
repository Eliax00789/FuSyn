package yeet.eliax00789.fusyn.error;

import java.io.File;

public interface ErrorReporter {
    void setFile(File file);
    void error(int position, String message);
}
