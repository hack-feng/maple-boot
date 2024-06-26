package com.maple.website.service;

import java.util.List;

import com.maple.website.vo.model.WebMenuModel;

/**
 * 网站菜单Service接口
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
public interface IWebMenuService {

    /**
     * 查询网站菜单树结构
     *
     * @param model 请求参数
     * @return 网站菜单集合
     */
    List<WebMenuModel> getTreeList(WebMenuModel model);

    /**
     * 根据id查询网站菜单详情
     *
     * @param id 网站菜单ID
     * @return 网站菜单
     */
    WebMenuModel getWebMenuById(Long id);

    /**
     * 新增网站菜单信息
     *
     * @param model 网站菜单信息
     * @return 新增后的ID
     */
    Long createWebMenu(WebMenuModel model);

    /**
     * 修改网站菜单信息
     *
     * @param model 网站菜单信息
     */
    void updateWebMenu(WebMenuModel model);

    /**
     * 删除网站菜单信息
     *
     * @param id 网站菜单ID
     * @return 删除数量
     */
    Integer deleteWebMenu(Long id);

    /**
     * 根据path获取对应菜单信息
     *
     * @param path 路径，唯一
     * @return 菜单信息
     */
    WebMenuModel getWebMenuByPath(String path);
}
