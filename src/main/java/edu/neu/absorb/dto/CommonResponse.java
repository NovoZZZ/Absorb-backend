package edu.neu.absorb.dto;

import edu.neu.absorb.exception.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author novo
 * @since 2022/4/15
 */
@Data
@AllArgsConstructor
public class CommonResponse {
    private int code;
    private String message;
    private Object data;

    /**
     * build error response
     * @param exception error
     * @return error response
     */
    public static CommonResponse error(ExceptionEnum exception) {
        return new CommonResponse(exception.getCode(), exception.getMessage(), null);
    }

    /**
     * build success response
     * @param data data
     * @return success response
     */
    public static CommonResponse success(Object data) {
        return new CommonResponse(200, "", data);
    }
}
