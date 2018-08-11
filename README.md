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
创建启动定时任务
```
TimerExecutor timerExecutor = new TimerExecutor(1);
Task task1 = new TaskImpl("a1", new ScheduledExpres("1-40 * * * *"));//1-40，定时每分钟执行
Task task2 = new TaskImpl("a1", new ScheduledCycle(1000 * 5));//a2定时每5s执行

timerExecutor.add(task1, task2);//添加任务 task1，task2
```

支持定时计划修改
```
//修改a1 每2s执行一次
task.setScheduled(new ScheduledCycle(2000 * 1));
timerExecutor.add(task);
```
请看测试案例，以及阅读详细源码，所有的代码也就几百行；

#### 关于
redbbs 交流群：527523235  
redkale交流群：527523235  
**有问题进群反馈交流，坐等你的到来**