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
 * 网站用户对象 web_config
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_config")
@ApiModel(value = "网站用户对象", description = "website-网站用户信息表")
public class WebConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "网站标题")
    private String title;
    
    @ApiModelProperty(value = "网站描述")
    private String description;
    
    @ApiModelProperty(value = "网站图标")
    private String webImg;
    
    @ApiModelProperty(value = "备案号")
    private String icp;
    
    @ApiModelProperty(value = "版本号")
    private Long version;
    
}
