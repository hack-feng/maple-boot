package com.maple.rest.controller.website;

import com.maple.website.service.IWebConfigService;
import com.maple.website.service.IWebMenuService;
import com.maple.website.vo.model.WebConfigModel;
import com.maple.website.vo.model.WebMenuModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "查询网站菜单树结构", notes = "网站菜单-查询树结构", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getHeaderMenuTreeList")
    public List<WebMenuModel> getHeaderMenuTreeList(@RequestBody WebMenuModel model) {
        return webMenuService.getTreeList(model);
    }

    @ApiOperation(value = "查询网站配置列表", notes = "网站配置-查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getAllConfigList")
    public List<WebConfigModel> getAllConfigList() {
        return webConfigService.getAllConfigList();
    }


}
