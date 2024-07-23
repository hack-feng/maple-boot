package com.maple.rest.controller.manage;

import com.maple.common.enums.BusinessTypeEnum;
import com.maple.common.enums.OperateTypeEnum;
import com.maple.common.model.MapleLog;
import com.maple.common.util.JwtUtil;
import com.maple.system.service.IMenuService;
import com.maple.system.service.IUserService;
import com.maple.system.vo.model.RouterModel;
import com.maple.system.vo.model.UserInfo;
import com.maple.system.vo.model.UserModel;
import com.maple.system.vo.query.LoginQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

/**
 * 系统登录
 *
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/8
 */
@Api(tags = "管理系统-系统登录操作")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/manage/sso")
public class LoginController {

    private final IUserService userService;

    private final IMenuService menuService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    @MapleLog(operateType = OperateTypeEnum.ADMIN, businessType = BusinessTypeEnum.OTHER)
    public UserModel login(@RequestBody LoginQuery req) {
        return userService.login(req);
    }

    @ApiOperation(value = "获取路由信息")
    @GetMapping("/getRouters")
    public List<RouterModel> getRouters() {
        return menuService.selectMenuTreeByUserId(JwtUtil.getUserId());
    }

    @ApiOperation(value = "获取用户信息")
    @GetMapping("/getInfo")
    public UserInfo getInfo() {
        UserModel user = userService.getUserById(JwtUtil.getUserId());
        return UserInfo.builder()
                .userModel(user)
                .permissions(menuService.selectMenuPermsByUserId(JwtUtil.getUserId()))
                .roles(new HashSet<>(JwtUtil.getRoleIdList()))
                .build();
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/logout")
    public void logout() {
        userService.logout();
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/getUserId")
    public String getUserId() {
        return "当前登录用户的ID为" + JwtUtil.getUserId();
    }
}
