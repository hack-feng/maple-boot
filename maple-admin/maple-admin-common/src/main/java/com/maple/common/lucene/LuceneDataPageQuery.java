package com.maple.common.lucene;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhangfuzeng
 * @date 2024/1/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LuceneDataPageQuery {


    @ApiModelProperty(value = "分页信息")
    private Page<LuceneDataModel> page = new Page<>();

    @ApiModelProperty(value = "搜索信息")
    private LuceneDataModel query;

}
