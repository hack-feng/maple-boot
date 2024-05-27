package com.maple.rest.controller.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebResourcePageQuery;
import com.maple.website.service.IWebResourceService;
import com.maple.website.vo.model.WebResourceModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站资源  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站资源")
@RestController
@RequestMapping("/manage/website/webResource")
@AllArgsConstructor
public class WebResourceController {
    
    private final IWebResourceService webResourceService;

    @ApiOperation(value = "分页查询网站资源列表", notes="网站资源-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebResourceModel> getPageList(@RequestBody WebResourcePageQuery query) {
        return webResourceService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站资源信息", notes="网站资源-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebResourceModel getWebResourceById(@PathVariable("id") Long id) {
        return webResourceService.getWebResourceById(id);
    }

    @ApiOperation(value = "新增网站资源数据", notes="网站资源-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebResource")
    public Long createWebResource(@RequestBody WebResourceModel model) {
        return webResourceService.createWebResource(model);
    }

    @ApiOperation(value = "修改网站资源数据", notes="网站资源-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebResource")
    public void updateWebResource(@RequestBody WebResourceModel model) {
        webResourceService.updateWebResource(model);
    }

    @ApiOperation(value = "删除网站资源", notes="网站资源-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebResource(@PathVariable("id") Long id) {
        return webResourceService.deleteWebResource(id);
    }
}
