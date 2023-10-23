package com.yangliu.nettyDemo.netty;

import com.yangliu.nettyDemo.tool.CDUtil;
import com.yangliu.nettyDemo.handler.CDData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据转化
 * @author YL
 * @date 2022/02/16
 **/
public class ESBTransformer implements com.yangliu.nettyDemo.netty.BoundTransformer {

    @Override
    public void cdDdata2Byte(CDData req, OutputStream os) throws IOException {
        String xml = CDUtil.toXml(req);
        int length = xml.getBytes().length;
        os.write((lpadZero(length) + xml).getBytes());
    }

    private static String lpadZero(int length) {
        return String.format("%08d",length);
    }

    @Override
    public CDData byte2CDData(InputStream is) throws IOException {
        byte[] buf = new byte[8];
        if (0 >= is.read(buf,0,8)) {
            return null;
        }
        int msglen = Integer.valueOf(new String(buf));

        if (0 >= msglen) return null;
        byte[] content = new byte[msglen];
        readFromInputStream(is,content,0,msglen);
        String contentStr = new String(content,"utf-8");
        return CDUtil.fromXml(contentStr);

    }

    private void readFromInputStream(InputStream is, byte[] readBuffer
                , int offest, int len) throws IOException{
        int readlen = is.read(readBuffer,offest,len);
        if (0 <= len && -1 ==readlen){
            throw  new IOException("cant read byte anymore");
        }
        else if (readlen != len){
            readFromInputStream(is,readBuffer,offest+readlen,len-readlen);
        }
    }
}
