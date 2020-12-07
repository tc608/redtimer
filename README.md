# redtimer

#### 项目介绍
redtimer是本人纯手工精心编写的一个任务定时调度器，全部代码均自己编写实现；
功能包含**定时和时间解析**两部分功能；
编写她的意图，用更少的代码满足自己的业务功能需求，让其他业务代码更佳的随心所欲；

为什么叫red，因为喜欢redkale，所以就加个red开头了，包括其他redbbs，可能以后更多redxxxx项目的产生，(也挺喜欢jfinal的，可能也会起个Jabc，开玩笑的);
项目地址：[https://gitee.com/tc608/redtimer](https://gitee.com/tc608/redtimer)

#### 软件架构
![](http://img.1216.top/redbbs/20180811122309.png)


#### 安装使用教程
##### 创建启动定时任务
```
TimerExecutor timer = new TimerExecutor(1);

//A1 任务每 5s 执行一次
Task task1 = TimerTask.by("A1", ScheduledCycle.of(1000 * 5), t -> {
    System.out.println(t.getName() + " 执行了");
});

//A2 任务每小时的1-40每分钟执行
Task task2 = TimerTask.by("A2", ScheduledExpres.of("1-40 * * * *"), t -> {
    System.out.println(t.getName() + " 执行了");

    task.setScheduled(ScheduledCycle.of(2000 * 1)); //修改当前任务执行计划
    // t.setComplete(true); //在执行任务的过程中 设置任务状态为[完成]，配合 timerExecutor.remove("taskname") 可很好的使用在系统数据过期检查中
});

timer.add(task1, task2); //添加任务 task1，task2

timer.remove("A1"); //从任务队列中删除A1任务
```
##### 计划时间定义
```
// ScheduledCycle 周期任务定义：
//传入数值，单位毫秒
ScheduledCycle.of(2000 * 1);
// 数值+单位，如：2s 内置转换表示 2秒，如： 
ScheduledCycle.of("2s"); // 每 2秒 执行执行一次
ScheduledCycle.of("2m"); // 每 2分钟 执行执行一次
ScheduledCycle.of("2H"); // 每 2小时 执行执行一次
ScheduledCycle.of("2d"); // 每 2天 执行执行一次

// ScheduledExpres 定义，从左到右空格分割共5位，分别表示：分-时-日-月-周，支持同 Linux中的 crond 时间表达式
ScheduledExpres.of("1-40 * * * *"); // 任务每小时的1-40每分钟执行

```

##### 支持定时计划修改
```
//修改a1 每2s执行一次
task.setScheduled(ScheduledCycle.of(2000 * 1));
timer.add(task);
```
请看测试案例，以及阅读详细源码，所有的代码也就几百行；

#### 关于
redbbs 交流群：527523235  
redkale交流群：527523235  
**有问题，欢迎进群反馈交流**