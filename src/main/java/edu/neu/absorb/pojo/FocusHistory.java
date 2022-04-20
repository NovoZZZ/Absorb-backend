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
 * @since 2022/4/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("focus_history")
public class FocusHistory {
    @TableId(value = "history_id", type = IdType.AUTO)
    private Integer historyId;
    private Integer userId;
    private String description;
    private Date startTime;
    private Date endTime;
    private Date createTime;
    private Date updateTime;
}
