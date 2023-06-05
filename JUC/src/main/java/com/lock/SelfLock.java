package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author YL
 * @date 2023/05/30
 **/
public class SelfLock implements Lock {
    //AQS
    private static class Sync extends AbstractQueuedSynchronizer{
        //独占锁 排他锁
        //加锁 state int
        @Override
        public boolean tryAcquire(int acquires){
            if (compareAndSetState(0,acquires)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        //解锁
        @Override
        public boolean tryRelease(int releases){
            if (getState() == 0){
                throw new IllegalMonitorStateException();
            }
            setState(0);
            return true;
        }

        //创建condition
        Condition newCondition(){
            return new ConditionObject();
        }

        public boolean isLocked(){
            //锁定状态 state == 1
            return getState() == 1;
        }
    }

    private  final Sync sync = new Sync();
    @Override
    public void lock() {
        sync.acquire(1);
    }
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }
    @Override
    public void unlock() {
        sync.release(1);
    }
    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
    public boolean isLocked(){
        return sync.isLocked();
    }
    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }
}
