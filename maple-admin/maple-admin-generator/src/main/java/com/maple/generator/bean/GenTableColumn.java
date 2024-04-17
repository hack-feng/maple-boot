package com.maple.generator.bean;

import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 代码生成-代码生成业务表字段
 * </p>
 *
 * @author Maple
 * @since 2021-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "GenTableColumn对象", description = "代码生成-代码生成业务表字段")
public class GenTableColumn extends BaseEntity {

    @ApiModelProperty(value = "归属表编号")
    private Long tableId;

    @ApiModelProperty(value = "列名称")
    private String columnName;

    @ApiModelProperty(value = "列描述")
    private String columnComment;

    @ApiModelProperty(value = "列类型")
    private String columnType;

    @ApiModelProperty(value = "JAVA类型")
    private String javaType;

    @ApiModelProperty(value = "JAVA字段名")
    private String javaField;

    @ApiModelProperty(value = "是否主键（1是）")
    private Boolean isPk;

    @ApiModelProperty(value = "是否自增（1是）")
    private Boolean isIncrement;

    @ApiModelProperty(value = "是否必填（1是）")
    private Boolean isRequired;

    @ApiModelProperty(value = "是否为插入字段（1是）")
    private Boolean isInsert;

    @ApiModelProperty(value = "是否编辑字段（1是）")
    private Boolean isEdit;

    @ApiModelProperty(value = "是否列表字段（1是）")
    private Boolean isList;

    @ApiModelProperty(value = "是否查询字段（1是）")
    private Boolean isQuery;

    @ApiModelProperty(value = "查询方式（等于、不等于、大于、小于、范围）")
    private String queryType;

    @ApiModelProperty(value = "显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）")
    private String htmlType;

    @ApiModelProperty(value = "字典类型")
    private String dictType;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}
