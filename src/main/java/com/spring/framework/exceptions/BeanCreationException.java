package com.spring.framework.exceptions;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-10 10:49
 **/
public class BeanCreationException extends IOCSmallException {
    public BeanCreationException() {
        super();
    }

    public BeanCreationException(String message) {
        super(message);
    }

    public BeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BeanCreationException(Throwable cause) {
        super(cause);
    }

    protected BeanCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
