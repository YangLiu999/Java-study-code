package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class LongField extends Field<Long> {

    private Long v;

    public LongField(Long v) {
        this.v = v;
    }

    @Override
    public Long getValue() {
        return v;
    }

    @Override
    public void setValue(Long o) {
        if (readOnly){
            //throw exception
        }
        v = o;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        //按照报文格式拼接字符串
        sb.append("Field { type=FieldType{Long} value{").append(v).append("} }");
    }

    @Override
    public LongField deepCopy() {
        return new LongField(this.v);
    }
}
