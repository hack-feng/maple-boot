package com.maple.rest.controller.website;

import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.enums.BusinessTypeEnum;
import com.maple.common.enums.OperateTypeEnum;
import com.maple.common.model.MapleLog;
import com.maple.website.service.IWebArticleService;
import com.maple.website.service.IWebUserOperationService;
import com.maple.website.vo.model.WebArticleModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author zhangfuzeng
 * @date 2024/1/30
 */
@Api(tags = "博客-用户操作接口")
@RestController
@RequestMapping("/website/operation")
@AllArgsConstructor
public class UserOperationController {

    private final IWebArticleService articleService;

    private final IWebUserOperationService userOperationService;

    @ApiOperation(value = "收藏/取消收藏")
    @PostMapping("/collectTitle")
    @MapleLog(operateType = OperateTypeEnum.BLOG, businessType = BusinessTypeEnum.UPDATE)
    public void collectTitle(@RequestBody WebArticleModel articleModel) {
        WebArticleModel article = articleService.getById(articleModel.getId());
        if (Objects.isNull(article)) {
            throw new MapleCommonException(ErrorCode.NOT_FIND_DATA);
        }
        userOperationService.collect(article.getId(), articleModel.getIsCollect(), article.getDataType());
    }

    @ApiOperation(value = "点赞/取消点赞")
    @PostMapping("/likeTitle")
    @MapleLog(operateType = OperateTypeEnum.BLOG, businessType = BusinessTypeEnum.UPDATE)
    public void likeTitle(@RequestBody WebArticleModel articleModel) {
        WebArticleModel article = articleService.getById(articleModel.getId());
        if (Objects.isNull(article)) {
            throw new MapleCommonException(ErrorCode.NOT_FIND_DATA);
        }
        userOperationService.like(article.getId(), articleModel.getIsLike(), article.getDataType());
    }

    @ApiOperation(value = "下载资源信息")
    @PostMapping("/downResource")
    @MapleLog(operateType = OperateTypeEnum.BLOG, businessType = BusinessTypeEnum.OTHER, saveResult = false)
    public void downResource(@RequestBody WebArticleModel articleModel, HttpServletResponse response) {
        articleService.downResource(response, articleModel);
    }
}
