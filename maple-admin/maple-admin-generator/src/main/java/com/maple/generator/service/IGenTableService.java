package com.maple.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.generator.bean.GenTable;
import com.maple.generator.vo.model.GenTableModel;
import com.maple.generator.vo.query.GenTablePageQuery;
import com.maple.generator.vo.query.GenTableQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代码生成-代码生成业务表 服务类
 * </p>
 *
 * @author Maple
 * @since 2021-12-30
 */
public interface IGenTableService extends IService<GenTable> {

    /**
     * 查询代码生成业务列表
     *
     * @param query 查询条件
     * @return 业务集合
     */
    IPage<GenTableModel> getGenTableList(GenTablePageQuery query);

    /**
     * 查询据库列表
     *
     * @param query 查询条件
     * @return 数据库表集合
     */
    List<GenTableModel> getDbTableList(GenTableQuery query);

    /**
     * 导入表结构
     *
     * @param tableNames 导入表名称
     */
    void importGenTable(List<String> tableNames);

    /**
     * 获取生成表的信息
     *
     * @param tableId 生成表ID
     * @return 表结构详情
     */
    GenTableModel getGenTableInfo(Long tableId);

    /**
     * 修改业务
     *
     * @param genTable 业务信息
     */
    void updateGenTable(GenTableModel genTable);

    /**
     * 删除业务信息
     *
     * @param tableIds 需要删除的表数据ID
     */
    void deleteGenTableByIds(List<Long> tableIds);

    /**
     * 预览代码
     *
     * @param tableId 表编号
     * @return 预览数据列表
     */
    Map<String, String> previewCode(Long tableId);

    /**
     * 生成代码（下载方式）
     *
     * @param tableName 表名称
     * @return 数据
     */
    byte[] downloadCode(String tableName);

    /**
     * 生成代码（自定义路径）
     *
     * @param tableName 表名称
     */
    void generatorCode(String tableName);

    /**
     * 同步数据库
     *
     * @param tableName 表名称
     */
    void syncDb(String tableName);

    /**
     * 批量生成代码（下载方式）
     *
     * @param tableNames 表数组
     * @return 数据
     */
    byte[] downloadCode(List<String> tableNames);

    /**
     * 修改保存参数校验
     *
     * @param genTable 业务信息
     */
    void validateEdit(GenTable genTable);

}
