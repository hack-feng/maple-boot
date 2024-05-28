package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站配置对象 web_config
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站配置对象", description = "website-网站配置信息表")
public class WebConfigModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "网站标题")
    private String title;

    @ApiModelProperty(value = "网站描述")
    private String description;

    @ApiModelProperty(value = "网站图标")
    private String webImg;

    @ApiModelProperty(value = "备案号")
    private String icp;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "版本号")
    private Long version;

}
