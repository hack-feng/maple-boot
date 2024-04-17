package com.maple.generator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.TransformUtils;
import com.maple.generator.bean.GenTable;
import com.maple.generator.bean.GenTableColumn;
import com.maple.generator.constant.Constants;
import com.maple.generator.constant.GenConstants;
import com.maple.generator.mapper.GenTableColumnMapper;
import com.maple.generator.mapper.GenTableMapper;
import com.maple.generator.model.model.GenTableColumnModel;
import com.maple.generator.model.model.GenTableModel;
import com.maple.generator.model.query.GenTablePageQuery;
import com.maple.generator.model.query.GenTableQuery;
import com.maple.generator.service.IGenTableColumnService;
import com.maple.generator.service.IGenTableService;
import com.maple.generator.util.GenUtils;
import com.maple.generator.util.StringUtils;
import com.maple.generator.util.VelocityInitializer;
import com.maple.generator.util.VelocityUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 代码生成-代码生成业务表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-30
 */
@Service
@AllArgsConstructor
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements IGenTableService {

    private final GenTableMapper genTableMapper;

    private final GenTableColumnMapper genTableColumnMapper;

    private final IGenTableColumnService genTableColumnService;

    @Override
    public IPage<GenTableModel> getGenTableList(@NotNull GenTablePageQuery query) {
        return genTableMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public List<GenTableModel> getDbTableList(GenTableQuery query) {
        return genTableMapper.selectDbTableList(query);
    }

    @Override
    public GenTableModel getGenTableInfo(Long tableId) {
        GenTableModel table = genTableMapper.selectGenTable(GenTableQuery.builder().id(tableId).build());
        List<GenTableModel> tables = genTableMapper.selectGenTableList(new GenTableQuery());
        List<GenTableColumn> list = genTableColumnMapper.selectList(Wrappers.lambdaQuery(GenTableColumn.class)
                .eq(GenTableColumn::getTableId, table.getId())
                .orderByAsc(GenTableColumn::getSort));
        table.setTables(tables);
        table.setColumns(TransformUtils.mapList(list, GenTableColumnModel.class));
        return table;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGenTable(GenTableModel model) {
        GenTable genTable = TransformUtils.map(model, GenTable.class);
        genTableMapper.updateById(genTable);
        List<GenTableColumn> genTableColumns = TransformUtils.mapList(model.getColumns(), GenTableColumn.class);
        genTableColumnService.updateBatchById(genTableColumns);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteGenTableByIds(List<Long> tableIds) {
        genTableMapper.deleteBatchIds(tableIds);
        genTableColumnMapper.delete(Wrappers.lambdaUpdate(GenTableColumn.class).in(GenTableColumn::getTableId, tableIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importGenTable(List<String> tableNames) {
        List<GenTableModel> tableList = genTableMapper.selectDbTableList(GenTableQuery.builder().tableNames(tableNames).build());
        String operateName = JwtUtil.getAccount();
        try {
            for (GenTableModel tableModel : tableList) {
                String tableName = tableModel.getTableName();
                GenUtils.initTable(tableModel, operateName);
                GenTable genTable = TransformUtils.map(tableModel, GenTable.class);
                int row = genTableMapper.insert(genTable);
                tableModel.setId(genTable.getId());
                if (row > 0) {
                    // 保存列信息
                    List<GenTableColumnModel> genTableColumns = genTableColumnMapper.selectDbTableColumnsByName(tableName);
                    for (GenTableColumnModel column : genTableColumns) {
                        GenUtils.initColumnField(column, tableModel);
                        genTableColumnMapper.insert(TransformUtils.map(column, GenTableColumn.class));
                    }
                }
            }
        } catch (Exception e) {
            log.error("导入生成表失败", e);
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "导入失败：" + e.getMessage());
        }
    }

    @Override
    public Map<String, String> previewCode(Long tableId) {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTableModel table = genTableMapper.selectGenTable(GenTableQuery.builder().id(tableId).build());
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    @Override
    public byte[] downloadCode(String tableName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public void generatorCode(String tableName) {
        
    }

    @Override
    public void syncDb(String tableName) {
        
    }

    @Override
    public byte[] downloadCode(List<String> tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames) {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    @Override
    public void validateEdit(GenTable genTable) {

    }


    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip) {
        // 查询表信息
        GenTableModel table = genTableMapper.selectGenTable(GenTableQuery.builder().tableName(tableName).build());
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory());
        for (String template : templates) {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, Constants.UTF8);
            tpl.merge(context, sw);
            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, Constants.UTF8);
                IOUtils.closeQuietly(sw);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 设置主键列信息
     *
     * @param table 业务表信息
     */
    public void setPkColumn(GenTableModel table) {
        for (GenTableColumnModel column : table.getColumns()) {
            if (Boolean.TRUE.equals(column.getIsPk())) {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn())) {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory())) {
            for (GenTableColumnModel column : table.getSubTable().getColumns()) {
                if (Boolean.TRUE.equals(column.getIsPk())) {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (StringUtils.isNull(table.getSubTable().getPkColumn())) {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    /**
     * 设置主子表信息
     *
     * @param table 业务表信息
     */
    public void setSubTable(GenTableModel table) {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty(subTableName)) {
            table.setSubTable(genTableMapper.selectGenTable(GenTableQuery.builder().tableName(subTableName).build()));
        }
    }

    /**
     * 获取代码生成地址
     *
     * @param table    业务表信息
     * @param template 模板文件路径
     * @return 生成地址
     */
    public static String getGenPath(GenTableModel table, String template) {
        String genPath = table.getGenPath();
        if (StringUtils.equals(genPath, "/")) {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }
}
