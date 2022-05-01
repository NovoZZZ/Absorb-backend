package edu.neu.absorb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.neu.absorb.dto.response.LeaderboardRecordInfo;
import edu.neu.absorb.mapper.FocusHistoryMapper;
import edu.neu.absorb.pojo.FocusHistory;
import edu.neu.absorb.pojo.User;
import edu.neu.absorb.service.FocusService;
import edu.neu.absorb.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author novo
 * @since 2022/4/20
 */
@Service
public class FocusServiceImpl implements FocusService {

    @Autowired
    FocusHistoryMapper focusHistoryMapper;

    @Autowired
    UserService userService;

    @Override
    public boolean addFocusRecord(@NonNull FocusHistory focusHistory) {
        return focusHistoryMapper.insert(focusHistory) == 1;
    }

    @Override
    public List<FocusHistory> getFocusHistoryListByUserId(@NonNull Integer userId) {
        QueryWrapper<FocusHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.orderByDesc("create_time");
        return focusHistoryMapper.selectList(queryWrapper);
    }

    @Override
    public FocusHistory getFocusDetailByHistoryId(@NonNull Integer historyId) {
        return focusHistoryMapper.selectById(historyId);
    }

    @Override
    public List<LeaderboardRecordInfo> getLeaderboardTop10(@NonNull Integer total) {
        List<LeaderboardRecordInfo> result;
        // select total or today
        if (total == 1) {
            result = focusHistoryMapper.getTotalFocusLeaderBoardTop10();
        } else {
            result = focusHistoryMapper.getTodayFocusLeaderBoardTop10();
        }
        // query user info
        for (LeaderboardRecordInfo info : result) {
            User userInfo = userService.getUserByUserId(info.getUserId());
            info.setUsername(userInfo.getUsername());
            info.setCreateTime(userInfo.getCreateTime());
            info.setScore(userInfo.getScore());
        }
        return result;
    }
}
