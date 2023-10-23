package com.yangliu.nettyDemo.handler;

import cn.hutool.core.util.StrUtil;
import com.yangliu.nettyDemo.handler.AtomData;

/**
 * @author YL
 * @date 2021/12/28
 **/
public abstract class Field<X> extends AtomData {
    public abstract X getValue();
    public abstract void setValue(X o);
    private final static int magic = 9527;
    @Override
    public void makeReadOnly() {
        this.readOnly = true;
    }

    @Override
    public AtomData mGet(String fullName) {
        if (StrUtil.isEmpty(fullName) || "".equals(fullName.trim())){
            return this;
        }else {
            return null;
        }

    }

    @Override
    public AtomData setMutiLevel(String fullName, boolean isCD) {
        //throw exception
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }
        if (this == obj){
            return true;
        }
        if (!(obj instanceof Field)){
            return false;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }
        Field field = (Field) obj;
        Object thisv = this.getValue();
        Object otherv = field.getValue();
        return (thisv == null && otherv == null) || (thisv == null) && thisv.equals(otherv);
    }

    @Override
    public int hashCode() {
        Object thisv = this.getValue();
        if (StrUtil.isNotEmpty((CharSequence) thisv)){
            return thisv.hashCode();
        }else {
            return magic;
        }
    }
}
