package com.maple.system.service;

import com.maple.system.vo.model.DeptModel;

import java.util.List;

/**
 * 用户中心-部门Service接口
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-25
 */
public interface IDeptService {

    /**
     * 查询用户中心-部门树结构
     *
     * @param model 请求参数
     * @return 用户中心-部门集合
     */
    List<DeptModel> getTreeList(DeptModel model);

    /**
     * 根据id查询用户中心-部门详情
     *
     * @param id 用户中心-部门ID
     * @return 用户中心-部门
     */
    DeptModel getDeptById(Long id);

    /**
     * 新增用户中心-部门信息
     *
     * @param model 用户中心-部门信息
     * @return 新增后的ID
     */
    Long createDept(DeptModel model);

    /**
     * 修改用户中心-部门信息
     *
     * @param model 用户中心-部门信息
     */
    void updateDept(DeptModel model);

    /**
     * 删除用户中心-部门信息
     *
     * @param id 用户中心-部门ID
     * @return 删除数量
     */
    Integer deleteDept(Long id);
}
