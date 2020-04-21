package com.github.shootercheng.export.exception;

/**
 * @author James
 */
public class ParamBuildException extends RuntimeException {

    public ParamBuildException(String message) {
        super(message);
    }

    public ParamBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}
