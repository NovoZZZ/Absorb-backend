package edu.neu.absorb.dto.request;

import edu.neu.absorb.pojo.FocusHistory;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author novo
 * @since 2022/4/20
 */
@Data
@AllArgsConstructor
public class AddFocusRecordRequest {
    private Integer userId;
    private String description;
    private Date startTime;
    private Date endTime;
    private String token;
    private Double rate;

    public FocusHistory convertToFocusHistory() {
        return new FocusHistory(null,
                userId,
                description,
                startTime,
                endTime,
                null,
                null);
    }
}
