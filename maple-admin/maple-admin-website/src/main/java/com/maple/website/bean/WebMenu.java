package com.maple.website.bean;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 网站菜单对象 web_menu
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_menu")
@ApiModel(value = "网站菜单对象", description = "website-网站菜单信息表")
public class WebMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    
}
