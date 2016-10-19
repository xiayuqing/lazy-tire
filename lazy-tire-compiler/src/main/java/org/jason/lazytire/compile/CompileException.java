package org.jason.lazytire.compile;

/**
 * Created by Jason.Xia on 16/10/18.
 */
public class CompileException extends RuntimeException {
    public CompileException() {
        super();
    }

    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    protected CompileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
