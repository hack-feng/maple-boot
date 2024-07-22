package com.maple.rest.controller.website;

import com.alibaba.fastjson.JSON;
import com.maple.common.config.GlobalConfig;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.RedisUtil;
import com.maple.website.service.IWebUserService;
import com.maple.website.vo.login.AppletAuthModel;
import com.maple.website.vo.login.LoginVo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * @author zhangfuzeng
 * @date 2023/11/23
 */
@Slf4j
@RestController
@RequestMapping("/website/sso")
@AllArgsConstructor
public class SsoController {

    private final IWebUserService userService;

    private final RedisUtil redisUtil;

    @ApiOperation("获取微信小程序授权的二维码")
    @PostMapping("/getUnlimitedQrCode")
    public AppletAuthModel getUnlimitedQrCode() {
        return userService.getUnlimitedQrCode();
    }

    @ApiOperation("检测微信小程序授权结果")
    @PostMapping("/checkAppletAuth")
    public LoginVo checkAppletAuth(@RequestBody AppletAuthModel authModel) {
        log.info(JSON.toJSONString(authModel));
        return userService.checkAppletAuth(authModel.getUniCode());
    }

    @ApiOperation("微信小程序授权结果通知")
    @PostMapping("/notifyAppletAuthResult")
    public void notifyAppletAuthResult(@RequestBody AppletAuthModel authModel) {
        userService.notifyAppletAuthResult(authModel);
    }

    @ApiOperation("退出登录")
    @GetMapping("/logout")
    public void logout() {
        Long userId = JwtUtil.getUserId();
        if (Objects.nonNull(userId)) {
            redisUtil.remove(GlobalConfig.getWebRedisUserKey(userId));
        }
    }
}
