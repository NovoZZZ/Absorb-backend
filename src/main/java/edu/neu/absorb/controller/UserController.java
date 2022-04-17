package edu.neu.absorb.controller;

import edu.neu.absorb.dto.CommonResponse;
import edu.neu.absorb.dto.request.LoginRequest;
import edu.neu.absorb.exception.AuthException;
import edu.neu.absorb.exception.ExceptionEnum;
import edu.neu.absorb.pojo.User;
import edu.neu.absorb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author novo
 * @since 2022/4/15
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Get user info by user id
     *
     * @param userId user id
     * @param token  token
     * @return user info
     */
    @GetMapping("/info/{id}&{token}")
    public CommonResponse getUserInfo(@PathVariable("id") Integer userId, @PathVariable("token") String token) {
        // validate token
        User userInfo = userService.getUserByToken(token);
        if (userInfo == null || !userInfo.getUserId().equals(userId)) {
            // no authorization
            throw new AuthException(ExceptionEnum.AUTH_EXCEPTION);
        }
        return CommonResponse.success(userService.getUserByUserId(userId));
    }

    /**
     * login request
     *
     * @param loginRequest login request
     * @return login response
     */
    @PostMapping("/login")
    public CommonResponse login(@RequestBody LoginRequest loginRequest) {
        return CommonResponse.success(userService.login(loginRequest));
    }
}
