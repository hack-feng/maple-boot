package com.maple.common.model;

import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/1/21
 */
@Data
public class HttpReqInfo {
    private boolean header;
    private boolean file;
    private String param;
    private String value;
    private String fileName;
    private byte[] data;

    public HttpReqInfo(String param, String value) {
        this.header = false;
        this.file = false;
        this.param = param;
        this.value = value;
    }

    public HttpReqInfo(String param, String fileName, byte[] data) {
        this.header = false;
        this.file = true;
        this.param = param;
        this.fileName = fileName;
        this.data = data;
    }

    public HttpReqInfo(String param, String value, boolean header) {
        this.header = header;
        this.file = false;
        this.param = param;
        this.value = value;
    }
}
