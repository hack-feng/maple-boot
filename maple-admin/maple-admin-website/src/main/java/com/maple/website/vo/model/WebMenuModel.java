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
 * 网站菜单对象 web_menu
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站菜单对象", description = "website-网站菜单信息表")
public class WebMenuModel {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "路由名称")
    private String name;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单大图")
    private String image;

    @ApiModelProperty(value = "菜单类型（H页头菜单 F页脚菜单）")
    private String menuType;

    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    @ApiModelProperty(value = "祖级菜单")
    private String ancestors;

    @ApiModelProperty(value = "显示顺序")
    private Long sortNum;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "是否外链")
    private Boolean isLink;

    @ApiModelProperty(value = "菜单状态（0正常 1停用）")
    private Integer status;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人id")
    private Long updateId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "子节点信息")
    List<WebMenuModel> children;
}
