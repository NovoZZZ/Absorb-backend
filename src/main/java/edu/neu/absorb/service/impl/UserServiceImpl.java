package edu.neu.absorb.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.neu.absorb.dto.request.LoginRequest;
import edu.neu.absorb.dto.request.SignUpRequest;
import edu.neu.absorb.dto.response.LoginResponse;
import edu.neu.absorb.dto.response.UserInfoResponse;
import edu.neu.absorb.exception.AuthException;
import edu.neu.absorb.exception.CommonException;
import edu.neu.absorb.exception.ExceptionEnum;
import edu.neu.absorb.mapper.FocusHistoryMapper;
import edu.neu.absorb.mapper.UserMapper;
import edu.neu.absorb.pojo.FocusHistory;
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

    @Autowired
    private FocusHistoryMapper focusHistoryMapper;

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
        if (!DigestUtil.bcryptCheck(loginRequest.getPassword(), userInfo.getPassword())) {
            throw new AuthException(ExceptionEnum.WRONG_PASSWORD_EXCEPTION);
        }
        // pass validation, generate new token
        String newToken = generateNewToken(userInfo.getUserId());
        // return user login info
        return new LoginResponse(userInfo.getUserId(), userInfo.getUsername(), userInfo.getNickname(), newToken);
    }

    @Override
    public LoginResponse signUp(@NonNull SignUpRequest signUpRequest) {
        // check parameters
        if (signUpRequest.getUsername().trim().equals("")
                || signUpRequest.getNickname().trim().equals("")
                || signUpRequest.getPassword().trim().equals("")) {
            throw new CommonException(ExceptionEnum.ILLEGAL_ARGUMENT_EXCEPTION);
        }
        // check if there are duplicate usernames
        String currentUsername = signUpRequest.getUsername();
        QueryWrapper<User> usernameQuery = new QueryWrapper<>();
        usernameQuery.eq("username", currentUsername);
        if (userMapper.selectCount(usernameQuery) != 0) {
            throw new CommonException(ExceptionEnum.USERNAME_EXIST_EXCEPTION);
        }
        // insert new row
        User userInfo = new User(null, currentUsername, signUpRequest.getNickname(), DigestUtil.bcrypt(signUpRequest.getPassword()), null, null, null, 0);
        if (userMapper.insert(userInfo) != 1) {
            throw new CommonException(ExceptionEnum.SERVER_EXCEPTION);
        }
        // get the new user id
        Integer newUserId = userMapper.getLastCreatedUserId();
        // generate new token
        String newToken = generateNewToken(newUserId);
        // return result
        return new LoginResponse(newUserId,
                currentUsername,
                signUpRequest.getNickname(),
                newToken);
    }

    @Override
    public boolean validateToken(@NonNull Integer userId, @NonNull String token) {
        // get user info
        User user = userMapper.selectById(userId);
        return user.getToken().equals(token);
    }

    @Override
    public boolean changePassword(@NonNull Integer userId, @NonNull String newPassword) {
        if (newPassword.trim().equals("")) {
            return false;
        }
        // get user info
        User user = userMapper.selectById(userId);
        // encrypt password
        user.setPassword(DigestUtil.bcrypt(newPassword));
        // update password
        userMapper.updateById(user);
        return true;
    }

    @Override
    public boolean validateOldPassword(@NonNull Integer userId, @NonNull String password) {
        // get user info
        User user = userMapper.selectById(userId);
        // return check result
        return DigestUtil.bcryptCheck(password, user.getPassword());
    }

    /**
     * generate new token
     *
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


    @Override
    public boolean addScore(Integer userId, Integer score) {
        // get user info
        User userInfo = userMapper.selectById(userId);
        userInfo.setScore(userInfo.getScore() + score);
        return userMapper.updateById(userInfo) == 1;
    }

    @Override
    public UserInfoResponse getUserInfoByUserId(Integer userId) {
        // get user basic info
        UserInfoResponse userInfoResponse = new UserInfoResponse(getUserByUserId(userId));
        // get focus count
        QueryWrapper<FocusHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        Integer focusCount = focusHistoryMapper.selectCount(queryWrapper);
        userInfoResponse.setFocusCount(focusCount);
        // get total focus hours
        Integer totalFocusHours = focusHistoryMapper.getTotalFocusHoursByUserId(userId);
        userInfoResponse.setTotalHours(totalFocusHours == null ? 0 : totalFocusHours);
        return userInfoResponse;
    }

    @Override
    public boolean updateNickname(Integer userId, String nickname) {
        // get user info
        User userInfo = userMapper.selectById(userId);
        if (userInfo == null) {
            throw new RuntimeException("update nickname -- " + userId + " doesn't exist");
        }
        // update nickname
        userInfo.setNickname(nickname);
        // update
        userMapper.updateById(userInfo);
        return true;
    }

}
