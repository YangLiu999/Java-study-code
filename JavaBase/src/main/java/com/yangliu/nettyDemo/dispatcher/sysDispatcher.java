package com.yangliu.nettyDemo.dispatcher;

import com.yangliu.nettyDemo.handler.CDData;
import com.yangliu.nettyDemo.handler.StringField;
import com.yangliu.nettyDemo.tool.*;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.xmlpull.mxp1.MXParser;
import org.xmlpull.v1.XmlPullParserException;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.xmlpull.v1.XmlPullParser.*;

/**
 * @author YL
 * @date 2021/12/22
 **/
public class sysDispatcher implements Dispatcher, ApplicationContextAware {

    Map<String, Flow> hanlderMapping = new HashMap<>();
    //扫描的包
    private String scanPackage;
    private ApplicationContext context;

    public Map<String, Flow> getHanlderMapping() {
        return hanlderMapping;
    }

    public void setHanlderMapping(Map<String, Flow> hanlderMapping) {
        this.hanlderMapping = hanlderMapping;
    }

    public String getScanPackage() {
        return scanPackage;
    }

    public void setScanPackage(String scanPackage) {
        this.scanPackage = scanPackage;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    @PostConstruct
    @Override
    public void init() throws Exception {
        doLoadConfigInJar();
    }

    private void doLoadConfigInJar() throws Exception{
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("flow.xml");
        doParseConfigInJar(in);
    }

    private void doParseConfigInJar(InputStream in) throws Exception{
        MXParser parser = new MXParser();
        try {
            parser.setInput(in,"UTF-8");
            //读取XML放入hadlerMapping
            int eventType;
            FlowParserContext ctx = new FlowParserContext();
            while (END_DOCUMENT != (eventType = parser.next())){
                switch (eventType){
                    case START_TAG:
                        String tagName = parser.getName();
                        if ("flow".equals(tagName)){
                            ctx.setMsgType(parser.getAttributeValue(0));
                            ctx.setMsgCode(parser.getAttributeValue(1));
                            ctx.setSrcType(parser.getAttributeValue(2));
                        }else if ("component".equals(tagName)){
                            ctx.getCompenents().add(new BizCompenent
                                    (parser.getAttributeValue(1),parser.getAttributeValue(2)));
                        }
                        break;
                    case END_TAG:
                        if ("flow".equals(parser.getName())){
                            Flow flow = new Flow();
                            flow.setMsgType(ctx.getMsgType());
                            flow.setMsgCode(ctx.getMsgCode());
                            flow.setSrcType(ctx.getSrcType());
                            flow.setCompenents((ArrayList<BizCompenent>) ctx.getCompenents());
                            ctx.setMsgType("");
                            ctx.setMsgCode("");
                            ctx.setSrcType("");
                            ctx.setCompenents(new ArrayList());
                            hanlderMapping.put(flow.getMsgType()+"_"+flow.getMsgCode()+"_"+flow.getSrcType(),flow);
                        }
                        break;
                    default:
                        break;
                }
            }

        }catch (XmlPullParserException e){

        }
    }

    @Override
    @Transactional
    public CDData doDispatcher(CDData req) {
        CDData resp = CDUtil.getRespFromRep(req);
        //根据请求的信息，去handlerMapping中找到对应的业务流，使用反射进行处理
        CDData data = req.deepCopy();//只拷贝要用的
        req.makeReadOnly();
        String srcType = ((StringField)req.mGet("SYS_HEAD.SOURCE_TYPE")).getValue();
        String msgType = ((StringField)req.mGet("SYS_HEAD.MESSAGE_TYPE")).getValue();
        String msgCode = ((StringField)req.mGet("SYS_HEAD.MESSAGE_CODE")).getValue();
        String reqUrl = msgType+"_"+msgCode+"_"+srcType;
        try {
            Iterator<Map.Entry<String,Flow>> it = hanlderMapping.entrySet().iterator();
            Flow flow = FlowFactory.getInstance();
            while (it.hasNext()){
                Map.Entry<String, Flow> entry = it.next();
                String key = entry.getKey();
                String flowMsgType = key.substring(0,key.indexOf("_"));
                String flowMsgCode = key.substring(key.indexOf("_"+1),key.lastIndexOf("_"));
                if ((StringUtils.countOccurrencesOf(entry.getKey(),srcType) > 0)
                    && flowMsgType.equals(msgType) && flowMsgCode.equals(msgCode)){
                    flow = hanlderMapping.get(entry.getKey());
                    break;
                }
            }

            if (flow.getCompenents().size() == 0){
                //throw Exception
            }

            for (BizCompenent compenent:flow.getCompenents()) {
                Object serviceObj = context.getBean(compenent.getService());
                Method method = serviceObj.getClass().getMethod(compenent.getSefunc(),CDData.class,CDData.class,CDData.class);
                method.invoke(serviceObj,req,data,resp);
            }

        }catch (Exception e){
            return doHandlerException(req,resp,e);
        }

        return resp;
    }

    private CDData doHandlerException(CDData req, CDData resp, Exception e) {
        //异常处理
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
