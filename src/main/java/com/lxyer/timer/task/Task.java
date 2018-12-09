package com.lxyer.timer.task;

import com.lxyer.timer.TimerExecutor;
import com.lxyer.timer.scheduled.Scheduled;

/**
 * @author: liangxianyou at 2018/8/5 19:32.
 */
public interface Task extends Runnable {

    /**
     * 得到任务名称
     *
     * @return
     */
    String getName();

    /**
     * 设置任务执行计划
     *
     * @param scheduled
     */
    void setScheduled(Scheduled scheduled);

    /**
     * 得到下一次执行计划的时间，并设置thenTime
     * @return
     */
    long nextTime();

    /**
     * 任务即将执行的时间点
     *
     * @return
     */
    long theTime();

    /**
     * 是否完成
     * @return
     */
    boolean isComplete();

    /**
     * 开始时间（创建时间）
     * @return
     */
    long startTime();

    TimerExecutor getTimerExecutor();

    void setTimerExecutor(TimerExecutor timerExecutor);
}