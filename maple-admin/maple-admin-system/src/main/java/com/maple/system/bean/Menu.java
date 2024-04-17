package com.maple.system.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户中心-菜单权限对象 usc_menu
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("usc_menu")
@ApiModel(value = "用户中心-菜单权限对象", description = "system-用户中心-菜单权限信息表")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路由名称")
    private String name;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "菜单类型")
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
    
    @ApiModelProperty(value = "重定向地址")
    private String redirect;

    @ApiModelProperty(value = "链接地址")
    private String linkUrl;
    
    @ApiModelProperty(value = "是否外链")
    private Boolean isLink;

    @ApiModelProperty(value = "是否内嵌窗口")
    private Boolean isIframe;

    @ApiModelProperty(value = "是否缓存（0缓存 1不缓存）")
    private Boolean isKeepAlive;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean isHide;

    @ApiModelProperty(value = "是否固定")
    private Boolean isAffix;

    @ApiModelProperty(value = "菜单状态")
    private Integer status;

    @ApiModelProperty(value = "权限标识")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;

}
