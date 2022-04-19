package edu.neu.absorb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author novo
 * @since 2022/4/19
 */
@Data
@AllArgsConstructor
public class ChangePasswordRequest {
    private Integer userId;
    private String token;
    private String oldPassword;
    private String newPassword;
}
