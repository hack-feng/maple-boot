package com.maple.website.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangfuzeng
 * @date 2024/7/17
 */
@Data
@Configuration
public class WechatConfig {

    @Value("${wechat.appId}")
    private String appId;

    @Value("${wechat.appSecret}")
    private String appSecret;

    @Value("${wechat.jscode2sessionUrl}")
    private String jscode2sessionUrl;

    @Value("${wechat.tokenUrl}")
    private String tokenUrl;

    @Value("${wechat.unlimitedQRCodeUrl}")
    private String unlimitedQrCodeUrl;
}
