package com.lxyer.timer;

import com.lxyer.timer.scheduled.ScheduledCycle;
import com.lxyer.timer.scheduled.ScheduledExpres;
import com.lxyer.timer.task.Task;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * t2 测试定时器加入任务的调度管理
 * t6 测试时间表达式配置校验
 * 其他测试用例
 * Created by liangxianyou at 2018/7/19 17:19.
 */
@SuppressWarnings("Duplicates")
public class TimerTest {

    /**
     * 启动定时器测试，
     */
    @Test
    public void t2() throws InterruptedException {
        TimerExecutor timerExecutor = new TimerExecutor(1);
        //Task t1 = new TaskImpl("a1", new ScheduledExpres("1-40 * * * *"));//1-40，定时每分钟执行
        //TaskImpl t2 = new TaskImpl("a2", ScheduledCycle.of(5000 * 1));
        Task task = TimerTask.by("A2", ScheduledCycle.of(1000 * 1), () -> {
            System.out.println("xxxx");

            return false;// false 继续执行， true结束任务
        });


        timerExecutor.add(task);

        //60s后修改a1 每2s执行一次
        //Thread.sleep(1000);
        //task = timerExecutor.get("a1");
        /*if (t1 != null){
            t1.setScheduled(new ScheduledCycle(2000 * 1));
            timerExecutor.add(t1);
        }*/




        Thread.sleep(5000);
    }

    /**
     * 测试给配置的时间 加1分钟
     */
    @Test
    public void t3(){

        // MM-dd HH:mm:ss
        //0 2 * * *
        //0-59 0-23 1-31(L) 1-12 0-6

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime next = null;

        String str = "8 18 * 7,8 4";
        String[] ss = str.split(" ");
        for (String s : ss) System.out.println(s);
        System.out.println("----------");
        //Minute *  1,3  1-3  */5  3-15/5  5
        String minute = ss[0];

        if ("*".equals(minute)){//*
            next = start.plusMinutes(1);
        } else if (minute.matches("^[0-5]??[0-9]??$")){//n
            next = LocalDateTime.of(start.getYear(),
                    start.getMonth(),
                    start.getDayOfMonth(),
                    start.getHour(),
                    Integer.parseInt(minute));
        }else if (minute.matches("^[*]/[0-9]+$")){// */5
            next = start.plusMinutes(Integer.parseInt(minute.replace("*/","")));
        }else if (minute.matches("^([0-5]??[0-9]??,)+([0-5]??[0-9]??)?  $")){//1,3
            String[] minutes = minute.split(",");
            int inx=0;
            for (int i = 0; i < minutes.length-1; i++) {
                if (start.getMinute() >= Integer.parseInt(minutes[i]) && start.getMinute()< Integer.parseInt(minutes[i+1])){
                    inx = i+1;
                }
            }
            next = LocalDateTime.of(start.getYear(),
                    start.getMonth(),
                    start.getDayOfMonth(),
                    start.getHour(),
                    Integer.parseInt(minutes[inx]));
        }else if (minute.matches("^[0-5]??[0-9]??\\-[0-5]??[0-9]??$")){//1-3
            int m = start.getMinute();
            String[] split = minute.split("-");
            int s = Integer.parseInt(split[0]);
            int e = Integer.parseInt(split[1]);

            if (m < s || m > e){
                m = s;
            }else{
                m = (m+1) < 60 ? (m+1) : 0;
            }
            next = LocalDateTime.of(start.getYear(),
                    start.getMonth(),
                    start.getDayOfMonth(),
                    start.getHour(),
                    m);
        }else if (minute.matches("^[0-5]??[0-9]??\\-[0-5]??[0-9]??/[0-5]??[0-9]??$")){//3-18/5
            //
        }

        System.out.println(next.getMinute());
    }

    /**
     * 测试各种正则表达式的合法性
     */
    @Test
    public void t4(){
        //Pattern pattern = Pattern.compile();
        //Minute *  1,3  1-3  */5  3-15/5

        //System.out.println("05".matches("^[0-5]??[0-9]??$"));
        //System.out.println("*/5".matches("^[*]/[0-9]+$"));
        //System.out.println("1,5,10,59".matches("^([0-5]??[0-9]??,)+([0-5]??[0-9]??)?$"));
        //System.out.println("1-3".matches("^[0-5]??[0-9]??\\-[0-5]??[0-9]??$"));
        //System.out.println("3-18/5".matches("^[0-5]??[0-9]??\\-[0-5]??[0-9]??/[0-5]??[0-9]??$"));

        String str = "01-05/5";// *  1,3  1-3  */5  3-15/5
        System.out.println(str.matches("^[0-5]??[0-9]??$"));
        System.out.println(str.matches("^[*]/[0-9]+$"));
        System.out.println(str.matches("^([0-5]??[0-9]??,)+([0-5]??[0-9]??)?$"));
        System.out.println(str.matches("^[0-5]??[0-9]??\\-[0-5]??[0-9]??$"));
        System.out.println(str.matches("^[0-5]??[0-9]??\\-[0-5]??[0-9]??/[0-5]??[0-9]??$"));

    }

    /**
     * 测试配置的表达式
     */
    @Test
    public void t6(){
        //分 时 日 月 周
        TimerExecutor executor = new TimerExecutor(10);

        /*
        08 18 * 7,8 4
        "task1", "1 22-23 * * 7"
         */
        Task task = TimerTask.by("task1", ScheduledExpres.of("1 22-23 * * 7"), () -> {

            //System.out.println("");

            System.out.println("----");
            //System.out.println(new SimpleDateFormat("0: yyyy-MM-dd HH:mm:ss").format(theTime()));
            //System.out.println(new SimpleDateFormat("1: yyyy-MM-dd HH:mm:ss").format(nextTime()));
            /*System.out.println(new SimpleDateFormat("2: yyyy-MM-dd HH:mm:ss").format(nextTime()));
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
                System.out.println(new SimpleDateFormat("13: yyyy-MM-dd HH:mm:ss").format(nextTime()));*/

            return true;
        });
        task.run();
        task.setScheduled(ScheduledCycle.of(1000 * 5));//定时每秒执行
        task.run();

    }

    @Test
    public void t7() {
        TimerExecutor executor = new TimerExecutor(1);

        executor.add(TimerTask.by("A1", ScheduledCycle.of(1000 * 5), () -> {
            try {
                Thread.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task do..");
            return true;
        }));


        try {
            Thread.sleep(1000 * 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
