package com.maple.system.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 系统管理-参数配置对象 sys_config
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "系统管理-参数配置对象", description = "system-系统管理-参数配置信息表")
public class ConfigModel {

    @ApiModelProperty(value = "参数主键")
    private Long id;

    @ApiModelProperty(value = "参数名称")
    private String configName;

    @ApiModelProperty(value = "参数键名")
    private String configKey;

    @ApiModelProperty(value = "参数键值")
    private String configValue;

    @ApiModelProperty(value = "是否内置")
    private Boolean configType;

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人id")
    private Long updateId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "数据归属部门ID")
    private Long belongDeptId;

}
