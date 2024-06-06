package com.maple.rest.controller.manage.website;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserOperationPageQuery;
import com.maple.website.service.IWebUserOperationService;
import com.maple.website.vo.model.WebUserOperationModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站用户操作  前端控制器
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Api(tags = "网站用户操作")
@RestController
@RequestMapping("/manage/manageUserOperation")
@AllArgsConstructor
public class ManageUserOperationController {
    
    private final IWebUserOperationService webUserOperationService;

    @ApiOperation(value = "分页查询网站用户操作列表", notes="网站用户操作-分页查询列表", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getPageList")
    public IPage<WebUserOperationModel> getPageList(@RequestBody WebUserOperationPageQuery query) {
        return webUserOperationService.getPageList(query);
    }

    @ApiOperation(value = "根据id查询网站用户操作信息", notes="网站用户操作-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebUserOperationModel getWebUserOperationById(@PathVariable("id") Long id) {
        return webUserOperationService.getWebUserOperationById(id);
    }
}
