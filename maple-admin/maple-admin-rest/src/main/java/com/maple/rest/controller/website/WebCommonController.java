package com.maple.rest.controller.website;

import com.maple.system.service.IDictDataService;
import com.maple.system.vo.model.DictDataModel;
import com.maple.website.service.IWebConfigService;
import com.maple.website.service.IWebMenuService;
import com.maple.website.vo.model.WebConfigModel;
import com.maple.website.vo.model.WebMenuModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangfuzeng
 * @date 2024/5/30
 */
@Api(tags = "网站通用接口")
@RestController
@RequestMapping("/website/common")
@AllArgsConstructor
public class WebCommonController {

    private final IWebMenuService webMenuService;

    private final IWebConfigService webConfigService;
    
    private final IDictDataService dictDataService;

    @ApiOperation(value = "查询网站菜单树结构", notes = "网站菜单-查询树结构", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getHeaderMenuTreeList")
    public List<WebMenuModel> getHeaderMenuTreeList(@RequestBody WebMenuModel model) {
        return webMenuService.getTreeList(model);
    }

    @GetMapping("/getWebMenuByPath/{path}")
    public WebMenuModel getWebMenuByPath(@PathVariable("path") String path) {
        return webMenuService.getWebMenuByPath(path);
    }

    @ApiOperation(value = "查询网站配置列表", notes = "网站配置-查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getAllConfigList")
    public List<WebConfigModel> getAllConfigList() {
        return webConfigService.getAllConfigList();
    }

    @ApiOperation(value = "根据字典type获取字典数据")
    @GetMapping("/getDictByCode/{dictCode}")
    public List<DictDataModel> getDictByCode(@PathVariable("dictCode") String dictCode) {
        return dictDataService.getDictByCode(dictCode);
    }

}
