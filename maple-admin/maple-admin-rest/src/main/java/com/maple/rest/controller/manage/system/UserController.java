package com.maple.rest.controller.manage.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.service.IUserService;
import com.maple.system.vo.model.UserModel;
import com.maple.system.vo.query.UserPageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/28
 */
@Api(tags = "系统管理-用户管理接口")
@RestController
@RequestMapping("/manage/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @ApiOperation(value = "获取用户列表")
    @PostMapping("/getPageList")
    public IPage<UserModel> getPageList(@RequestBody UserPageQuery req) {
        return userService.getPageList(req);
    }

    @ApiOperation(value = "根据用户ID查询用户信息")
    @GetMapping("/{id}")
    public UserModel getUserById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @ApiOperation(value = "新增用户信息")
    @PostMapping("/createUser")
    public Long createUser(@RequestBody UserModel model) {
        return userService.createUser(model);
    }

    @ApiOperation(value = "修改用户信息")
    @PostMapping("/updateUser")
    public void updateUser(@RequestBody UserModel model) {
        userService.updateUser(model);
    }

    @ApiOperation(value = "根据ID删除用户信息")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
