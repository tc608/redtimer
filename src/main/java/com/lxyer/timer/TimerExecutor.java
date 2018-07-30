package com.lxyer.timer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liangxianyou at 2018/7/23 14:07.
 */
public class TimerExecutor {
    private TimerQueue queue = new TimerQueue();
    private ExecutorService executor;

    /**
     * 创建定时调度器
     * @param n 调度线程数
     */
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

    /**
     * 启动定时调度
     */
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

class TimerQueue{
    Object lock = new Object();
    Task[] queue = new Task[128];
    Set<String> names = new HashSet<>();
    int size=0;

    void put(Task task) {
        remove(task.name);
        synchronized (lock){
            int inx = size;//目标坐标
            while (inx > 0 && queue[inx-1].nextTime > task.nextTime ){
                inx--;
            }

            if (queue.length == size+1)
                queue = Arrays.copyOf(queue, size * 2);

            for (int i = size+1; i > inx; i--) {
                queue[i] = queue[i-1];
                queue[i-1] = null;
            }
            queue[inx] = task;

            size++;
            names.add(task.name);
            lock.notify();
        }
    }

    Task take() throws InterruptedException {
        synchronized (lock){
            if (size == 0) lock.wait();

            long currentTime = System.currentTimeMillis();
            long nextTime = queue[0].nextTime;

            if (currentTime >= nextTime){
                Task task = queue[0];
                for (int i = 0; i < size;i++) {
                    queue[i] = queue[i+1];
                }
                queue[size-1] = null;
                size--;
                return task;
            }else {
                lock.wait(nextTime - currentTime);
                return take();
            }
        }
    }

    Task remove(String name){
        synchronized (lock){
            if(!names.contains(name)) return null;

            Task take = null;
            for (int i = 0; i < size-1; i++) {
                if (name.equals(queue[i].name)){
                    take = queue[i];
                    while (i < size+1){
                        queue[i] = queue[i+1];
                        queue[i+1] = null;
                    }
                    names.remove(name);
                    break;
                }
            }
            return take;
        }
    }
}