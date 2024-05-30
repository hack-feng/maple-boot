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
 * 网站配置对象 web_config
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_config")
@ApiModel(value = "网站配置对象", description = "website-网站配置信息表")
public class WebConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "是否内置")
    private Boolean isSystem;

    @ApiModelProperty(value = "备注")
    private String remark;

}
