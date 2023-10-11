package com.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author YL
 * @date 2023/08/17
 **/
public class ReentrantReadWriteLockTest {

    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {

        lock.readLock().lock();

        lock.writeLock().lock();

    }

}
