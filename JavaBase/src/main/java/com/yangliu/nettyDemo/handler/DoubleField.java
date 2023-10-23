package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class DoubleField extends Field<Double> {

    private Double v;

    public DoubleField(Double v) {
        this.v = v;
    }

    @Override
    public Double getValue() {
        return v;
    }

    @Override
    public void setValue(Double o) {
        if (readOnly){
            //throw exception
        }
        v = o;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        //按照报文格式拼接字符串
        sb.append("Field { type=FieldType{Double} value{").append(v).append("} }");
    }

    @Override
    public DoubleField deepCopy() {
        return new DoubleField(this.v);
    }
}
