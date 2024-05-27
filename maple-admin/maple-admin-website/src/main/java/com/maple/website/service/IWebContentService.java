package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebContentPageQuery;
import com.maple.website.vo.model.WebContentModel;

/**
 * 网站文章内容Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebContentService {
   
    /**
     * 分页查询网站文章内容列表
     *
     * @param query 请求参数
     * @return 网站文章内容集合
     */
    IPage<WebContentModel> getPageList(WebContentPageQuery query);

    /**
     * 根据id查询网站文章内容详情
     *
     * @param id 网站文章内容ID
     * @return 网站文章内容
     */
    WebContentModel getWebContentById(Long id);

    /**
     * 新增网站文章内容信息
     *
     * @param model 网站文章内容信息
     * @return 新增后的ID
     */
    Long createWebContent(WebContentModel model);
    
    /**
     * 修改网站文章内容信息
     *
     * @param model 网站文章内容信息
     */
    void updateWebContent(WebContentModel model);

    /**
     * 删除网站文章内容信息
     * 
     * @param id 网站文章内容ID
     * @return 删除数量
     */
    Integer deleteWebContent(Long id);
}
