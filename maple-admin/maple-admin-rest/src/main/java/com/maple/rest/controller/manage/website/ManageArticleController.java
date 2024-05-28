package com.maple.rest.controller.manage.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebArticlePageQuery;
import com.maple.website.service.IWebArticleService;
import com.maple.website.vo.model.WebArticleModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站文章  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站文章")
@RestController
@RequestMapping("/manage/manageArticle")
@AllArgsConstructor
public class ManageArticleController {
    
    private final IWebArticleService webArticleService;

    @ApiOperation(value = "分页查询网站文章列表", notes="网站文章-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebArticleModel> getPageList(@RequestBody WebArticlePageQuery query) {
        return webArticleService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站文章信息", notes="网站文章-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebArticleModel getWebArticleById(@PathVariable("id") Long id) {
        return webArticleService.getWebArticleById(id);
    }

    @ApiOperation(value = "新增网站文章数据", notes="网站文章-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebArticle")
    public Long createWebArticle(@RequestBody WebArticleModel model) {
        return webArticleService.createWebArticle(model);
    }

    @ApiOperation(value = "/修改网站文章数据", notes="网站文章-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebArticle")
    public void updateWebArticle(@RequestBody WebArticleModel model) {
        webArticleService.updateWebArticle(model);
    }

    @ApiOperation(value = "删除网站文章", notes="网站文章-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebArticle(@PathVariable("id") Long id) {
        return webArticleService.deleteWebArticle(id);
    }
}
