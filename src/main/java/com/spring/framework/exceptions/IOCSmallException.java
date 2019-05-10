package com.spring.framework.exceptions;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-10 10:47
 **/
public class IOCSmallException extends RuntimeException {
    public IOCSmallException() {
        super();
    }

    public IOCSmallException(String message) {
        super(message);
    }

    public IOCSmallException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOCSmallException(Throwable cause) {
        super(cause);
    }

    protected IOCSmallException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
