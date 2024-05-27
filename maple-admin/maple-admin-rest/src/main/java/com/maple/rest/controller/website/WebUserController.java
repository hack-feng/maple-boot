package com.maple.rest.controller.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserPageQuery;
import com.maple.website.service.IWebUserService;
import com.maple.website.vo.model.WebUserModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站用户  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站用户")
@RestController
@RequestMapping("/manage/website/webUser")
@AllArgsConstructor
public class WebUserController {
    
    private final IWebUserService webUserService;

    @ApiOperation(value = "分页查询网站用户列表", notes="网站用户-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebUserModel> getPageList(@RequestBody WebUserPageQuery query) {
        return webUserService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站用户信息", notes="网站用户-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebUserModel getWebUserById(@PathVariable("id") Long id) {
        return webUserService.getWebUserById(id);
    }

    @ApiOperation(value = "新增网站用户数据", notes="网站用户-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebUser")
    public Long createWebUser(@RequestBody WebUserModel model) {
        return webUserService.createWebUser(model);
    }

    @ApiOperation(value = "修改网站用户数据", notes="网站用户-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebUser")
    public void updateWebUser(@RequestBody WebUserModel model) {
        webUserService.updateWebUser(model);
    }

    @ApiOperation(value = "删除网站用户", notes="网站用户-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebUser(@PathVariable("id") Long id) {
        return webUserService.deleteWebUser(id);
    }
}
