package com.yangliu.nettyDemo.netty;

import com.yangliu.nettyDemo.handler.CDHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.Executor;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class NettyServer implements Runnable{

    private int port;
    private Executor executor;
    private CDHandler cdHandler;
    private int threadPoolSize;
    private int timeout;

    public NettyServer(int port, Executor executor,  int threadPoolSize, int timeout) {
        this.port = port;
        this.executor = executor;
        this.threadPoolSize = threadPoolSize;
        this.timeout = timeout;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public CDHandler getCdHandler() {
        return cdHandler;
    }

    public void setCdHandler(CDHandler cdHandler) {
        this.cdHandler = cdHandler;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(this.getThreadPoolSize());
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel){
                            socketChannel.pipeline().addLast(new ReadTimeoutHandler(NettyServer.this.getTimeout()));
                            socketChannel.pipeline().addLast(new NettyHandler(executor,cdHandler));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128);

            ChannelFuture cf = bootstrap.bind(port).sync();
            //同步监听close事件后关闭server，防止nettyServer bootstrap.bind(port).sync()执行完后，
            // 很快进入finally关闭
            cf.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
