package com.maple.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.vo.model.RoleModel;
import com.maple.system.vo.query.RolePageQuery;

import java.util.List;

/**
 * 用户中心-角色信息Service接口
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-27
 */
public interface IRoleService {

    /**
     * 分页查询用户中心-角色信息列表
     *
     * @param query 请求参数
     * @return 用户中心-角色信息集合
     */
    IPage<RoleModel> getPageList(RolePageQuery query);

    /**
     * 获取所有有效的角色列表
     * @return 角色信息集合
     */
    List<RoleModel> getRoleList();

    /**
     * 根据id查询用户中心-角色信息详情
     *
     * @param id 用户中心-角色信息ID
     * @return 用户中心-角色信息
     */
    RoleModel getRoleById(Long id);

    /**
     * 新增用户中心-角色信息信息
     *
     * @param model 用户中心-角色信息信息
     * @return 新增后的ID
     */
    Long createRole(RoleModel model);

    /**
     * 修改用户中心-角色信息信息
     *
     * @param model 用户中心-角色信息信息
     */
    void updateRole(RoleModel model);

    /**
     * 删除用户中心-角色信息信息
     *
     * @param id 用户中心-角色信息ID
     * @return 删除数量
     */
    Integer deleteRole(Long id);
}
