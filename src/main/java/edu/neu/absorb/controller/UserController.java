package edu.neu.absorb.controller;

import edu.neu.absorb.dto.CommonResponse;
import edu.neu.absorb.dto.request.ChangePasswordRequest;
import edu.neu.absorb.dto.request.LoginRequest;
import edu.neu.absorb.dto.request.SignUpRequest;
import edu.neu.absorb.dto.response.UserInfoResponse;
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
    @GetMapping("/info")
    public CommonResponse getUserInfo(@RequestParam("id") Integer userId, @RequestParam("token") String token) {
        // validate token
        User userInfo = userService.getUserByToken(token);
        if (userInfo == null || !userInfo.getUserId().equals(userId)) {
            // no authorization
            throw new AuthException(ExceptionEnum.AUTH_EXCEPTION);
        }
        return CommonResponse.success(new UserInfoResponse(userService.getUserByUserId(userId)));
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

    /**
     * sign up request
     * @param request sign up request
     * @return result
     */
    @PostMapping("/create")
    public CommonResponse signUp(@RequestBody SignUpRequest request) {
        return CommonResponse.success(userService.signUp(request));
    }

    /**
     * change user password
     * @param request change password request
     * @return result
     */
    @PostMapping("/change_password")
    public CommonResponse changePassword(@RequestBody ChangePasswordRequest request) {
        // validate token
        if (!userService.validateToken(request.getUserId(), request.getToken())) {
            throw new AuthException(ExceptionEnum.AUTH_EXCEPTION);
        }
        // validate old password
        if (!userService.validateOldPassword(request.getUserId(), request.getOldPassword())) {
            return CommonResponse.success("Incorrect old password");
        }
        // update new password
        if (!userService.changePassword(request.getUserId(), request.getNewPassword())) {
            return CommonResponse.success("Invalid new password");
        }
        return CommonResponse.success("Successfully change password");
    }
}
