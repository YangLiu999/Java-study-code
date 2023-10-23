package com.yangliu.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;


/**
 * @author YL
 * @date 2023/10/23
 **/
@Slf4j
@Component
public class FileToEsbUtil {

    @Autowired
    private EsbConfig esbConfig;

    public MyHttpClient getMyHttpClient(String host,int port){
        MyHttpClient httpClient = new MyHttpClient(host,port,null);
        return httpClient;
    }

    /**
     * upload file to esb
     * @param filepath
     * @return
     */
    public boolean fileUpload(String filepath){
        boolean result = false;
        MyHttpClient httpClient = new MyHttpClient(esbConfig.getEsbFileUrl(),
                Integer.valueOf(esbConfig.getEsbAgentPort()),null);
        result = httpClient.fileUpload(esbConfig.getFileUrl(),esbConfig.getWorkingDirectory(),filepath);
        return result;
    }

    /**
     * 写文件到本地
     * @param serverPath
     * @param data
     * @return
     */
    public String writeFileToLocalHost(String serverPath,Object data) throws IOException {
        File file = new File(serverPath);
        File fileDir = file.getParentFile();
        if (!fileDir.exists()){//directory not exist
            fileDir.mkdirs();
        }
        if (!file.exists()){//file not exist
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //文件内容拼接
        String str = "sss";
        try (
                BufferedOutputStream bu = new BufferedOutputStream(new FileOutputStream(file,false))
        ) {
            bu.write(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(str.length());
    }
}
