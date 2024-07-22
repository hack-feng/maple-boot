package com.maple.common.config;

/**
 * 全局配置
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 */
public class GlobalConfig {

    private GlobalConfig() {

    }

    /**
     * 用户储存在redis中的过期时间
     */
    public static final long EXPIRE_TIME = 60 * 60 * 12L;

    /**
     * 网页端通过小程序授权结果的过期时间
     */
    public static final long APPLET_AUTH_EXPIRE_TIME = 300;

    /**
     * 生成token的私钥
     */
    public static final String SECRET = "maple1223";

    /**
     * 管理员用户登录token保存在redis的key值
     *
     * @param account 用户登录帐号
     * @return token保存在redis的key
     */
    public static String getRedisUserKey(String account) {
        return "MAPLE_BOOT_ADMIN:" + account;
    }

    /**
     * 小程序用户登录token保存在redis的key值
     *
     * @param account 用户登录帐号
     * @return token保存在redis的key
     */
    public static String getAppletRedisUserKey(String account) {
        return "MAPLE_APPLET:" + account;
    }


    /**
     * web用户登录token保存在redis的key值
     *
     * @param account 用户登录帐号
     * @return token保存在redis的key
     */
    public static String getWebRedisUserKey(Long account) {
        return "MAPLE_BOOT_WEB:" + account;
    }

    /**
     * 网页端通过小程序授权编码保存在redis的key值
     *
     * @param uniCode 唯一编码
     * @return 保存在redis的key
     */
    public static String getAppletAuthRedisKey(String uniCode) {
        return "MAPLE_APPLET_AUTH:" + uniCode;
    }

    /**
     * 网页端通过小程序授权结果保存在redis的key值
     *
     * @param uniCode 唯一编码
     * @return 保存在redis的key
     */
    public static String getAppletAuthResultRedisKey(String uniCode) {
        return "MAPLE_APPLET_AUTH_RESULT:" + uniCode;
    }

    /**
     * 前端传递token的header名称
     */
    public static final String TOKEN_NAME = "Authorization";

    public static final Long SIGN_MAPLE_NUM = 20L;
}
