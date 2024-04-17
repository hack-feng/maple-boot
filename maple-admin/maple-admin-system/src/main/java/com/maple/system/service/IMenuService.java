package com.maple.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.system.bean.Menu;
import com.maple.system.vo.model.MenuModel;
import com.maple.system.vo.model.RouterModel;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 用户中心-菜单权限表 服务类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 获取用户拥有的菜单信息
     *
     * @param userId 用户ID
     * @return 用户拥有的菜单信息
     */
    List<RouterModel> selectMenuTreeByUserId(Long userId);

    /**
     * 根据用户ID查询用户拥有的菜单
     *
     * @param userId 用户ID
     * @return 用户拥有的菜单
     */
    Set<String> selectMenuPermsByUserId(Long userId);
    
    /**
     * 查询用户中心-菜单权限树结构
     *
     * @param model 请求参数
     * @return 用户中心-菜单权限集合
     */
    List<MenuModel> getTreeList(MenuModel model);

    /**
     * 根据id查询用户中心-菜单权限详情
     *
     * @param id 用户中心-菜单权限ID
     * @return 用户中心-菜单权限
     */
    MenuModel getMenuById(Long id);

    /**
     * 新增用户中心-菜单权限信息
     *
     * @param model 用户中心-菜单权限信息
     * @return 新增后的ID
     */
    Long createMenu(MenuModel model);

    /**
     * 修改用户中心-菜单权限信息
     *
     * @param model 用户中心-菜单权限信息
     */
    void updateMenu(MenuModel model);

    /**
     * 删除用户中心-菜单权限信息
     *
     * @param id 用户中心-菜单权限ID
     * @return 删除数量
     */
    Integer deleteMenu(Long id);

}
