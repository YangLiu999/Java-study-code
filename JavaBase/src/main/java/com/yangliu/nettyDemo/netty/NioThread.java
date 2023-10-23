package com.yangliu.nettyDemo.netty;

import com.yangliu.nettyDemo.handler.CDHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class NioThread implements Runnable{
    private  final ChannelHandlerContext ctx;
    private byte[] req;
    private CDHandler cdHandler;

    public NioThread(ChannelHandlerContext ctx, byte[] req, CDHandler cdHandler) {
        this.ctx = ctx;
        this.req = req;
        this.cdHandler = cdHandler;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    @Override
    public void run() {
        ByteBuf out = null;
        try {
            byte[] resp = cdHandler.doHandle(req);
            if (resp == null){
                //throw Exception
            }else {
                out = NioKit.getOutBytes(resp);
            }

        }catch (Exception e){

        }

        synchronized (this.ctx){
            Boolean flag = NioSync.SyncMap.get(ctx);
            if (flag != null &&flag){
                NioSync.SyncMap.remove(ctx);
                if (out != null){
                    ctx.writeAndFlush(out).addListener(ChannelFutureListener.CLOSE);
                }else {
                    ctx.close();
                }
            }else {
                //throw Exception
            }

        }
    }
}
