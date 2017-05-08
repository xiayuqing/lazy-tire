package org.jason.lazytire.compile;

/**
 * Created by Jason.Xia on 17/5/8.
 */
public class UnableException extends RuntimeException {
    public UnableException() {
        super();
    }

    public UnableException(String message) {
        super(message);
    }

    public UnableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableException(Throwable cause) {
        super(cause);
    }

    protected UnableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
