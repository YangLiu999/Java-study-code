package com.yangliu.nettyDemo.tool;

/**
 * @author YL
 * @date 2021/12/27
 **/
public class BizCompenent {
    private String service;
    private String sefunc;

    public BizCompenent(String serviceClassname, String func) {
        this.service = serviceClassname;
        this.sefunc = func;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSefunc() {
        return sefunc;
    }

    public void setSefunc(String sefunc) {
        this.sefunc = sefunc;
    }
}
