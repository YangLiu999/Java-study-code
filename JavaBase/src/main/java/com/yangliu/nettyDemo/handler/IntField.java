package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class IntField extends Field<Integer> {

    private Integer v;

    public IntField(Integer v) {
        this.v = v;
    }

    @Override
    public Integer getValue() {
        return v;
    }

    @Override
    public void setValue(Integer o) {
        if (readOnly){
            //throw exception
        }
        v = o;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        //按照报文格式拼接字符串
        sb.append("Field { type=FieldType{Int} value{").append(v).append("} }");
    }

    @Override
    public IntField deepCopy() {
        return new IntField(this.v);
    }
}
