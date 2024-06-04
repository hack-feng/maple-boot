package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站用户操作对象 web_menu_category
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-31
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站用户操作对象", description = "system-网站用户操作信息表")
public class WebMenuCategoryModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "菜单id")
    private Long webMenuId;

    @ApiModelProperty(value = "类目id")
    private Long categoryId;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

}
