package com.maple.rest.controller.manage.website;

import java.util.List;
import com.maple.website.service.IWebCategoryService;
import com.maple.website.vo.model.WebCategoryModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 网站类目  前端控制器
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
@Api(tags = "网站类目")
@RestController
@RequestMapping("/manage/manageCategory")
@AllArgsConstructor
public class ManageCategoryController {

    private final IWebCategoryService webCategoryService;

    @ApiOperation(value = "查询网站类目树结构", notes="网站类目-查询树结构", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/getTreeList")
    public List<WebCategoryModel> getTreeList(@RequestBody WebCategoryModel model) {
        return webCategoryService.getTreeList(model);
    }

    @ApiOperation(value = "查询类目列表", notes="博客类目-分页查询列表", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/getCategoryList")
    public List<WebCategoryModel> getCategoryList(@RequestBody WebCategoryModel query) {
        return webCategoryService.getCategoryList(query);
    }

    @ApiOperation(value = "根据id查询网站类目信息", notes="网站类目-根据id查询数据信息", nickname = "www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public WebCategoryModel getWebCategoryById(@PathVariable("id") Long id) {
        return webCategoryService.getWebCategoryById(id);
    }

    @ApiOperation(value = "新增网站类目数据", notes="网站类目-新增数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/createWebCategory")
    public Long createWebCategory(@RequestBody WebCategoryModel model) {
        return webCategoryService.createWebCategory(model);
    }

    @ApiOperation(value = "修改网站类目数据", notes="网站类目-修改数据", nickname = "www.xiaoxiaofeng.com")
    @PostMapping("/updateWebCategory")
    public void updateWebCategory(@RequestBody WebCategoryModel model) {
        webCategoryService.updateWebCategory(model);
    }

    @ApiOperation(value = "删除网站类目", notes="网站类目-根据id删除数据信息", nickname = "www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteWebCategory(@PathVariable("id") Long id) {
        return webCategoryService.deleteWebCategory(id);
    }
}
