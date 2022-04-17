package edu.neu.absorb.exception;

/**
 * @author novo
 * @since 2022/4/15
 */
public class CommonException extends RuntimeException {
    ExceptionEnum exceptionEnum;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public CommonException(ExceptionEnum exceptionEnum, String message) {
        super(message);
        this.exceptionEnum = exceptionEnum;
    }
}