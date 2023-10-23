package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class FloatField extends Field<Float> {

    private Float v;

    public FloatField(Float v) {
        this.v = v;
    }

    @Override
    public Float getValue() {
        return v;
    }

    @Override
    public void setValue(Float o) {
        if (readOnly){
            //throw exception
        }
        v = o;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        //按照报文格式拼接字符串
        sb.append("Field { type=FieldType{Float} value{").append(v).append("} }");
    }

    @Override
    public FloatField deepCopy() {
        return new FloatField(this.v);
    }
}
