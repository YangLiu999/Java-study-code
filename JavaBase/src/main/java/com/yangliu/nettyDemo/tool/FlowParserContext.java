package com.yangliu.nettyDemo.tool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YL
 * @date 2021/12/28
 **/
public class FlowParserContext<E>{
    private String msgType;
    private String msgCode;
    private String srcType;
    private List<E> compenents = new ArrayList<E>();

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getSrcType() {
        return srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public List<E> getCompenents() {
        return compenents;
    }

    public void setCompenents(List<E> compenents) {
        this.compenents = compenents;
    }
}
