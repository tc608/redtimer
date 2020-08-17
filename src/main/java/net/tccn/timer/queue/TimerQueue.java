package net.tccn.timer.queue;

import net.tccn.timer.task.Task;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by liangxianyou at 2018/7/23 14:07.
 */
public class TimerQueue {
    Object lock = new Object();
    Task[] queue = new Task[128];
    Set<String> names = new HashSet<>();
    int size = 0;

    /**
     * 新加调度任务
     *
     * @param task
     */
    public void push(Task task) {
        synchronized (lock) {
            remove(task.getName());
            int inx = size;//目标坐标
            while (inx > 0 && queue[inx - 1].theTime() > task.theTime()) {
                inx--;
            }

            if (queue.length == size + 1)
                queue = Arrays.copyOf(queue, size * 2);

            for (int i = size + 1; i > inx; i--) {
                queue[i] = queue[i - 1];
            }
            queue[inx] = task;

            size++;
            names.add(task.getName());
            lock.notify();
        }
    }

    /**
     * 调度等待执行的任务
     *
     * @return
     * @throws InterruptedException
     */
    public Task take() throws InterruptedException {
        synchronized (lock) {
            while (size == 0) lock.wait(10);//循环避免非put线程唤醒空异常

            long currentTime = System.currentTimeMillis();
            long nextTime = queue[0].theTime();

            if (currentTime >= nextTime) {
                Task task = queue[0];
                for (int i = 0; i < size; i++) {
                    queue[i] = queue[i + 1];
                }
                queue[size - 1] = null;
                size--;
                return task;
            } else {
                lock.wait(nextTime - currentTime);
                return take();
            }
        }
    }

    /**
     * 删除指定名称的任务
     *
     * @param name
     * @return
     */
    public Task remove(String name) {
        return get(name, true);
    }

    /**
     * 返回指定名称的任务
     *
     * @param name
     * @return
     */
    public Task get(String name) {
        return get(name, false);
    }

    private Task get(String name, boolean remove) {
        synchronized (lock) {
            if (!names.contains(name)) return null;

            Task take = null;
            for (int i = 0; i < size; i++) {
                if (name.equals(queue[i].getName())) {
                    take = queue[i];
                    if (!remove) break;
                    while (i < size + 1) {
                        queue[i] = queue[i + 1];
                        queue[i + 1] = null;
                        i++;
                    }
                    names.remove(name);
                    size--;
                    break;
                }
            }
            lock.notify();
            return take;
        }
    }
}
