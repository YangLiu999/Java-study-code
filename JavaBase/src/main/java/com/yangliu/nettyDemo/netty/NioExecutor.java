package com.yangliu.nettyDemo.netty;


import org.springframework.beans.factory.FactoryBean;

import java.util.concurrent.*;

/**
 * @author YL
 * @date 2022/01/18
 **/
public class NioExecutor implements FactoryBean<Executor> {//spring的factoryBean
    private int corePoolSize;//32
    private int queueSize;

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    @Override
    public Executor getObject(){
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(queueSize);
        return new ThreadPoolExecutor(corePoolSize,corePoolSize,0, TimeUnit.NANOSECONDS,queue,new RejectExecution());
    }

    @Override
    public Class<?> getObjectType() {
        return ThreadPoolExecutor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    //自定义拒绝策略
    private class RejectExecution implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

        }
    }
}
