package com.spring.framework.exceptions;

/**
 * @program: IOCsmall
 * @author: xuan
 * @create: 2019-05-10 10:50
 **/
public class ConfigParseException extends IOCSmallException {
    public ConfigParseException() {
        super();
    }

    public ConfigParseException(String message) {
        super(message);
    }

    public ConfigParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigParseException(Throwable cause) {
        super(cause);
    }

    protected ConfigParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
