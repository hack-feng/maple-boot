package com.maple.common.config.exception;

/**
 * 异常类型不好归类时，抛出此通用异常
 * 如异常可以明确归类，不建议使用该异常抛出
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/8
 */
public class MapleCommonException extends MapleBaseException {

    public MapleCommonException(String code, String errorMsg) {
        super(code, errorMsg);
    }

    public MapleCommonException(ErrorCode code) {
        super(code);
    }

    public MapleCommonException(ErrorCode code, String errorMsg) {
        super(code, errorMsg);
    }
}
