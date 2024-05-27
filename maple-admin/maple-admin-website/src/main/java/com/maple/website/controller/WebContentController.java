package com.maple.website.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebContentPageQuery;
import com.maple.website.service.IWebContentService;
import com.maple.website.vo.model.WebContentModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站文章内容  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站文章内容")
@RestController
@RequestMapping("/manage/website/webContent")
@AllArgsConstructor
public class WebContentController {
    
    private final IWebContentService webContentService;

    @ApiOperation(value = "分页查询网站文章内容列表", notes="网站文章内容-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebContentModel> getPageList(@RequestBody WebContentPageQuery query) {
        return webContentService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站文章内容信息", notes="网站文章内容-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebContentModel getWebContentById(@PathVariable("id") Long id) {
        return webContentService.getWebContentById(id);
    }

    @ApiOperation(value = "新增网站文章内容数据", notes="网站文章内容-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebContent")
    public Long createWebContent(@RequestBody WebContentModel model) {
        return webContentService.createWebContent(model);
    }

    @ApiOperation(value = "/修改网站文章内容数据", notes="网站文章内容-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebContent")
    public void updateWebContent(@RequestBody WebContentModel model) {
        webContentService.updateWebContent(model);
    }

    @ApiOperation(value = "删除网站文章内容", notes="网站文章内容-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebContent(@PathVariable("id") Long id) {
        return webContentService.deleteWebContent(id);
    }
}
