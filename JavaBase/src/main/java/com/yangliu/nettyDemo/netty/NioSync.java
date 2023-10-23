package com.yangliu.nettyDemo.netty;

import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class NioSync {
    //对应的flag存在且为true，表示连接未关闭
    public static ConcurrentHashMap<ChannelHandlerContext,Boolean> SyncMap = new ConcurrentHashMap<>();
}
