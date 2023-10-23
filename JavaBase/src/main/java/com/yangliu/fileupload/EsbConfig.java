package com.yangliu.fileupload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author YL
 * @date 2023/10/23
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "request.esb")
public class EsbConfig {

    private String esbFileUrl;
    private String esbAgentPort;

    private String fileUrl;
    private String workingDirectory;
}
