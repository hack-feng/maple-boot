package com.maple.rest.controller.manage.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebConfigPageQuery;
import com.maple.website.service.IWebConfigService;
import com.maple.website.vo.model.WebConfigModel;
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
@RequestMapping("/manage/manageConfig")
@AllArgsConstructor
public class ManageConfigController {
    
    private final IWebConfigService webConfigService;

    @ApiOperation(value = "分页查询网站用户列表", notes="网站用户-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebConfigModel> getPageList(@RequestBody WebConfigPageQuery query) {
        return webConfigService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站用户信息", notes="网站用户-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebConfigModel getWebConfigById(@PathVariable("id") Long id) {
        return webConfigService.getWebConfigById(id);
    }

    @ApiOperation(value = "新增网站用户数据", notes="网站用户-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebConfig")
    public Long createWebConfig(@RequestBody WebConfigModel model) {
        return webConfigService.createWebConfig(model);
    }

    @ApiOperation(value = "/修改网站用户数据", notes="网站用户-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebConfig")
    public void updateWebConfig(@RequestBody WebConfigModel model) {
        webConfigService.updateWebConfig(model);
    }

    @ApiOperation(value = "删除网站用户", notes="网站用户-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebConfig(@PathVariable("id") Long id) {
        return webConfigService.deleteWebConfig(id);
    }
}
