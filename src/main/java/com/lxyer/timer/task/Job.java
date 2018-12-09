package com.lxyer.timer.task;

/**
 * @author: liangxianyou at 2018/12/8 17:24.
 */
@FunctionalInterface
public interface Job {

    /**
     * 任务执行的内容
     * @return true:完成完成任务，false：未完成任务
     */
    boolean execute();

    /*default Job then(Job job) {
        return job;
    }*/

}
