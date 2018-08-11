package com.lxyer.timer;

/**
 * @author: liangxianyou at 2018/8/5 19:32.
 */
public interface Task extends Runnable{

    String getName();
    long nextTime();
    long theTime();
    void run();
}
