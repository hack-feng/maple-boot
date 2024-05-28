package com.maple.rest.controller.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserCommentPageQuery;
import com.maple.website.service.IWebUserCommentService;
import com.maple.website.vo.model.WebUserCommentModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站用户评论  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站用户评论")
@RestController
@RequestMapping("/website/webUserComment")
@AllArgsConstructor
public class WebUserCommentController {
    
    private final IWebUserCommentService webUserCommentService;

    @ApiOperation(value = "分页查询网站用户评论列表", notes="网站用户评论-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebUserCommentModel> getPageList(@RequestBody WebUserCommentPageQuery query) {
        return webUserCommentService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站用户评论信息", notes="网站用户评论-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebUserCommentModel getWebUserCommentById(@PathVariable("id") Long id) {
        return webUserCommentService.getWebUserCommentById(id);
    }

    @ApiOperation(value = "新增网站用户评论数据", notes="网站用户评论-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebUserComment")
    public Long createWebUserComment(@RequestBody WebUserCommentModel model) {
        return webUserCommentService.createWebUserComment(model);
    }

    @ApiOperation(value = "修改网站用户评论数据", notes="网站用户评论-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebUserComment")
    public void updateWebUserComment(@RequestBody WebUserCommentModel model) {
        webUserCommentService.updateWebUserComment(model);
    }

    @ApiOperation(value = "删除网站用户评论", notes="网站用户评论-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebUserComment(@PathVariable("id") Long id) {
        return webUserCommentService.deleteWebUserComment(id);
    }
}
