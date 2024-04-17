package com.maple.common.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常-Base父类，细化的自定义异常，应该继承此类
 * 统一异常处理时，会根据此异常类型做判断，返回结果时，如果是自定义异常自动解析code和errorMsg返回
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MapleBaseException extends RuntimeException {

    private final String code;

    private final String errorMsg;

    public MapleBaseException(String code, String errorMsg) {
        super(errorMsg);
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public MapleBaseException(ErrorCode code) {
        super(code.getMsg());
        this.code = code.getCode();
        this.errorMsg = code.getMsg();
    }

    public MapleBaseException(ErrorCode code, String errorMsg) {
        super(errorMsg);
        this.code = code.getCode();
        this.errorMsg = errorMsg;
    }
}
