package edu.neu.absorb.dto.request;

import lombok.Data;

/**
 * @author novo
 * @since 2022/4/29
 */
@Data
public class UpdateNicknameRequest {
    private Integer userId;
    private String token;
    private String nickname;
}
