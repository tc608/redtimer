package com.lxyer.timer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liangxianyou at 2018/7/23 14:07.
 */
class TimerQueue{
    Object lock = new Object();
    Task[] queue = new Task[128];
    Set<String> names = new HashSet<>();
    int size=0;

    void put(Task task) {
        remove(task.getName());
        synchronized (lock){
            int inx = size;//目标坐标
            while (inx > 0 && queue[inx-1].theTime() > task.theTime()){
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
            names.add(task.getName());
            lock.notify();
        }
    }

    Task take() throws InterruptedException {
        synchronized (lock){
            if (size == 0) lock.wait();

            long currentTime = System.currentTimeMillis();
            long nextTime = queue[0].theTime();

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
                if (name.equals(queue[i].getName())){
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
