package edu.neu.absorb.service;

import edu.neu.absorb.dto.request.LoginRequest;
import edu.neu.absorb.dto.request.SignUpRequest;
import edu.neu.absorb.dto.response.LoginResponse;
import edu.neu.absorb.pojo.User;

/**
 * @author novo
 * @since 2022/4/15
 */
public interface UserService {
    /**
     * Get user info by user id
     *
     * @param userId target user id
     * @return user info
     */
    User getUserByUserId(Integer userId);

    /**
     * Get user info by username
     *
     * @param username target username
     * @return user info
     */
    User getUserByUsername(String username);

    /**
     * Get user info by token
     *
     * @param token target token
     * @return user token
     */
    User getUserByToken(String token);

    /**
     * handle login request
     *
     * @param loginRequest login request
     * @return login response
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * handle sign up request
     *
     * @param signUpRequest sign up request
     * @return sign up response -- same as login response
     */
    LoginResponse signUp(SignUpRequest signUpRequest);

    /**
     * validate token is correct
     * @param userId target user id
     * @param token token
     * @return correct token or not
     */
    boolean validateToken(Integer userId, String token);

    /**
     * change user password
     *
     * @param userId      target user
     * @param newPassword new password
     * @return change password result
     */
    boolean changePassword(Integer userId, String newPassword);

    /**
     * validate user password is correct or not
     *
     * @param userId   target user
     * @param password old password
     * @return password is correct or not
     */
    boolean validateOldPassword(Integer userId, String password);
}
