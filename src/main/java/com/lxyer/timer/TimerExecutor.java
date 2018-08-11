package com.lxyer.timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: liangxianyou
 */
public class TimerExecutor {
    private TimerQueue queue = new TimerQueue();
    private ExecutorService executor;

    public TimerExecutor(int n) {
        executor = Executors.newFixedThreadPool(n);
    }

    public void add(Task task){
        queue.put(task);
    }
    private void add(Task task, boolean upTime){
        if (upTime) task.nextTime();
        queue.put(task);
    }


    public void start() {
        new Thread(()->{
            while (true){
                try{
                    Task take = null;
                    try {
                        take = queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    executor.execute(take);
                    add(take, true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}