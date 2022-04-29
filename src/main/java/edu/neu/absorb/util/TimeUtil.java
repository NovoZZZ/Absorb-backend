package edu.neu.absorb.util;

import java.util.Date;

/**
 * @author novo
 * @since 2022/4/29
 */
public class TimeUtil {
    public static Integer getIntervalMinutes(Date start, Date end) {
        return (int)((end.getTime() - start.getTime()) / (1000 * 6));
    }
}
