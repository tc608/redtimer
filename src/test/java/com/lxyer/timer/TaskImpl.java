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
        System.out.println("----");
        System.out.println(new SimpleDateFormat("0: yyyy-MM-dd HH:mm:ss").format(theTime()));
        System.out.println(new SimpleDateFormat("1: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("2: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("3: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("4: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("5: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("6: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("7: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("8: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("9: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("10: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("11: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("12: yyyy-MM-dd HH:mm:ss").format(nextTime()));
        System.out.println(new SimpleDateFormat("13: yyyy-MM-dd HH:mm:ss").format(nextTime()));
    }
}
