package com.maple.generator.bean;

import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成-代码生成业务表
 * </p>
 *
 * @author Maple
 * @since 2021-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GenTable对象", description = "代码生成-代码生成业务表")
public class GenTable extends BaseEntity {

    @ApiModelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty(value = "表描述")
    private String tableComment;

    @ApiModelProperty(value = "关联子表的表名")
    private String subTableName;

    @ApiModelProperty(value = "子表关联的外键名")
    private String subTableFkName;

    @ApiModelProperty(value = "实体类名称")
    private String className;

    @ApiModelProperty(value = "使用的模板（crud单表操作 tree树表操作）")
    private String tplCategory;

    @ApiModelProperty(value = "生成包路径")
    private String packageName;

    @ApiModelProperty(value = "生成模块名")
    private String moduleName;

    @ApiModelProperty(value = "生成业务名")
    private String businessName;

    @ApiModelProperty(value = "生成功能名")
    private String functionName;

    @ApiModelProperty(value = "生成功能作者")
    private String functionAuthor;

    @ApiModelProperty(value = "生成代码方式（0zip压缩包 1自定义路径）")
    private String genType;

    @ApiModelProperty(value = "生成路径（不填默认项目路径）")
    private String genPath;

    @ApiModelProperty(value = "树编码字段")
    private String treeCode;

    @ApiModelProperty(value = "树父编码字段")
    private String treeParentCode;

    @ApiModelProperty(value = "树名称字段")
    private String treeName;

    @ApiModelProperty(value = "上级菜单ID字段")
    private Long parentMenuId;
    
    @ApiModelProperty(value = "上级菜单名称字段")
    private String parentMenuName;

    @ApiModelProperty(value = "其它生成选项")
    private String options;

    @ApiModelProperty(value = "备注")
    private String remark;
}
