package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebFriendlyLinkPageQuery;
import com.maple.website.vo.model.WebFriendlyLinkModel;

/**
 * 网站链接Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebFriendlyLinkService {
   
    /**
     * 分页查询网站链接列表
     *
     * @param query 请求参数
     * @return 网站链接集合
     */
    IPage<WebFriendlyLinkModel> getPageList(WebFriendlyLinkPageQuery query);

    /**
     * 根据id查询网站链接详情
     *
     * @param id 网站链接ID
     * @return 网站链接
     */
    WebFriendlyLinkModel getWebFriendlyLinkById(Long id);

    /**
     * 新增网站链接信息
     *
     * @param model 网站链接信息
     * @return 新增后的ID
     */
    Long createWebFriendlyLink(WebFriendlyLinkModel model);
    
    /**
     * 修改网站链接信息
     *
     * @param model 网站链接信息
     */
    void updateWebFriendlyLink(WebFriendlyLinkModel model);

    /**
     * 删除网站链接信息
     * 
     * @param id 网站链接ID
     * @return 删除数量
     */
    Integer deleteWebFriendlyLink(Long id);
}
