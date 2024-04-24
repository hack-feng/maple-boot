package com.maple.rest.controller.manage.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.service.IDictDataService;
import com.maple.system.service.IDictTypeService;
import com.maple.system.vo.model.DictDataModel;
import com.maple.system.vo.model.DictTypeModel;
import com.maple.system.vo.query.DictTypePageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/5/23
 */
@Api(tags = "基础功能-字典管理接口")
@RestController
@AllArgsConstructor
@RequestMapping("/manage/dict")
public class DictController {

    private final IDictDataService dictDataService;


    private final IDictTypeService dictTypeService;

    @ApiOperation(value = "分页查询系统管理-字典类型列表", notes = "系统管理-字典类型-分页查询列表", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<DictTypeModel> getPageList(@RequestBody DictTypePageQuery query) {
        return dictTypeService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询系统管理-字典类型信息", notes = "系统管理-字典类型-根据id查询数据信息", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public DictTypeModel getDictTypeById(@PathVariable("id") Long id) {
        return dictTypeService.getDictTypeById(id);
    }

    @ApiOperation(value = "新增系统管理-字典类型数据", notes = "系统管理-字典类型-新增数据", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/createDictType")
    public Long createDictType(@RequestBody DictTypeModel model) {
        return dictTypeService.createDictType(model);
    }

    @ApiOperation(value = "/修改系统管理-字典类型数据", notes = "系统管理-字典类型-修改数据", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/updateDictType")
    public void updateDictType(@RequestBody DictTypeModel model) {
        dictTypeService.updateDictType(model);
    }

    @ApiOperation(value = "删除系统管理-字典类型", notes = "系统管理-字典类型-根据id删除数据信息", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteDictType(@PathVariable("id") Long id) {
        return dictTypeService.deleteDictType(id);
    }

    @ApiOperation(value = "根据字典type获取字典数据")
    @GetMapping("/getDictByCode/{dictCode}")
    public List<DictDataModel> getDictByCode(@PathVariable("dictCode") String dictCode) {
        return dictDataService.getDictByCode(dictCode);
    }

    @ApiOperation("获取字典类型列表")
    @PostMapping("/getDictTypeList")
    public List<DictTypeModel> getDictTypeList() {
        return dictTypeService.getDictTypeList();
    }
    
}
