package com.maple.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.generator.bean.GenTable;
import com.maple.generator.vo.model.GenTableModel;
import com.maple.generator.vo.query.GenTableQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代码生成-代码生成业务表 Mapper 接口
 * </p>
 *
 * @author Maple
 * @since 2021-12-30
 */
public interface GenTableMapper extends BaseMapper<GenTable> {

    List<GenTableModel> selectDbTableList(@Param("req") GenTableQuery req);

    /**
     * id 和 name 必传一个，且应该都是唯一的数据
     */
    GenTableModel selectGenTable(@Param("req") GenTableQuery req);

    List<GenTableModel> selectGenTableList(@Param("req") GenTableQuery req);

    IPage<GenTableModel> getPageList(Page<GenTable> page, @Param("req") GenTableQuery query);
}
