package com.lxyer.timer.scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author: liangxianyou at 2018/8/5 18:05.
 */
public class ScheduledCycle implements Scheduled {

    private LocalDateTime theTime;
    private long period;
    private TemporalUnit unit = ChronoUnit.SECONDS;

    public ScheduledCycle(LocalDateTime startTime, long period) {
        this.theTime = startTime;
        this.period = period;
    }
    public ScheduledCycle(LocalDateTime startTime, long period, TemporalUnit unit) {
        this.theTime = startTime;
        this.period = period;
        this.unit = unit;
    }

    @Override
    public LocalDateTime nextTime() {
        return theTime.plus(period, unit);
    }

    @Override
    public LocalDateTime theTime() {
        return theTime;
    }
}
