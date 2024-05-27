package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站资源对象 web_resource
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站资源对象", description = "website-网站资源信息表")
public class WebResourceModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "资源类型")
    private Integer resourceType;

    @ApiModelProperty(value = "模板名称")
    private String resourceName;

    @ApiModelProperty(value = "模板描述")
    private String resourceDesc;

    @ApiModelProperty(value = "图片")
    private String resourceImg;

    @ApiModelProperty(value = "下载类型 1:又拍云  2：百度云")
    private Boolean downType;

    @ApiModelProperty(value = "下载地址")
    private String downUrl;

    @ApiModelProperty(value = "文件后缀")
    private String fileSuffix;

    @ApiModelProperty(value = "预览地址")
    private String visitUrl;

    @ApiModelProperty(value = "排序")
    private Long sortNum;

    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;

    @ApiModelProperty(value = "是否热门")
    private Boolean isHot;

    @ApiModelProperty(value = "状态")
    private Boolean status;

    @ApiModelProperty(value = "下载数量")
    private Long downloadNum;

    @ApiModelProperty(value = "收藏数量")
    private Long collectNum;

    @ApiModelProperty(value = "资源描述内容ID")
    private Long titleId;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

}
