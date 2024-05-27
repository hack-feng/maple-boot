package com.maple.rest.controller.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebFriendlyLinkPageQuery;
import com.maple.website.service.IWebFriendlyLinkService;
import com.maple.website.vo.model.WebFriendlyLinkModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站链接  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站链接")
@RestController
@RequestMapping("/manage/website/webFriendlyLink")
@AllArgsConstructor
public class WebFriendlyLinkController {
    
    private final IWebFriendlyLinkService webFriendlyLinkService;

    @ApiOperation(value = "分页查询网站链接列表", notes="网站链接-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebFriendlyLinkModel> getPageList(@RequestBody WebFriendlyLinkPageQuery query) {
        return webFriendlyLinkService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站链接信息", notes="网站链接-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebFriendlyLinkModel getWebFriendlyLinkById(@PathVariable("id") Long id) {
        return webFriendlyLinkService.getWebFriendlyLinkById(id);
    }

    @ApiOperation(value = "新增网站链接数据", notes="网站链接-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebFriendlyLink")
    public Long createWebFriendlyLink(@RequestBody WebFriendlyLinkModel model) {
        return webFriendlyLinkService.createWebFriendlyLink(model);
    }

    @ApiOperation(value = "修改网站链接数据", notes="网站链接-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebFriendlyLink")
    public void updateWebFriendlyLink(@RequestBody WebFriendlyLinkModel model) {
        webFriendlyLinkService.updateWebFriendlyLink(model);
    }

    @ApiOperation(value = "删除网站链接", notes="网站链接-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebFriendlyLink(@PathVariable("id") Long id) {
        return webFriendlyLinkService.deleteWebFriendlyLink(id);
    }
}
