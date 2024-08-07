package com.maple.rest.controller.manage.website;

import java.util.List;
import com.maple.website.service.IWebMenuService;
import com.maple.website.vo.model.WebMenuModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站菜单  前端控制器
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
@Api(tags = "网站菜单")
@RestController
@RequestMapping("/manage/manageWebMenu")
@AllArgsConstructor
public class ManageWebMenuController {

    private final IWebMenuService webMenuService;

    @ApiOperation(value = "查询网站菜单树结构", notes="网站菜单-查询树结构", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getTreeList")
    public List<WebMenuModel> getTreeList(@RequestBody WebMenuModel model) {
        return webMenuService.getTreeList(model);
    }

    @ApiOperation(value = "根据id查询网站菜单信息", notes="网站菜单-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebMenuModel getWebMenuById(@PathVariable("id") Long id) {
        return webMenuService.getWebMenuById(id);
    }

    @ApiOperation(value = "新增网站菜单数据", notes="网站菜单-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebMenu")
    public Long createWebMenu(@RequestBody WebMenuModel model) {
        return webMenuService.createWebMenu(model);
    }

    @ApiOperation(value = "修改网站菜单数据", notes="网站菜单-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebMenu")
    public void updateWebMenu(@RequestBody WebMenuModel model) {
        webMenuService.updateWebMenu(model);
    }

    @ApiOperation(value = "删除网站菜单", notes="网站菜单-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebMenu(@PathVariable("id") Long id) {
        return webMenuService.deleteWebMenu(id);
    }
}
