package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class StringField extends Field<String> {

    private String v;

    public StringField(String v) {
        this.v = v;
    }

    @Override
    public String getValue() {
        return v;
    }

    @Override
    public void setValue(String o) {
        if (readOnly){
            //throw exception
        }
        v = o;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        //按照报文格式拼接字符串
        sb.append("Field { type=FieldType{string} value{").append(v).append("} }");
    }

    @Override
    public StringField deepCopy() {
        return new StringField(this.v);
    }
}
