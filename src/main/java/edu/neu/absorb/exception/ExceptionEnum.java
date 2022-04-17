package edu.neu.absorb.exception;

import lombok.Getter;

/**
 * exception enum
 *
 * @author novo
 * @since 2022/4/15
 */
@Getter
public enum ExceptionEnum {
    AUTH_EXCEPTION(403, "Forbidden"),
    SERVER_EXCEPTION(500, "Server error"),
    UNAUTHORIZED_EXCEPTION(401, "Unauthorized, please login"),
    WRONG_PASSWORD_EXCEPTION(200, "Wrong password"),
    LOGIN_FAILED_EXCEPTION(200, "Login failed"),
    ILLEGAL_ARGUMENT_EXCEPTION(400, "Illegal arguments"),
    USERNAME_EXIST_EXCEPTION(400, "Username already exists");

    private final Integer code;

    private final String message;

    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
