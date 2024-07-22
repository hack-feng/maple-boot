package com.maple.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.GlobalConfig;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.model.TokenBean;
import com.maple.common.util.*;
import com.maple.website.bean.WebUser;
import com.maple.website.config.WechatConfig;
import com.maple.website.mapper.WebUserMapper;
import com.maple.website.service.IWebUserService;
import com.maple.website.vo.login.AppletAuthModel;
import com.maple.website.vo.login.LoginVo;
import com.maple.website.vo.model.WebUserModel;
import com.maple.website.vo.query.WebUserPageQuery;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.maple.common.config.MapleConstants.LAST_LIMIT_1;

/**
 * 网站用户Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebUserServiceImpl extends ServiceImpl<WebUserMapper, WebUser> implements IWebUserService {

    private final WebUserMapper webUserMapper;

    private final RedisUtil redisUtil;

    private final WechatConfig wechatConfig;

    @Override
    public IPage<WebUserModel> getPageList(WebUserPageQuery query) {
        return webUserMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebUserModel getWebUserById(Long id) {
        return TransformUtils.map(webUserMapper.selectById(id), WebUserModel.class);
    }

    @Override
    public Long createWebUser(WebUserModel model) {
        WebUser webUser = TransformUtils.map(model, WebUser.class);
        webUser.setStatus(1);
        webUser.setInviterUserId(1L);
        webUser.setUserNo(getUserNo());
        webUserMapper.insert(webUser);
        return webUser.getId();
    }

    @Override
    public void updateWebUser(WebUserModel model) {
        webUserMapper.updateById(TransformUtils.map(model, WebUser.class));
    }

    @Override
    public Integer deleteWebUser(Long id) {
        return webUserMapper.deleteById(id);
    }


    @Override
    public AppletAuthModel getUnlimitedQrCode() {
        String scene = UUID.randomUUID().toString().replace("-", "");

        redisUtil.set(GlobalConfig.getAppletAuthRedisKey(scene), false, GlobalConfig.APPLET_AUTH_EXPIRE_TIME);
        AppletAuthModel authModel = new AppletAuthModel();
        authModel.setAuthToken(getAppletToken());
        authModel.setUniCode(scene);
        return authModel;
    }

    @Override
    public LoginVo checkAppletAuth(String uniCode) {
        LoginVo result = new LoginVo();
        result.setAuthResult(false);
        Boolean isAuth = (Boolean) redisUtil.get(GlobalConfig.getAppletAuthRedisKey(uniCode));
        if (BooleanUtils.isTrue(isAuth)) {
            String appletToken = (String) redisUtil.get(GlobalConfig.getAppletAuthResultRedisKey(uniCode));
            String openId = JwtUtil.getOpenId(appletToken);
            WebUser appletUser = webUserMapper.selectOne(Wrappers.lambdaQuery(WebUser.class).eq(WebUser::getOpenid, openId).last(LAST_LIMIT_1));
            if (Objects.nonNull(appletUser)) {
                result.setNickName(appletUser.getNickName());
                result.setHeadPortraitLink(appletUser.getHeadPortraitLink());
            } else {
                appletUser = new WebUser();
                appletUser.setOpenid(openId);
                appletUser.setUserNo(getUserNo());
                appletUser.setNickName("笑小枫_" + RandomUtils.nextInt(1000, 9999));
                webUserMapper.insert(appletUser);
            }

            String token;
            try {
                TokenBean tokenBean = new TokenBean();
                tokenBean.setUserId(appletUser.getId());
                tokenBean.setOpenId(openId);
                token = JwtUtil.createToken(tokenBean);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new MapleCommonException(ErrorCode.COMMON_ERROR);
            }
            result.setToken(token);
            result.setAuthResult(true);
            redisUtil.set(GlobalConfig.getWebRedisUserKey(appletUser.getId()), token, GlobalConfig.EXPIRE_TIME);
        }
        return result;
    }

    @Override
    public void notifyAppletAuthResult(AppletAuthModel authModel) {
        if (BooleanUtils.isTrue(authModel.getAuthResult())) {
            // 保存小程序的登录token
            redisUtil.set(GlobalConfig.getAppletAuthResultRedisKey(authModel.getUniCode()), authModel.getToken(), GlobalConfig.APPLET_AUTH_EXPIRE_TIME);
            // 刷新小程序授权登录状态
            redisUtil.set(GlobalConfig.getAppletAuthRedisKey(authModel.getUniCode()), true, GlobalConfig.APPLET_AUTH_EXPIRE_TIME);
        }
    }

    private String getUserNo() {
        int retryMax = 10;
        while (retryMax-- > 0) {
            String userNo = "XF" + DateUtil.dateToYyMmDd(new Date()) + RandomUtils.nextInt(10000, 99999);
            long count = webUserMapper.selectCount(Wrappers.lambdaQuery(WebUser.class).eq(WebUser::getUserNo, userNo));
            if (count == 0) {
                return userNo;
            }
            log.warn(userNo + "已被使用，尝试生成其它编号");
        }
        throw new MapleCommonException(ErrorCode.COMMON_ERROR.getCode(), "生成用户信息失败, 请重试");
    }


    private String getAppletToken() {
        StringBuilder paramBuffer = new StringBuilder();
        paramBuffer.append(wechatConfig.getTokenUrl())
                .append("?appid=").append(wechatConfig.getAppId())
                .append("&secret=").append(wechatConfig.getAppSecret())
                .append("&grant_type=client_credential");
        String token;
        try {
            String wechatValid = HttpClientUtil.sendGet(paramBuffer.toString(), null);
            JSONObject wechatRes = JSON.parseObject(wechatValid);
            token = wechatRes.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "微信接口请求失败");
        }
        if (StringUtils.isEmpty(token)) {
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "微信登录凭证校验失败");
        }
        return token;
    }


}
