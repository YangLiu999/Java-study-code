package com.yangliu.fileupload;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;


/**
 * @author YL
 * @date 2023/10/23
 **/
public class HttpClientIntializer extends ChannelInitializer<SocketChannel> {

    private String tmpDir;

    private int timeout;

    public HttpClientIntializer(String tmpDir, int timeout) {
        this.tmpDir = tmpDir;
        this.timeout = timeout;
    }

    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();
        channelPipeline.addLast("readTimeout",
                new ReadTimeoutHandler((long)this.timeout, TimeUnit.MILLISECONDS));
        channelPipeline.addLast("codec",new HttpClientCodec());
        channelPipeline.addLast("chunkedWriter",new ChunkedWriteHandler());
        channelPipeline.addLast("handler",new HttpClientHandler(this.tmpDir));
    }
}
