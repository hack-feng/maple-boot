package com.maple.generator.model.model;

import com.maple.generator.util.StringUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/1/21
 */
@Data
public class GenTableColumnModel {

    @ApiModelProperty(value = "编号")
    private Long id;

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

    @ApiModelProperty(value = "创建人id")
    private Long createId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人id")
    private Long updateId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "操作人名称")
    private String operateName;

    public boolean isSuperColumn() {
        return isSuperColumn(this.javaField);
    }

    public static boolean isSuperColumn(String javaField) {
        return StringUtils.equalsAnyIgnoreCase(javaField,
                // BaseEntity
                "createBy" , "createTime" , "updateBy" , "updateTime" , "remark" ,
                // TreeEntity
                "parentName" , "parentId" , "sortNum" , "ancestors");
    }

    public boolean isUsableColumn() {
        return isUsableColumn(javaField);
    }

    public static boolean isUsableColumn(String javaField) {
        // isSuperColumn()中的名单用于避免生成多余Domain属性，若某些属性在生成页面时需要用到不能忽略，则放在此处白名单
        return StringUtils.equalsAnyIgnoreCase(javaField, "parentId" , "sortNum" , "remark");
    }

    public String readConverterExp() {
        String remarks = StringUtils.substringBetween(this.columnComment, "（" , "）");
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotEmpty(remarks)) {
            for (String value : remarks.split(" ")) {
                if (StringUtils.isNotEmpty(value)) {
                    Object startStr = value.subSequence(0, 1);
                    String endStr = value.substring(1);
                    sb.append("").append(startStr).append("=").append(endStr).append(",");
                }
            }
            return sb.deleteCharAt(sb.length() - 1).toString();
        } else {
            return this.columnComment;
        }
    }
}
