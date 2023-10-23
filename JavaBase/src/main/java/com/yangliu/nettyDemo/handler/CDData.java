package com.yangliu.nettyDemo.handler;

import cn.hutool.core.util.StrUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author YL
 * @date 2021/12/22
 **/
public final class CDData extends AtomData implements Map<String, AtomData> {
    private final  static int POINTCODE;
    private final  static int LPCODE;
    private final  static int RPCODE;

    static {
        POINTCODE = ".".codePointAt(0);
        LPCODE = "[".codePointAt(0);
        RPCODE = "]".codePointAt(0);
    }

    private Map<String, AtomData> data;

    public CDData() {
        this.data = new HashMap<>();
    }

    @Override
    public void makeReadOnly() {
        this.readOnly = true;
        this.data.values().stream().filter(d -> d != null).forEach(AtomData::makeReadOnly);
    }

    /**
     *
     * @param fullName a.b.c,a[i].b.c
     * @return null or AtomData,no exception throw up
     */
    @Override
    public AtomData mGet(String fullName) {
        if (StrUtil.isEmpty(fullName) || "".equals(fullName.trim())){
            return null;
        }
        fullName = fullName.trim();
        if (fullName.codePointAt(0) == POINTCODE || fullName.codePointAt(0) == LPCODE){
            return null;
        }
        int pi = fullName.indexOf(POINTCODE);
        int li = fullName.indexOf(LPCODE);
        if (pi == -1 || li == -1){
            return this.get(fullName);
        }else if ((pi > 0 && pi <li) || (pi > 0 && li == -1)){
            String name = fullName.substring(0,pi);
            String leftName = fullName.substring(pi+1);
            AtomData par = this.get(name);
            if (par == null || !(par instanceof CDData)){
                return null;
            }
            return par.mGet(leftName);
        }else if ((li > 0 && li < pi) || (li > 0 && pi == -1)){
            int ri = fullName.indexOf(RPCODE);
            if (li > pi){
                return null;
            }
            String name = fullName.substring(0,li);
            String leftName = fullName.substring(li);
            AtomData par = this.get(name);
            if (par == null || !(par instanceof CDData)){
                return null;
            }
            return par.mGet(leftName);
        }
        return null;
    }

    @Override
    public AtomData setMutiLevel(String fullName, boolean isCD) {
        return null;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {

    }

    @Override
    public CDData deepCopy() {
        CDData cp = new CDData();
        for (Entry<String, AtomData> entry:this.data.entrySet()) {
            if (entry.getValue() != null){
                cp.put(entry.getKey(),entry.getValue().deepCopy());
            }else {
                cp.put(entry.getKey(),null);
            }
        }
        return cp;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public AtomData get(Object key) {
        return data.get(key);
    }

    @Override
    public AtomData put(String key, AtomData value) {
        return data.put(key,value);
    }

    @Override
    public AtomData remove(Object key) {
        return data.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends AtomData> m) {
        if (readOnly){
            //throw exception
        }
        data.putAll(m);
    }

    @Override
    public void clear() {
        if (readOnly){
            //throw exception
        }
        data.clear();
    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<AtomData> values() {
        return null;
    }

    @Override
    public Set<Entry<String, AtomData>> entrySet() {
        return null;
    }
}
