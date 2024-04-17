package com.maple.common.config;

/**
 * 全局常量定义
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 */
public class MapleConstants {

    private MapleConstants() {
    }

    /**
     * 状态-无效
     */
    public static final Integer STATUS_FALSE = 0;

    /**
     * 状态-有效
     */
    public static final Integer STATUS_TRUE = 1;

    /**
     * 用于mybatis-plus selectOne()方法调用，确保取出的结果只有一条，防止报错
     */
    public static final String LAST_LIMIT_1 = "LIMIT 1";

    /**
     * 请求头放token的参数
     */
    public static final String TOKEN_NAME = "Authorization";

    public static final int MAX_TEXT_LENGTH = 65000;
}