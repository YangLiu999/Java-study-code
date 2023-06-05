package com.lock;

import java.util.concurrent.locks.Lock;

/**
 * @author YL
 * @date 2023/06/05
 **/
public class SelfLockTest {
    static Lock lock = new SelfLock();
    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(() -> {
            testLock();
        });
        Thread b = new Thread(() -> {
            testLock();
        });
        a.setName("线程a");
        b.setName("线程b");
        a.start();
        Thread.sleep(100);
        b.start();

    }
    public static void testLock(){
        lock.lock();
        try {
            System.out.println("获取到锁,线程："+Thread.currentThread().getName());
            while (true){

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
