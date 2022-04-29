package edu.neu.absorb.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author novo
 * @since 2022/4/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    private String username;
    private String nickname;
    private String password;
    private String token;
    private Date createTime;
    private Date updateTime;
    private Integer score;
}
