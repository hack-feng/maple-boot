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
 * 网站类目对象 web_category
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_category")
@ApiModel(value = "网站类目对象", description = "website-网站类目信息表")
public class WebCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图标")
    private String icon;
    
    @ApiModelProperty(value = "名称")
    private String name;
    
    @ApiModelProperty(value = "描述")
    private String description;
    
    @ApiModelProperty(value = "父节点id")
    private Long parentId;
    
    @ApiModelProperty(value = "祖级菜单")
    private String ancestors;
    
    @ApiModelProperty(value = "排序")
    private Long sortNum;
    
    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;
    
    @ApiModelProperty(value = "是否热门")
    private Boolean isHot;
    
    @ApiModelProperty(value = "目标地址")
    private String target;
    
    @ApiModelProperty(value = "跳转地址")
    private String url;
    
    @ApiModelProperty(value = "是否有效")
    private Boolean isValid;
    
}
