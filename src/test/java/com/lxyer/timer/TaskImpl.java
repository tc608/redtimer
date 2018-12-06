package com.lxyer.timer;

import com.lxyer.timer.scheduled.Scheduled;

import java.text.SimpleDateFormat;

/**
 * @author: liangxianyou
 * @createtime: 2018/8/5 20:39.
 */
public class TaskImpl extends AbstractTask {
    public TaskImpl(String name, Scheduled scheduled) {
        super(name, scheduled);
    }

    @Override
    public void run() {
        ThreadLocal<SimpleDateFormat> local = new ThreadLocal<>();
        SimpleDateFormat sdf = local.get();
        if (sdf == null){
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        System.out.printf("执行任务:%s now:%s, %n", name, sdf.format(System.currentTimeMillis()));
    }

    @Override
    public long startTime() {
        return 0;
    }
}
