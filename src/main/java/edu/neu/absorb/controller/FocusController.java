package edu.neu.absorb.controller;

import edu.neu.absorb.dto.CommonResponse;
import edu.neu.absorb.dto.request.AddFocusRecordRequest;
import edu.neu.absorb.exception.AuthException;
import edu.neu.absorb.exception.CommonException;
import edu.neu.absorb.exception.ExceptionEnum;
import edu.neu.absorb.service.FocusService;
import edu.neu.absorb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author novo
 * @since 2022/4/20
 */
@RestController
@RequestMapping("/focus")
public class FocusController {

    @Autowired
    FocusService focusService;

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public CommonResponse addFocusRecord(@RequestBody AddFocusRecordRequest request) {
        // validate token
        if (!userService.validateToken(request.getUserId(), request.getToken())) {
            throw new AuthException(ExceptionEnum.AUTH_EXCEPTION);
        }
        // add new focus record
        if (!focusService.addFocusRecord(request.convertToFocusHistory())) {
            throw new CommonException(ExceptionEnum.SERVER_EXCEPTION);
        }
        return CommonResponse.success("Successfully add focus record");
    }

    @GetMapping("/list")
    public CommonResponse getFocusList(@RequestParam("user_id") Integer userId, @RequestParam("token") String token) {
        // validate token
        if (!userService.validateToken(userId, token)) {
            throw new AuthException(ExceptionEnum.AUTH_EXCEPTION);
        }
        return CommonResponse.success(focusService.getFocusHistoryListByUserId(userId));
    }

    @GetMapping("/detail")
    public CommonResponse getDetailOfFocusRecord(@RequestParam("user_id") Integer userId,
                                                 @RequestParam("token") String token,
                                                 @RequestParam("history_id") Integer historyId) {
        // validate token
        if (!userService.validateToken(userId, token)) {
            throw new AuthException(ExceptionEnum.AUTH_EXCEPTION);
        }
        return CommonResponse.success(focusService.getFocusDetailByHistoryId(historyId));
    }

    /**
     * get foucus details by user ID
     *
     * @param userId
     * @return
     */
    @GetMapping("focusListById")
    public CommonResponse getFocusRecordById(@RequestParam("user_id") Integer userId) {
        return CommonResponse.success(focusService.getFocusHistoryListByUserId(userId));
    }

    /**
     * get leaderboard list
     * @param total show total record leaderboard or just today's leaderboard
     * @return result
     */
    @GetMapping("/leaderboard")
    public CommonResponse getLeaderboardTop10(@RequestParam("total") Integer total) {
        return CommonResponse.success(focusService.getLeaderboardTop10(total));
    }
}
