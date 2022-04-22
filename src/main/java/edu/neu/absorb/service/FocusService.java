package edu.neu.absorb.service;

import edu.neu.absorb.dto.response.LeaderboardRecordInfo;
import edu.neu.absorb.pojo.FocusHistory;

import java.util.List;

/**
 * @author novo
 * @since 2022/4/20
 */
public interface FocusService {
    /**
     * add one focus history record
     *
     * @param focusHistory new focus history record
     * @return success or not
     */
    boolean addFocusRecord(FocusHistory focusHistory);

    /**
     * get a list of user's focus history
     *
     * @param userId target user id
     * @return list of focus history
     */
    List<FocusHistory> getFocusHistoryListByUserId(Integer userId);

    /**
     * get a focus detail by history id
     *
     * @param historyId target history id
     * @return focus detail
     */
    FocusHistory getFocusDetailByHistoryId(Integer historyId);

    /**
     * get leaderboard top 10 records
     *
     * @param total show total records or just today's record, 1=total, 0=today
     * @return result
     */
    List<LeaderboardRecordInfo> getLeaderboardTop10(Integer total);
}
