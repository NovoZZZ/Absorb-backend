package edu.neu.absorb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author novo
 * @since 2022/4/15
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private Integer userId;
    private String username;
    private String nickname;
    private String token;
}
