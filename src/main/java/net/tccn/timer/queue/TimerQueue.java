package net.tccn.timer.queue;

import net.tccn.timer.task.Task;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by liangxianyou at 2018/7/23 14:07.
 */
public class TimerQueue {
    Object lock = new Object();
    LinkedList<Task> queue = new LinkedList();
    Set<String> names = new HashSet<>();

    /**
     * 新加调度任务
     *
     * @param task
     */
    public void push(Task task) {
        synchronized (lock) {
            remove(task.getName());
            int inx = queue.size();//目标坐标
            while (inx > 0 && queue.get(inx).theTime() > task.theTime()) {
                inx--;
            }

            queue.add(inx, task);

            //size++;
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
            while (queue.size() == 0) {
                lock.wait(10);//循环避免非put线程唤醒空异常
            }

            long currentTime = System.currentTimeMillis();
            long nextTime = queue.getFirst().theTime();

            if (currentTime >= nextTime) {
                return queue.removeFirst();
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
            if (!names.contains(name)) {
                return null;
            }

            Task take = null;
            for (int i = 0; i < queue.size(); i++) {
                if (name.equals(queue.get(i).getName())) {
                    take = queue.get(i);
                }
            }
            if (remove && take != null) {
                queue.remove(take);
                names.remove(take.getName());
            }

            lock.notify();
            return take;
        }
    }
}
