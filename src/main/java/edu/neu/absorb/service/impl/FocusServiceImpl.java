package edu.neu.absorb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.neu.absorb.mapper.FocusHistoryMapper;
import edu.neu.absorb.pojo.FocusHistory;
import edu.neu.absorb.service.FocusService;
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

    @Override
    public boolean addFocusRecord(FocusHistory focusHistory) {
        return focusHistoryMapper.insert(focusHistory) == 1;
    }

    @Override
    public List<FocusHistory> getFocusHistoryListByUserId(Integer userId) {
        QueryWrapper<FocusHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return focusHistoryMapper.selectList(queryWrapper);
    }

    @Override
    public FocusHistory getFocusDetailByHistoryId(Integer historyId) {
        return focusHistoryMapper.selectById(historyId);
    }
}
