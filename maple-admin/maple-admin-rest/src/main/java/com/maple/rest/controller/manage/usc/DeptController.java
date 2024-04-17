package com.maple.rest.controller.manage.usc;

import com.maple.system.service.IDeptService;
import com.maple.system.vo.model.DeptModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户中心-部门  前端控制器
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-25
 */
@Api(tags = "用户中心-部门")
@RestController
@RequestMapping("/manage/system/dept")
@AllArgsConstructor
public class DeptController {

    private final IDeptService deptService;

    @ApiOperation(value = "查询用户中心-部门树结构", notes="用户中心-部门-查询树结构", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/getTreeList")
    public List<DeptModel> getTreeList(@RequestBody DeptModel model) {
        return deptService.getTreeList(model);
    }

    @ApiOperation(value = "根据id查询用户中心-部门信息", notes="用户中心-部门-根据id查询数据信息", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @GetMapping(value = "/{id}")
    public DeptModel getDeptById(@PathVariable("id") Long id) {
        return deptService.getDeptById(id);
    }

    @ApiOperation(value = "新增用户中心-部门数据", notes="用户中心-部门-新增数据", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/createDept")
    public Long createDept(@RequestBody DeptModel model) {
        return deptService.createDept(model);
    }

    @ApiOperation(value = "/修改用户中心-部门数据", notes="用户中心-部门-修改数据", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @PostMapping("/updateDept")
    public void updateDept(@RequestBody DeptModel model) {
        deptService.updateDept(model);
    }

    @ApiOperation(value = "删除用户中心-部门", notes="用户中心-部门-根据id删除数据信息", nickname = "笑小枫-www.xiaoxiaofeng.com")
    @DeleteMapping("/{id}")
    public Integer deleteDept(@PathVariable("id") Long id) {
        return deptService.deleteDept(id);
    }
}
