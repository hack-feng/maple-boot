package com.maple.system.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理-参数配置对象 sys_config
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_config")
@ApiModel(value = "系统管理-参数配置对象", description = "system-系统管理-参数配置信息表")
public class Config extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "参数名称")
    private String configName;
    
    @ApiModelProperty(value = "参数键名")
    private String configKey;
    
    @ApiModelProperty(value = "参数键值")
    private String configValue;
    
    @ApiModelProperty(value = "是否内置")
    private Boolean configType;
    
    @ApiModelProperty(value = "备注")
    private String remark;
    
    @ApiModelProperty(value = "数据归属部门ID")
    @TableField(value = "belong_dept_id", fill = FieldFill.INSERT)
    private Long belongDeptId;
}
