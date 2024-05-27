package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebConfigPageQuery;
import com.maple.website.vo.model.WebConfigModel;

/**
 * 网站用户Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebConfigService {
   
    /**
     * 分页查询网站用户列表
     *
     * @param query 请求参数
     * @return 网站用户集合
     */
    IPage<WebConfigModel> getPageList(WebConfigPageQuery query);

    /**
     * 根据id查询网站用户详情
     *
     * @param id 网站用户ID
     * @return 网站用户
     */
    WebConfigModel getWebConfigById(Long id);

    /**
     * 新增网站用户信息
     *
     * @param model 网站用户信息
     * @return 新增后的ID
     */
    Long createWebConfig(WebConfigModel model);
    
    /**
     * 修改网站用户信息
     *
     * @param model 网站用户信息
     */
    void updateWebConfig(WebConfigModel model);

    /**
     * 删除网站用户信息
     * 
     * @param id 网站用户ID
     * @return 删除数量
     */
    Integer deleteWebConfig(Long id);
}
