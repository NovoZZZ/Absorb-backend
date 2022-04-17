package edu.neu.absorb.exception;

/**
 * @author novo
 * @since 2022/4/15
 */
public class AuthException extends RuntimeException {
    ExceptionEnum exceptionEnum;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public AuthException(ExceptionEnum exceptionEnum, String message) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }
}
