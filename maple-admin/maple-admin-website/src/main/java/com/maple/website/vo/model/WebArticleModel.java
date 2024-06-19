package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 网站文章对象 web_article
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站文章对象", description = "website-网站文章信息表")
public class WebArticleModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "所属类目id")
    private Long categoryId;

    @ApiModelProperty("文章类型")
    private Integer articleType;

    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "排序")
    private Long sortNum;

    @ApiModelProperty(value = "关键词，多个以英文逗号分割")
    private String keywords;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "下载类型")
    private Integer downloadType;

    @ApiModelProperty(value = "原文地址")
    private String originalUrl;

    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;

    @ApiModelProperty(value = "是否热门")
    private Boolean isHot;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "来源")
    private Integer source;

    @ApiModelProperty(value = "星级")
    private Long level;

    @ApiModelProperty(value = "阅读数量")
    private Long readNum;

    @ApiModelProperty(value = "收藏数量")
    private Long collectNum;

    @ApiModelProperty(value = "点赞数量")
    private Long likeNum;

    @ApiModelProperty(value = "评论数量")
    private Long commentNum;

    @ApiModelProperty("是否阅读")
    private Boolean isRead;

    @ApiModelProperty("是否收藏")
    private Boolean isCollect;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty("类目信息")
    private WebCategoryModel categoryModel;

    @ApiModelProperty("类目ID集合")
    private List<Long> categoryIdList;

    @ApiModelProperty("文章内容")
    private WebContentModel contentModel;

    @ApiModelProperty("文章多级目录")
    private List<WebArticleModel> childList;

    @ApiModelProperty("上一篇文章")
    private WebArticleModel preTitle;

    @ApiModelProperty("下一篇文章")
    private WebArticleModel nextTitle;

}
