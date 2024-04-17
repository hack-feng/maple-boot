package com.maple.common.model;

import lombok.Data;

/**
 * 统一返回信息包装类
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/8
 */
@Data
public class ResultJson {

    private static final String SUCCESS_CODE = "0000";

    /**
     * 成功失败的状态值，true：成功；false：失败
     * 这里返回编码为：“0000”，系统就认为接口成功；其他值，代表失败
     */
    private Boolean status;

    /**
     * 状态码 正确为0000
     */
    private String code;

    /**
     * 返回提示信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private Object data;

    public ResultJson() {
        this.status = true;
        this.code = SUCCESS_CODE;
        this.msg = "";
    }

    public ResultJson(Object data) {
        this.status = true;
        this.code = SUCCESS_CODE;
        this.msg = "";
        this.data = data;
    }

    public ResultJson(String code, String msg) {
        this.status = SUCCESS_CODE.equals(code);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 如果返回状态码非0000，且接口状态为成功，请使用这个
     * @param status 接口请求状态
     * @param code 返回code值
     * @param msg 返回消息
     * @param data 返回数据
     */
    public ResultJson(Boolean status, String code, String msg, Object data) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
