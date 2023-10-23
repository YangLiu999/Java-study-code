package com.yangliu.nettyDemo.netty;

import com.yangliu.nettyDemo.handler.CDHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executor;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class NettyHandler extends ChannelInboundHandlerAdapter implements Runnable {

    protected Executor executor;
    private ByteBuf head;
    private ByteBuf body;
    private CDHandler cdHandler;
    private boolean flag;//0:read head,1:read body else, error

    public NettyHandler(Executor executor, CDHandler cdHandler) {
        this.executor = executor;
        this.cdHandler = cdHandler;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public ByteBuf getHead() {
        return head;
    }

    public void setHead(ByteBuf head) {
        this.head = head;
    }

    public ByteBuf getBody() {
        return body;
    }

    public void setBody(ByteBuf body) {
        this.body = body;
    }

    public CDHandler getCdHandler() {
        return cdHandler;
    }

    public void setCdHandler(CDHandler cdHandler) {
        this.cdHandler = cdHandler;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        head = Unpooled.buffer(8);//前8为是长度
        flag = true;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf in = (ByteBuf) msg;
            if (flag){
                readHead(ctx,in);
            }else {
                readBody(ctx,in);
            }
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void readHead(ChannelHandlerContext ctx, ByteBuf in) {
        int hw = head.writableBytes();
        int ir = in.readableBytes();
        int toreadlen = (ir < hw) ?ir :hw;
        in.readBytes(head,toreadlen);
        if (head.readableBytes() == 8){
            flag = false;
            byte[] buf = head.array();
            try {
                String slen = new String(buf,"utf-8");
                int len = Integer.parseInt(slen);
                body = Unpooled.buffer(len);
            }catch (UnsupportedEncodingException e){
                throw new RuntimeException(e);
            }

            if (in.readableBytes() > 0){
                readBody(ctx,in);
            }
        }

    }

    private void readBody(ChannelHandlerContext ctx, ByteBuf in) {
        int hw = body.writableBytes();
        int ir = in.readableBytes();
        int toreadlen = (ir < hw) ?ir :hw;
        in.readBytes(body,toreadlen);
        if (body.writableBytes() == 0){
            byte[] buf = body.array();
            doHandle(buf,ctx,cdHandler);
        }
    }

    private void doHandle(byte[] buf, ChannelHandlerContext ctx, CDHandler cdHandler) {
        NioSync.SyncMap.put(ctx,true);
        NioThread nioThread = new NioThread(ctx,buf,cdHandler);
        executor.execute(nioThread);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void run() {

    }
}
