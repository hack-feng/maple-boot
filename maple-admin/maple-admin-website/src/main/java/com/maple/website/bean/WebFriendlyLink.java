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
 * 网站链接对象 web_friendly_link
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_friendly_link")
@ApiModel(value = "网站链接对象", description = "website-网站链接信息表")
public class WebFriendlyLink extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "链接类型")
    private Integer linkType;
    
    @ApiModelProperty(value = "名称")
    private String name;
    
    @ApiModelProperty(value = "图标")
    private String logo;
    
    @ApiModelProperty(value = "链接地址")
    private String url;
    
    @ApiModelProperty(value = "排序")
    private Long sortNum;
    
    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;
    
    @ApiModelProperty(value = "是否热门")
    private Boolean isHot;
    
    @ApiModelProperty(value = "链接次数")
    private Long linkNum;
    
    @ApiModelProperty(value = "描述")
    private String description;
    
    @ApiModelProperty(value = "备注")
    private String remark;
    
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;
    
}
