package edu.neu.absorb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author novo
 * @since 2022/4/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaderboardRecordInfo {
    private Integer userId;
    private String username;
    private Date createTime;
    private Double totalHour;
    private Integer focusCount;
    private Integer score;
}
