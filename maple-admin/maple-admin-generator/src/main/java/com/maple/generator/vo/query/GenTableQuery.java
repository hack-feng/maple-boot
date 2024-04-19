package com.maple.generator.vo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/1/21
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GenTableQuery {

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "表名称")
    private String tableName;

    @ApiModelProperty(value = "表描述")
    private String tableComment;

    @ApiModelProperty(value = "多个表名称")
    private List<String> tableNames;
}
