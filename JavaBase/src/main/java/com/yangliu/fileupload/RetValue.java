package com.yangliu.fileupload;

import lombok.Data;

/**
 * @author YL
 * @date 2023/10/23
 **/
@Data
public class RetValue {
    private boolean success;
    private String fileName;
    private String response;
}
