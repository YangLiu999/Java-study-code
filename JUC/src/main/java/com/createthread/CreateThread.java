package com.createthread;

import java.lang.Thread;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的方法
 * @author YL
 * @date 2023/03/01
 **/
public class CreateThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(getName()+"打了："+i+"个小兵");
        }

    }

    public static class MyRunnable implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {//sleep会发生异常要显示处理
                    Thread.sleep(20);//暂停20毫秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "打了:" + i + "个小兵");
            }
        }
    }

    public static class callTast implements Callable<String>{

        @Override
        public String call() throws Exception {
            return "Hello,i am running!";
        }
    }


    public static void main(String[] args) {
        /*CreateThread c1 = new CreateThread();
        CreateThread c2 = new CreateThread();
        CreateThread c3 = new CreateThread();

        c1.setName("亚瑟");
        c2.setName("小乔");
        c3.setName("鲁班");

        c1.start();
        c2.start();
        c3.start();*/

        //创建MyRunnable类
//        MyRunnable mr = new MyRunnable();
//        //创建Thread类的有参构造,并设置线程名
//        Thread t1 = new Thread(mr, "张飞");
//        Thread t2 = new Thread(mr, "貂蝉");
//        Thread t3 = new Thread(mr, "吕布");
//        //启动线程
//        t1.start();
//        t2.start();
//        t3.start();

        //创建异步任务
        FutureTask<String> task=new FutureTask<String>(new callTast());
        //启动线程
        new Thread(task).start();
        try {
            //等待执行完成，并获取返回结果
            String result=task.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
