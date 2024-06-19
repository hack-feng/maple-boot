package com.maple.website.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网站文章对象 web_article
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_article")
@ApiModel(value = "网站文章对象", description = "website-网站文章信息表")
public class WebArticle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "所属类目id")
    private Long categoryId;

    @ApiModelProperty(value = "父节点id")
    private Long parentId;

    @ApiModelProperty("文章类型")
    private Integer articleType;

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

    @ApiModelProperty(value = "下载数量")
    private Long downloadNum;

    @ApiModelProperty(value = "收藏数量")
    private Long collectNum;

    @ApiModelProperty(value = "点赞数量")
    private Long likeNum;

    @ApiModelProperty(value = "评论数量")
    private Long commentNum;

}
