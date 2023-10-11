package com.executor;

import java.util.concurrent.*;

/**
 * 线程池相关demo
 * @author YL
 * @date 2023/08/03
 **/
public class ThreadPoolExecutorCode {

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5,10,120, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>());

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    }
}
