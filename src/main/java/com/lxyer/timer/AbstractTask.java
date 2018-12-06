package com.lxyer.timer;

import com.lxyer.timer.scheduled.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Created by liangxianyou at 2018/7/23 14:33.
 */
public abstract class AbstractTask implements Task {
    protected String name;
    private long theTime;
    private Scheduled scheduled;
    private boolean isComplete;
    private long startTime = System.currentTimeMillis();

    public AbstractTask(String name, Scheduled scheduled) {
        this.name = name;
        this.scheduled = scheduled;
        this.theTime = scheduled.theTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setScheduled(Scheduled scheduled) {
        this.scheduled = scheduled;
        this.theTime = scheduled.theTime().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    @Override
    public long nextTime(){
        LocalDateTime next = scheduled.nextTime();
        this.theTime = next.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("下次执行:"+ sdf.format(next.toInstant(ZoneOffset.of("+8")).toEpochMilli()));*/
        return theTime;
    }
    @Override
    public long theTime(){
        LocalDateTime next = scheduled.theTime();
        this.theTime = next.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return theTime;
    }

    @Override
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public long startTime() {
        return startTime;
    }
}

