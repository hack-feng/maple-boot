package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserPageQuery;
import com.maple.website.vo.model.WebUserModel;

/**
 * 网站用户Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebUserService {
   
    /**
     * 分页查询网站用户列表
     *
     * @param query 请求参数
     * @return 网站用户集合
     */
    IPage<WebUserModel> getPageList(WebUserPageQuery query);

    /**
     * 根据id查询网站用户详情
     *
     * @param id 网站用户ID
     * @return 网站用户
     */
    WebUserModel getWebUserById(Long id);

    /**
     * 新增网站用户信息
     *
     * @param model 网站用户信息
     * @return 新增后的ID
     */
    Long createWebUser(WebUserModel model);
    
    /**
     * 修改网站用户信息
     *
     * @param model 网站用户信息
     */
    void updateWebUser(WebUserModel model);

    /**
     * 删除网站用户信息
     * 
     * @param id 网站用户ID
     * @return 删除数量
     */
    Integer deleteWebUser(Long id);
}
