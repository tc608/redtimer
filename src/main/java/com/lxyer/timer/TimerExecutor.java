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
        start();
    }

    public void add(Task ... task){
        for (Task t : task) {
            queue.put(t);
        }
    }
    private void add(Task task, boolean upTime){
        if (upTime) task.nextTime();
        queue.put(task);
    }

    public Task remove(String name){
        return queue.remove(name);
    }
    public Task get(String name){
        return queue.get(name);
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