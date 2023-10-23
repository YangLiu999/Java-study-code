package com.yangliu.nettyDemo.handler;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class ImageField extends Field {

    private static Class byteArray;

    static {
        byteArray = (new byte[0]).getClass();
    }

    private byte[] v;

    public ImageField(byte[] v) {
        this.v = v;
    }

    @Override
    public Object getValue() {
        if (readOnly){
            if (v != null){
                byte[] cp = new byte[v.length];
                System.arraycopy(v,0,cp,0,v.length);
                return cp;
            }
        }
        return v;
    }

    @Override
    public void setValue(Object o) {
        if (readOnly){
            //throw exception
        }
        if (o == null){
            v= null;
        }else if (o.getClass() == byteArray){
            v= (byte[])o;
        }else {
            throw new RuntimeException();
        }

    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {
        //按照报文格式拼接字符串
        sb.append("Field { type=FieldType{image} value{").append(v).append("} }");
    }

    @Override
    public ImageField deepCopy() {
        byte[] cpv = new byte[v.length];
        System.arraycopy(v,0,cpv,0,v.length);
        return new ImageField(cpv);
    }
}
