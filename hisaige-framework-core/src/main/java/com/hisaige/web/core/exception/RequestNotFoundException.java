package com.hisaige.web.core.exception;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/13$ - 22:03$
 */
public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException() {
        super();
    }

    public RequestNotFoundException(String message) {
        super(message);
    }
}
