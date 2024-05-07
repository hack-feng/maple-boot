package com.maple.common.config.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author zhangfuzeng
 * @date 2024/5/6
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BaseQuery {

    @ApiModelProperty("数据查询权限")
    private String dataScope;
}
