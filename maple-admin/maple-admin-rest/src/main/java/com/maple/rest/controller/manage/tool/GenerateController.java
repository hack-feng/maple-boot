package com.maple.rest.controller.manage.tool;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.generator.model.model.GenTableModel;
import com.maple.generator.model.query.GenTablePageQuery;
import com.maple.generator.model.query.GenTableQuery;
import com.maple.generator.service.IGenTableService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 *
 * @author xiaoxiaofeng
 */
@RestController
@RequestMapping("/manage/generate")
@AllArgsConstructor
public class GenerateController {
    private final IGenTableService genTableService;

    @ApiOperation(value = "分页查询代码生成业务列表")
    @PostMapping("/getPageList")
    public IPage<GenTableModel> getPageList(@RequestBody GenTablePageQuery query) {
        return genTableService.getGenTableList(query);
    }

    @ApiOperation(value = "查询据库列表")
    @PostMapping("/getDbList")
    public List<GenTableModel> getDbList(@RequestBody GenTableQuery query) {
        return genTableService.getDbTableList(query);
    }

    @ApiOperation(value = "将数据库表导入代码生成业务列表")
    @PostMapping("/importTable")
    public void importTableSave(@RequestBody GenTableQuery query) {
        genTableService.importGenTable(query.getTableNames());
    }

    @ApiOperation(value = "根据ID获取代码生成业务详情")
    @GetMapping("/{id}")
    public GenTableModel getInfo(@PathVariable Long id) {
        return genTableService.getGenTableInfo(id);
    }

    @ApiOperation(value = "根据ID修改生成代码的配置信息")
    @PostMapping("/updateGenTable")
    public void updateGenTable(@RequestBody GenTableModel model) {
        genTableService.updateGenTable(model);
    }

    @ApiOperation(value = "预览生成代码")
    @GetMapping("/preview/{tableId}")
    public Map<String, String> preview(@PathVariable("tableId") Long tableId) {
        return genTableService.previewCode(tableId);
    }

    @ApiOperation(value = "根据ID删除代码生成相关信息")
    @DeleteMapping("/{tableId}")
    public void deleteGenTableById(@PathVariable("tableId") Long tableId) {
        genTableService.deleteGenTableByIds(Collections.singletonList(tableId));
    }

    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName) throws IOException {
        byte[] data = genTableService.downloadCode(tableName);
        genCode(response, data);
    }

    @PostMapping("/batchDownload")
    public void batchDownload(HttpServletResponse response, @RequestBody GenTableQuery query) throws IOException {
        byte[] data = genTableService.downloadCode(query.getTableNames());
        genCode(response, data);
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=generateCode.zip");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }


}