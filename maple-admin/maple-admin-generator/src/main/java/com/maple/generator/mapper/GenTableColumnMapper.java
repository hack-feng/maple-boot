package com.maple.generator.mapper;

import com.maple.generator.bean.GenTableColumn;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.generator.model.model.GenTableColumnModel;

import java.util.List;

/**
 * <p>
 * 代码生成-代码生成业务表字段 Mapper 接口
 * </p>
 *
 * @author Maple
 * @since 2021-12-30
 */
public interface GenTableColumnMapper extends BaseMapper<GenTableColumn> {

    List<GenTableColumnModel> selectDbTableColumnsByName(String tableName);
}
