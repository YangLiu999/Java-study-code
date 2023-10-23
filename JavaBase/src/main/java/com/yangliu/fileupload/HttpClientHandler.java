package com.yangliu.fileupload;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author YL
 * @date 2023/10/23
 **/
public class HttpClientHandler extends SimpleChannelInboundHandler<HttpObject> {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientHandler.class);
    private RetValue ret;
    private Boolean readingChucks;
    private File file = null;
    private FileOutputStream fos = null;
    private int succCode;
    private CharSequence charsetName;
    private Charset charset;
    private StringBuilder response = new StringBuilder();
    private File tmpDir = null;

    public HttpClientHandler(String tmpDir){
        if (tmpDir != null){
            this.tmpDir = new File(tmpDir);
            if (!this.tmpDir.exists() && !this.tmpDir.mkdirs()){
                logger.warn("tmpDir[{}] is not exist,use system temp dir instead");
                this.tmpDir = null;
            }
        }
    }

    protected void channelRead0(ChannelHandlerContext ctx,
                                HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse){
            HttpResponse response = (HttpResponse) msg;
            logger.debug("Response:[\n{}]",response);
            if (!response.headers().isEmpty()){
                Iterator i$ = response.headers().names().iterator();
                while (i$.hasNext()){
                    String name = (String) i$.next();
                    Iterator m = response.headers().getAll(name).iterator();

                    while (m.hasNext()){
                        String value = (String) m.next();
                        logger.debug("HEADER:{}={}",name,value);
                    }

                }
            }

            this.succCode = response.status().code();
            this.charsetName = HttpUtil.getCharsetAsString(response);
            if (this.charsetName != null){
                this.charset = Charset.forName(this.charsetName.toString());
            }
            if (succCode == 200){
                this.ret.setSuccess(true);
                if (this.charsetName == null){
                    this.file = File.createTempFile("http",".tmp",this.tmpDir);
                    this.fos = new FileOutputStream(this.file);
                }
            }else {
                this.ret.setSuccess(false);
            }

            if (HttpUtil.isTransferEncodingChunked(response)){
                this.readingChucks = true;
                logger.debug("CHUNKED CONTENT BEGIN :");
            }else {
                this.readingChucks = false;
                logger.debug("CONTENT BEGIN :");
            }
        }

        if (msg instanceof HttpContent){
            HttpContent chunk = (HttpContent) msg;
            ByteBuf buffer = chunk.content();
            if (this.charsetName != null){
                this.response.append(buffer.toString(this.charset));
            }else {
                logger.debug("HttpContent:length={}",buffer.readableBytes());
                if (this.succCode == 200 && this.fos != null){
                    byte[] dst = new byte[buffer.readableBytes()];

                    while (buffer.isReadable()){
                        buffer.readBytes(dst);
                        this.fos.write(dst);
                    }
                    if (null != this.fos){
                        this.fos.flush();
                    }
                }
            }

            if (chunk instanceof LastHttpContent){
                if (this.charsetName != null){
                    this.ret.setResponse(this.response.toString());
                    logger.debug("ret:[{}]",this.ret);
                }else if (this.fos != null){
                    this.ret.setFileName(this.file.getAbsolutePath());
                    logger.debug("ret:[{}]",this.ret);
                    this.fos.flush();
                    this.fos.close();
                    this.file = null;
                    this.fos = null;
                }

                if (this.readingChucks){
                    logger.debug("END OF CHUNKED CONTENT");
                }else {
                    logger.debug("END OF CONTENT");
                }

                this.readingChucks = false;
            }
        }

    }


    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.trace("channelActive");
        super.channelActive(ctx);
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.trace("channelInactive");
        super.channelInactive(ctx);
    }

    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        logger.trace("exceptionCaught",cause);
        ctx.channel().close();
    }

    public RetValue getRet(){
        return this.ret;
    }
}
