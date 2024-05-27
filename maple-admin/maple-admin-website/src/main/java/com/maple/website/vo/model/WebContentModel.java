package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站文章内容对象 web_content
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站文章内容对象", description = "website-网站文章内容信息表")
public class WebContentModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "简介id")
    private Long articleId;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "原始内容")
    private String originalContent;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

}
