package com.maple.system.bean;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户中心-角色信息对象 usc_role
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("usc_role")
@ApiModel(value = "用户中心-角色信息对象", description = "system-用户中心-角色信息信息表")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleKey;

    @ApiModelProperty(value = "显示顺序")
    private Long sortNum;

    @ApiModelProperty(value = "数据范围")
    private String dataScope;

    @ApiModelProperty(value = "菜单树选择项是否关联显示")
    private Boolean menuCheckStrictly;

    @ApiModelProperty(value = "部门树选择项是否关联显示")
    private Boolean deptCheckStrictly;

    @ApiModelProperty(value = "角色状态")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @TableLogic
    @ApiModelProperty(value = "删除标志")
    private Boolean isDelete;

}
