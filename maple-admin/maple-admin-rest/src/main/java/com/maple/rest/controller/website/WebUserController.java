package com.maple.rest.controller.website;

import com.maple.common.enums.BusinessTypeEnum;
import com.maple.common.enums.OperateTypeEnum;
import com.maple.common.model.MapleLog;
import com.maple.website.service.IWebUserService;
import com.maple.website.vo.model.WebUserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网站用户  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站用户")
@RestController
@RequestMapping("/website/webUser")
@AllArgsConstructor
public class WebUserController {

    private final IWebUserService userService;

    @ApiOperation(value = "修改用户昵称头像信息")
    @PostMapping("/updateUser")
    @MapleLog(operateType = OperateTypeEnum.BLOG, businessType = BusinessTypeEnum.UPDATE)
    public WebUserModel updateUser(@RequestBody WebUserModel userVo) {
        return userService.updateWebUser(userVo);
    }

    @ApiOperation(value = "获取用户信息")
    @PostMapping("/getUserInfo")
    public WebUserModel getUserInfo() {
        return userService.getUserInfo();
    }
}
