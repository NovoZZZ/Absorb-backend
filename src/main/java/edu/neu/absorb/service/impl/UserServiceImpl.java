package edu.neu.absorb.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.neu.absorb.dto.request.LoginRequest;
import edu.neu.absorb.dto.request.SignUpRequest;
import edu.neu.absorb.dto.response.LoginResponse;
import edu.neu.absorb.exception.AuthException;
import edu.neu.absorb.exception.CommonException;
import edu.neu.absorb.exception.ExceptionEnum;
import edu.neu.absorb.mapper.UserMapper;
import edu.neu.absorb.pojo.User;
import edu.neu.absorb.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author novo
 * @since 2022/4/15
 */
@Service
public class UserServiceImpl implements UserService {

    // user mapper
    @Autowired
    private UserMapper userMapper;

    private static final String PASSWORD_SALT = "ffc81086bd1311ec9d640242ac120002";

    @Override
    public User getUserByUserId(@NonNull Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User getUserByUsername(@NonNull String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public User getUserByToken(@NonNull String token) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("token", token);
        return userMapper.selectOne(userQueryWrapper);
    }

    @Override
    public LoginResponse login(@NonNull LoginRequest loginRequest) {
        User userInfo = getUserByUsername(loginRequest.getUsername());
        if (userInfo == null) {
            throw new AuthException(ExceptionEnum.LOGIN_FAILED_EXCEPTION);
        }
        // encrypt password
        String encryptedPassword = DigestUtil.md5Hex(loginRequest.getPassword());
        if (!userInfo.getPassword().equals(encryptedPassword)) {
            throw new AuthException(ExceptionEnum.LOGIN_FAILED_EXCEPTION);
        }
        // pass validation, generate new token
        String newToken = generateNewToken(userInfo.getUserId());
        // return user login info
        return new LoginResponse(userInfo.getUsername(), userInfo.getNickname(), newToken);
    }

    @Override
    public LoginResponse signUp(@NonNull SignUpRequest signUpRequest) {
        // check parameters
        if (signUpRequest.getUsername().equals("")
                || signUpRequest.getNickname().equals("")
                || signUpRequest.getPassword().equals("")) {
            throw new CommonException(ExceptionEnum.ILLEGAL_ARGUMENT_EXCEPTION);
        }
        // check if there are duplicate usernames

        return null;
    }

    /**
     * generate new token
     * @param userId target user
     * @return new token
     */
    private String generateNewToken(@NonNull Integer userId) {
        // get user info
        User userInfo = userMapper.selectById(userId);
        if (userInfo == null) {
            throw new RuntimeException("generateNewToken -- " + userId + " doesn't exist");
        }
        // generate new token
        String token = UUID.fastUUID().toString();
        userInfo.setToken(token);
        // update
        userMapper.updateById(userInfo);
        return token;
    }

}
