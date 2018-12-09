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
    private TemporalUnit unit = ChronoUnit.MILLIS;

    /**
     * 构造方法仅限类内部使用，为降低使用成本统一使用 of 的静态方法构建对象
     * @param period
     */
    @Deprecated
    public ScheduledCycle(long period) {
        this.theTime = LocalDateTime.now().plus(period, ChronoUnit.MILLIS);
        this.period = period;
    }
    @Deprecated
    public ScheduledCycle(long period,TemporalUnit unit) {
        this.theTime = LocalDateTime.now().plus(period, unit);
        this.period = period;
        this.unit = unit;
    }
    @Deprecated
    public ScheduledCycle(LocalDateTime startTime, long period) {
        this.theTime = startTime;
        this.period = period;
    }
    @Deprecated
    public ScheduledCycle(LocalDateTime startTime, long period, TemporalUnit unit) {
        this.theTime = startTime;
        this.period = period;
        this.unit = unit;
    }

    public static Scheduled of(long period) {
        return new ScheduledCycle(period);
    }
    public static Scheduled of(long period,TemporalUnit unit) {
        return new ScheduledCycle(period, unit);
    }
    public static Scheduled of(LocalDateTime startTime, long period) {
        return new ScheduledCycle(startTime, period);
    }
    public static Scheduled of(LocalDateTime startTime, long period, TemporalUnit unit) {
        return new ScheduledCycle(startTime, period, unit);
    }

    @Override
    public LocalDateTime nextTime() {
        return theTime = theTime.plus(period, unit);
    }

    @Override
    public LocalDateTime theTime() {
        return theTime;
    }
}
