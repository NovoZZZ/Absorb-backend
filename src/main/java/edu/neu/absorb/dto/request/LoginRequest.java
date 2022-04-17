package edu.neu.absorb.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author novo
 * @since 2022/4/15
 */
@Data
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}
