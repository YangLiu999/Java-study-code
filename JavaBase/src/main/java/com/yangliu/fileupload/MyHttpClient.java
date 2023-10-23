package com.yangliu.fileupload;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author YL
 * @date 2023/10/23
 **/
public class MyHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
    private EventLoopGroup group;
    private Bootstrap b;
    private String host;
    private int port;
    private int CONNECT_TIMEOUT_MILLIS = 5000;
    private int READ_TIMEOUT_MILLIS = 30000;

    public MyHttpClient(String host, int port,String tmpDir) {
        this.host = host;
        this.port = port;
        this.group = new NioEventLoopGroup();
        DiskFileUpload.deleteOnExitTemporaryFile = true;
        DiskFileUpload.baseDirectory = null;
        DiskAttribute.deleteOnExitTemporaryFile = true;
        DiskAttribute.baseDirectory = null;
        this.b = new Bootstrap();
        this.b.option(ChannelOption.CONNECT_TIMEOUT_MILLIS,this.CONNECT_TIMEOUT_MILLIS);
        this.b.group(this.group).channel(NioSocketChannel.class)
                .handler(new HttpClientIntializer(tmpDir,this.READ_TIMEOUT_MILLIS));
    }

    public Boolean fileUpload(String url,String serverPath,String localFileName){
        logger.debug("fileUpload[host:{},port:{},url:{},serverPath:{},localFileName:{}]"
                ,new Object[]{this.host,this.port,url,serverPath,localFileName});
        RetValue ret = null;
        URI uriFile;
        try {
            uriFile = new URI(url);
        } catch (URISyntaxException e) {
            logger.error("fileUpload[ url not correct url:{}]",url,e);
            return false;
        }

        File file = new File(localFileName);
        if (!file.canRead()){
            logger.error("fileUpload[file no found localFileName:{}]",localFileName);
            return false;
        }else {
            try {
                ret = this.fileUpload(this.b,this.host,this.port,uriFile,serverPath,file);
            }catch (Exception var8){
                logger.error("fileUpload[fileUpload exception:{}]",var8);
                ret = new RetValue();
                ret.setSuccess(false);
            }

            if (DiskAttribute.deleteOnExitTemporaryFile && ret.getFileName() != null){
                new File(ret.getFileName()).delete();
            }

            logger.debug("fileUpload[}]",ret);
            return ret.isSuccess();
        }

    }

    private RetValue fileUpload(Bootstrap bootstrap, String host, int port, URI uriFile, String serverPath, File file) throws Exception {
        HttpDataFactory factory = new DefaultHttpDataFactory();
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host,port));
        Channel channel = future.sync().channel();
        HttpClientHandler handler = (HttpClientHandler) channel.pipeline().last();
        HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,uriFile.toASCIIString());
        request.headers().set(HttpHeaderNames.HOST,host);
        request.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.USER_AGENT,"httpClient");
        HttpPostRequestEncoder bodyRequestEncoder = new HttpPostRequestEncoder(factory,request,true);
        bodyRequestEncoder.addBodyFileUpload(serverPath,file,"application/octet-stream",false);
        HttpRequest httpRequest = bodyRequestEncoder.finalizeRequest();
        channel.write(httpRequest);
        if (bodyRequestEncoder.isChunked()){
            channel.writeAndFlush(bodyRequestEncoder).awaitUninterruptibly();
        }else {
            channel.flush();
        }
        bodyRequestEncoder.cleanFiles();
        channel.closeFuture().sync();
        return handler.getRet();
    }
}
