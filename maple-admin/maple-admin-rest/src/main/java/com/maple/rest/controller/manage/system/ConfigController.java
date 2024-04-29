package com.maple.rest.controller.manage.system;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.service.IConfigService;
import com.maple.system.vo.model.ConfigModel;
import com.maple.system.vo.query.ConfigPageQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 系统管理-参数配置  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
@Api(tags = "系统管理-参数配置")
@RestController
@RequestMapping("/manage/system/config")
@AllArgsConstructor
public class ConfigController {
    
    private final IConfigService configService;

    @ApiOperation(value = "分页查询系统管理-参数配置列表", notes="系统管理-参数配置-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<ConfigModel> getPageList(@RequestBody ConfigPageQuery query) {
        return configService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询系统管理-参数配置信息", notes="系统管理-参数配置-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public ConfigModel getConfigById(@PathVariable("id") Long id) {
        return configService.getConfigById(id);
    }

    @ApiOperation(value = "新增系统管理-参数配置数据", notes="系统管理-参数配置-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createConfig")
    public Long createConfig(@RequestBody ConfigModel model) {
        return configService.createConfig(model);
    }

    @ApiOperation(value = "/修改系统管理-参数配置数据", notes="系统管理-参数配置-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateConfig")
    public void updateConfig(@RequestBody ConfigModel model) {
        configService.updateConfig(model);
    }

    @ApiOperation(value = "删除系统管理-参数配置", notes="系统管理-参数配置-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteConfig(@PathVariable("id") Long id) {
        return configService.deleteConfig(id);
    }
}
