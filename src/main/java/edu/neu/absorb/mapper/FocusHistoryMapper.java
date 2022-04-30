package edu.neu.absorb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.neu.absorb.dto.response.LeaderboardRecordInfo;
import edu.neu.absorb.pojo.FocusHistory;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author novo
 * @since 2022/4/20
 */
@Repository
public interface FocusHistoryMapper extends BaseMapper<FocusHistory> {
    // get the id that just inserted
    @Select("SELECT last_insert_id()")
    Integer getLastCreatedUserId();

    @Select(" SELECT user_id, COUNT(*) AS focus_count, SUM(UNIX_TIMESTAMP(end_time) - UNIX_TIMESTAMP(start_time))/60/60 AS total_hour FROM focus_history" +
            " GROUP BY user_id" +
            " ORDER BY total_hour DESC" +
            " LIMIT 10;")
    List<LeaderboardRecordInfo> getTotalFocusLeaderBoardTop10();

    @Select(" SELECT today_history.user_id, COUNT(*) AS focus_count, SUM(UNIX_TIMESTAMP(today_history.end_time) - UNIX_TIMESTAMP(today_history.start_time))/60/60 AS total_hour FROM" +
            "   (SELECT * FROM focus_history WHERE TO_DAYS(start_time) = TO_DAYS(NOW())) AS today_history" +
            " GROUP BY today_history.user_id" +
            " ORDER BY total_hour DESC" +
            " LIMIT 10;")
    List<LeaderboardRecordInfo> getTodayFocusLeaderBoardTop10();

    @Select("SELECT SUM(UNIX_TIMESTAMP(end_time) - UNIX_TIMESTAMP(start_time))/60/60 AS total_hour FROM focus_history\n" +
            " WHERE user_id = #{userId};")
    int getTotalFocusHoursByUserId(Integer userId);
}
