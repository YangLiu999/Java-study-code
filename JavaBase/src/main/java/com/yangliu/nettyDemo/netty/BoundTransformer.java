package com.yangliu.nettyDemo.netty;


import com.yangliu.nettyDemo.handler.CDData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author YL
 * @date 2022/02/16
 **/
public interface BoundTransformer {
    void cdDdata2Byte(CDData req, OutputStream os) throws IOException;
    CDData byte2CDData(InputStream is) throws IOException;
}
