package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebResourcePageQuery;
import com.maple.website.vo.model.WebResourceModel;

/**
 * 网站资源Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebResourceService {
   
    /**
     * 分页查询网站资源列表
     *
     * @param query 请求参数
     * @return 网站资源集合
     */
    IPage<WebResourceModel> getPageList(WebResourcePageQuery query);

    /**
     * 根据id查询网站资源详情
     *
     * @param id 网站资源ID
     * @return 网站资源
     */
    WebResourceModel getWebResourceById(Long id);

    /**
     * 新增网站资源信息
     *
     * @param model 网站资源信息
     * @return 新增后的ID
     */
    Long createWebResource(WebResourceModel model);
    
    /**
     * 修改网站资源信息
     *
     * @param model 网站资源信息
     */
    void updateWebResource(WebResourceModel model);

    /**
     * 删除网站资源信息
     * 
     * @param id 网站资源ID
     * @return 删除数量
     */
    Integer deleteWebResource(Long id);
}
