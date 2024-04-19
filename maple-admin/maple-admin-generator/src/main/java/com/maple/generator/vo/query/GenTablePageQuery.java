package com.maple.generator.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.generator.bean.GenTable;
import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2024/2/4
 */
@Data
public class GenTablePageQuery {

    private Page<GenTable> page;

    private GenTableQuery query;
}
