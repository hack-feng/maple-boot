package com.maple.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/3/13
 */
@Data
public class IdNumList {

    @ApiModelProperty("分类ID")
    private Long id;
    
    @ApiModelProperty("数量")
    private Long num;
}
