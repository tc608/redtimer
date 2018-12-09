package com.lxyer.timer;

import com.lxyer.timer.scheduled.Scheduled;
import com.lxyer.timer.task.Job;
import com.lxyer.timer.task.Task;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by liangxianyou at 2018/7/23 14:33.
 */
public class TimerTask implements Task {
    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private long startTime = System.currentTimeMillis();
    protected String name;
    private long theTime;
    private Scheduled scheduled;
    private boolean isComplete;

    private TimerExecutor timerExecutor;
    private Job job;

    public static Task by(String name, Scheduled scheduled, Job job) {
        TimerTask task = new TimerTask();
        task.name = name;
        task.scheduled = scheduled;
        task.job = job;
        return task;
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
        timerExecutor.remove(name);
    }

    public TimerExecutor getTimerExecutor() {
        return timerExecutor;
    }

    public void setTimerExecutor(TimerExecutor timerExecutor) {
        this.timerExecutor = timerExecutor;
    }

    public long startTime() {
        return startTime;
    }

    @Override
    public void run() {
        //没有完成任务，继续执行，返回true,表示完成
        if (!isComplete) {
            long start = System.currentTimeMillis();
            StringBuilder buf = new StringBuilder();
            buf.append("task [" + getName() + "] : ").append("not complete -> ");
            long end;
            if (!(isComplete = job.execute())) {
                end = System.currentTimeMillis();
                timerExecutor.add(this, true);
            } else {
                end = System.currentTimeMillis();
            }

            logger.log(Level.INFO, buf.append(isComplete ? "had complete" : "not complete;").append("time: ").append(end - start).append(" ms").toString());
        }

    }
}

