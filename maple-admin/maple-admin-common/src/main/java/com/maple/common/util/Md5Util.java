package com.maple.common.util;

import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;


/**
 * MD5撒盐加密 及MD5加密
 *
 * @author bzy
 * @date 2019/12/5 09:46
 */
@Slf4j
public class Md5Util {

    private Md5Util() {

    }

    /**
     * 密码加密处理
     *
     * @param password 密码明文
     * @param salt     盐
     * @return 加密后密文
     */
    public static String encrypt(String password, String salt) {
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(salt)) {
            log.error("密码加密失败原因： password and salt cannot be empty");
            throw new MapleCommonException(ErrorCode.PARAM_ERROR);
        }
        return DigestUtils.md5DigestAsHex((salt + password).getBytes());
    }

    /**
     * 校验密码
     *
     * @param target 待校验密码
     * @param source 原密码
     * @param salt   加密原密码的盐
     */
    public static boolean verifyPassword(String target, String source, String salt) {
        if (StringUtils.isEmpty(target) || StringUtils.isEmpty(source) || StringUtils.isEmpty(salt)) {
            log.info("校验密码失败，原因 target ={}， source ={}， salt ={}", target, source, salt);
            return false;
        }
        String targetEncryptPwd = encrypt(target, salt);
        return targetEncryptPwd.equals(source);
    }
}
