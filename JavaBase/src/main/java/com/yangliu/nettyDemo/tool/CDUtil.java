package com.yangliu.nettyDemo.tool;

import cn.hutool.core.util.StrUtil;
import com.yangliu.nettyDemo.handler.*;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.*;

import java.util.List;

/**
 * @author YL
 * @date 2021/12/22
 **/
public class CDUtil {
    private static final Base64 BASE64 = new Base64();

    public static CDData getRespFromRep(CDData req) {
        CDData resp = new CDData();
        //根据约定的报文获取信息
        CDData syshead = (CDData) req.get("SYS_HEAD");
        if (syshead != null){

        }
        CDData apphead = (CDData) req.get("APP_HEAD");
        if (apphead != null){

        }
        return resp;
    }

    public static CDData fromXml(String xml) {
        try {
            Document document = DocumentHelper.parseText(xml);
            return fromDoc(document);
        } catch (DocumentException e) {
            e.printStackTrace();
            //抛出自定义异常
            try {
                throw new DocumentException(e);
            } catch (DocumentException documentException) {
                documentException.printStackTrace();
            }
        }
        return null;
    }

    private static CDData fromDoc(Document document) {
        CDData rt = new CDData();
        //解析报文
        Element root = document.getRootElement();
        Element sysHeader = root.element("sys-head");
        if (sysHeader != null){
            Element data = sysHeader.element("data");
            if (data != null){
                Element struct = data.element("struct");
                if (struct != null){
                    rt.put("SYS_HEAD",fromStruct(struct));
                }
            }
        }

        Element appHeader = root.element("app-head");
        if (appHeader != null){
            Element data = appHeader.element("data");
            if (data != null){
                Element struct = data.element("struct");
                if (struct != null){
                    rt.put("APP_HEAD",fromStruct(struct));
                }
            }
        }

        Element localHeader = root.element("local-head");
        if (localHeader != null){
            Element data = localHeader.element("data");
            if (data != null){
                Element struct = data.element("struct");
                if (struct != null){
                    rt.put("LOCAL_HEAD",fromStruct(struct));
                }
            }
        }

        Element body = root.element("body");
        if (body != null){
            for (Object sub:body.elements()){
                Element subele = (Element) sub;
                if (!StrUtil.equals("data",subele.getName())){
                    //throws exception
                }
                String name = subele.attributeValue("name");
                if (StrUtil.isEmpty(name)){
                    //throws exception
                }
                if (name.equals("SYS_HEAD") ||name.equals("APP_HEAD") || name.equals("LOCAL_HEAD")){
                    continue;
                }
                List datasubs = subele.elements();
                if (datasubs.size() != 1){
                    //throws exception
                }
                Element datasub = (Element) datasubs.get(0);
                parseRecursive(rt,name,datasub);
            }
        }
        return rt;
    }

    private static void parseRecursive(CDData rt, String name, Element datasub) {
        if (StrUtil.equals("field",datasub.getName())){
            rt.put("name",fromField(datasub));
        }else if (StrUtil.equals("array",datasub.getName())){
            rt.put("array",fromArray(datasub));
        }else if (StrUtil.equals("struct",datasub.getName())){
            rt.put("struct",fromStruct(datasub));
        }else {
            //throws exception
        }
    }

    private static Array fromArray(Element array) {
        /*Array arr = new Array();
        if (Object o :array.elements()){
            Element datasub =(Element) o;
            if (StrUtil.equals("field",datasub.getName())){
                arr.add("name",fromField(datasub));
            }else if (StrUtil.equals("array",datasub.getName())){
                arr.add("array",fromArray(datasub));
            }else if (StrUtil.equals("struct",datasub.getName())){
                arr.add("struct",fromStruct(datasub));
            }else if{
                //throws exception
            }
        }*/
        return null;
    }

    private static Field fromField(Element field) {
        String type = field.attributeValue("type");
        if (StrUtil.equals("int",type)){
            String txt = null;
            if (field.hasContent()){
                txt = field.getText();
            }
            Integer v;
            if (txt == null){
                v = null;
            }else {
                try {
                    v = Integer.valueOf(txt);
                }catch (NumberFormatException e){
                    //throw Exception
                    throw new RuntimeException();
                }
            }
            return new IntField(v);
        }if (StrUtil.equals("long",type)){
            String txt = null;
            if (field.hasContent()){
                txt = field.getText();
            }
            Long v;
            if (txt == null){
                v = null;
            }else {
                try {
                    v = Long.valueOf(txt);
                }catch (NumberFormatException e){
                    //throw Exception
                    throw new RuntimeException();
                }
            }
            return new LongField(v);
        }if (StrUtil.equals("double",type)){
            String txt = null;
            if (field.hasContent()){
                txt = field.getText();
            }
            Double v;
            if (txt == null){
                v = null;
            }else {
                try {
                    v = Double.valueOf(txt);
                }catch (NumberFormatException e){
                    //throw Exception
                    throw new RuntimeException();
                }
            }
            return new DoubleField(v);
        }if (StrUtil.equals("string",type)){
            String txt = null;
            if (field.hasContent()){
                txt = field.getText();
            }
            String v;
            if (txt == null){
                v = null;
            }else {
                try {
                    v = txt;
                }catch (NumberFormatException e){
                    //throw Exception
                    throw new RuntimeException();
                }
            }
            return new com.yangliu.nettyDemo.handler.StringField(v);
        }if (StrUtil.equals("image",type)){
            String txt = null;
            if (field.hasContent()){
                txt = field.getText();
            }
            byte[] v;
            if (txt == null){
                v = null;
            }else {
                try {
                    v = BASE64.decode(txt);
                }catch (NumberFormatException e){
                    //throw Exception
                    throw new RuntimeException();
                }
            }
            return new com.yangliu.nettyDemo.handler.ImageField(v);
        }else {
            //throw exception
            throw new RuntimeException();
        }

    }

    private static CDData fromStruct(Element struct) {
        CDData cd = new CDData();
        for (Object sub:struct.elements()){
            Element subele = (Element) sub;
            if (!StrUtil.equals("data",subele.getName())){
                //throws exception
            }
            String name = subele.attributeValue("name");
            if (StrUtil.isEmpty(name)){
                //throws exception
            }
            if (name.equals("SYS_HEAD") ||name.equals("APP_HEAD") || name.equals("LOCAL_HEAD")){
                continue;
            }
            List datasubs = subele.elements();
            if (datasubs.size() != 1){
                //throws exception
            }
            Element datasub = (Element) datasubs.get(0);
            parseRecursive(cd,name,datasub);
        }
        return cd;
    }

    public static String toXml(CDData cd) {
        return toXml(cd,false);
    }

    private static String toXml(CDData cd, boolean b) {
        Document doc = toDoc(cd);

        return doc.asXML();
    }

    private static Document toDoc(CDData cd) {
        Document doc = DocumentFactory.getInstance().createDocument();
        doc.setXMLEncoding("utf-8");


        return doc;
    }
}
