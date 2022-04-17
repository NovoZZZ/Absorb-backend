package edu.neu.absorb.exception;

import edu.neu.absorb.dto.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author novo
 * @since 2022/4/15
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * handle unspecified exception
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public CommonResponse handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();

        // convert error message
        String mainExceptionContent = getMainExceptionContent(e);
        log.error(mainExceptionContent);

        // return server error
        return CommonResponse.error(ExceptionEnum.SERVER_EXCEPTION);
    }

    /**
     * handle authorization exception
     */
    @ResponseBody
    @ExceptionHandler(AuthException.class)
    public CommonResponse handleAuthException(HttpServletRequest request, HttpServletResponse response, AuthException e) throws IOException {
        e.printStackTrace();

        // return response
        CommonResponse exceptionResponse;

        // convert error message
        String mainExceptionContent = getMainExceptionContent(e);
        log.error(mainExceptionContent);

        // generate error response
        if (e.exceptionEnum != null) {
            exceptionResponse = CommonResponse.error(e.exceptionEnum);
        } else {
            exceptionResponse = CommonResponse.error(ExceptionEnum.SERVER_EXCEPTION);
        }
        return exceptionResponse;
    }

    /**
     * handle common exception
     */
    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public CommonResponse handleCommonException(HttpServletRequest request, HttpServletResponse response, AuthException e) throws IOException {
        e.printStackTrace();

        // return response
        CommonResponse exceptionResponse;

        // convert error message
        String mainExceptionContent = getMainExceptionContent(e);
        log.error(mainExceptionContent);

        // generate error response
        if (e.exceptionEnum != null) {
            exceptionResponse = CommonResponse.error(e.exceptionEnum);
        } else {
            exceptionResponse = CommonResponse.error(ExceptionEnum.SERVER_EXCEPTION);
        }
        return exceptionResponse;
    }

    /**
     * convert exception info to message
     *
     * @param e exception
     * @return converted message
     */
    private String getMainExceptionContent(Exception e) {
        StringBuilder errorMessage = new StringBuilder("\n"
                + "-----Error------" + "\n"
                + e.getClass().getName() + "\n"
                + e.getMessage() + "\n"
                + e.toString() + "\n");
        for (int i = 0; i < e.getStackTrace().length && i < 5; i++) {
            errorMessage.append(e.getStackTrace()[i].toString()).append("\n");
        }
        return errorMessage.toString();
    }
}
