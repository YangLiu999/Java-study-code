package com.yangliu.nettyDemo.tool;

import java.util.ArrayList;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class Flow {
    private String msgType;
    private String msgCode;
    private String srcType;
    private ArrayList<BizCompenent> compenents = new ArrayList<>();

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

    public ArrayList<BizCompenent> getCompenents() {
        return compenents;
    }

    public void setCompenents(ArrayList<BizCompenent> compenents) {
        this.compenents = compenents;
    }
}
