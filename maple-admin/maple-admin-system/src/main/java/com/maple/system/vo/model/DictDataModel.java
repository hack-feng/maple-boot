package com.maple.system.vo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/5/23
 */
@Data
public class DictDataModel {

    @ApiModelProperty(value = "字典编码")
    private Long id;

    @ApiModelProperty(value = "字典排序")
    private Integer dictSort;

    @ApiModelProperty(value = "字典标签")
    private String dictLabel;

    @ApiModelProperty(value = "字典键值")
    private String dictValue;

    @ApiModelProperty(value = "字典编码")
    private String dictCode;

    @ApiModelProperty(value = "样式属性（其他样式扩展）")
    private String cssClass;

    @ApiModelProperty(value = "表格回显样式")
    private String listClass;

    @ApiModelProperty(value = "是否默认（1是 0否）")
    private Boolean isDefault;

    @ApiModelProperty(value = "状态（1正常 0停用）")
    private Boolean status;

    @ApiModelProperty(value = "备注")
    private String remark;
}
