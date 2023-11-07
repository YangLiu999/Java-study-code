package com.createthread;

/**
 * 死锁示例
 * @author YL
 * @date 2023/11/07
 **/
public class DeadLockSimple extends Thread{

    private String first;
    private String second;

    public DeadLockSimple(String name,String first, String second) {
        super(name);
        this.first = first;
        this.second = second;
    }

    @Override
    public void run() {
        synchronized (first) {
            System.out.println(this.getName() + " obtained: " + first);
            try {
                Thread.sleep(1000L);
                synchronized (second) {
                    System.out.println(this.getName() + " obtained: " + second);
                }
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        DeadLockSimple t1 = new DeadLockSimple("thread1",lockA,lockB);
        DeadLockSimple t2 = new DeadLockSimple("thread2",lockB,lockA);

        t1.start();
        t2.start();

        t1.run();
        t2.run();
    }
}
