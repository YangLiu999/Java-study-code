package com.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;

/**
 * @author YL
 * @date 2023/06/09
 **/
public class ReentrantLockTest {

    private static  Lock lock;
    public static void main(String[] args) {
        lock.lock();
        try {

        }finally {
            lock.unlock();
        }
        lock.tryLock();

    }
}
