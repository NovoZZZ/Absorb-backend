package edu.neu.absorb.dto.response;

import edu.neu.absorb.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author novo
 * @since 2022/4/17
 */
@Data
public class UserInfoResponse {
    private Integer userId;
    private String username;
    private String nickname;

    public UserInfoResponse(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
