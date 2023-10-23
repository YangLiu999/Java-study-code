package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/22
 **/
public abstract class AtomData {
    protected boolean readOnly;
    public abstract void makeReadOnly();
    public abstract AtomData mGet(String fullName);
    public abstract AtomData setMutiLevel(String fullName,boolean isCD);
    public abstract void toString(StringBuilder sb,String tab,int tabcnt);
    public abstract <T extends AtomData> T deepCopy();
}
