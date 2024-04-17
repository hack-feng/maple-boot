package com.maple.common.config.exception;

/**
 * 检测结果不一致时，抛出此异常
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/8
 */
public class MapleCheckException extends MapleBaseException {

    public MapleCheckException(String code, String errorMsg) {
        super(code, errorMsg);
    }

    public MapleCheckException(ErrorCode code) {
        super(code);
    }

    public MapleCheckException(ErrorCode code, String errorMsg) {
        super(code, errorMsg);
    }
}
