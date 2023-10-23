package com.yangliu.nettyDemo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

/**
 * @author YL
 * @date 2021/12/28
 **/
public class NioKit {
    public static ByteBuf getOutBytes(byte[] resp) {
        int len = resp.length;
        String slen = String.valueOf(len);
        if (slen.length() > 8){
            return null;
        }else {
            try {
                ByteBuf out = Unpooled.buffer(len + 8);
                int loopcnt = 8 - slen.length();
                for (int i = 0; i < loopcnt; i++) {
                    out.writeBytes("0".getBytes("utf-8"));
                }
                out.writeBytes(slen.getBytes("utf-8"));
                out.writeBytes(resp);
                return out;
            }catch (UnsupportedEncodingException e){
                return null;
            }
        }
    }
}
