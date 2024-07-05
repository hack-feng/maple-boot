package com.maple.rest.controller.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.common.util.JwtUtil;
import com.maple.website.service.IWebArticleService;
import com.maple.website.service.IWebCategoryService;
import com.maple.website.vo.model.WebArticleModel;
import com.maple.website.vo.model.WebCategoryModel;
import com.maple.website.vo.query.WebArticlePageQuery;
import com.maple.website.vo.query.WebCategoryPageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 网站文章  前端控制器
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站文章")
@RestController
@RequestMapping("/website")
@AllArgsConstructor
public class WebsiteController {

    private final IWebArticleService webArticleService;

    private final IWebCategoryService webCategoryService;

    @ApiOperation(value = "分页查询网站文章列表", notes = "网站文章-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/webArticle/getPageArticle")
    public IPage<WebArticleModel> getPageArticle(@RequestBody WebArticlePageQuery query) {
        if (Objects.isNull(query.getQuery())) {
            query.setQuery(new WebArticleModel());
        }
        query.getQuery().setUserId(JwtUtil.getUserId());
        query.getQuery().setStatus(1);
        return webArticleService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站文章信息", notes = "网站文章-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/webArticle/{id}")
    public WebArticleModel getWebArticleById(@PathVariable("id") Long id) {
        return webArticleService.getWebArticleById(id, true);
    }

    @ApiOperation(value = "查询类目列表", notes="博客类目-分页查询列表", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/webCategory/getPageCategory")
    public IPage<WebCategoryModel> getPageCategory(@RequestBody WebCategoryPageQuery query) {
        return webCategoryService.getPageCategory(query);
    }

    @ApiOperation(value = "根据id查询网站类目信息", notes="网站类目-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/webCategory/{id}")
    public WebCategoryModel getWebCategoryById(@PathVariable("id") Long id) {
        return webCategoryService.getCategoryInfo(id);
    }
}
