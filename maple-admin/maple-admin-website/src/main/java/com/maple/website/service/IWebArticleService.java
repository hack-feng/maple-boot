package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebArticlePageQuery;
import com.maple.website.vo.model.WebArticleModel;

/**
 * 网站文章Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebArticleService {
   
    /**
     * 分页查询网站文章列表
     *
     * @param query 请求参数
     * @return 网站文章集合
     */
    IPage<WebArticleModel> getPageList(WebArticlePageQuery query);

    /**
     * 根据id查询网站文章详情
     *
     * @param id 网站文章ID
     * @param isWebUser 是否网站用户
     * @return 网站文章
     */
    WebArticleModel getWebArticleById(Long id, Boolean isWebUser);

    /**
     * 新增网站文章信息
     *
     * @param model 网站文章信息
     * @return 新增后的ID
     */
    Long createWebArticle(WebArticleModel model);
    
    /**
     * 修改网站文章信息
     *
     * @param model 网站文章信息
     */
    void updateWebArticle(WebArticleModel model);

    /**
     * 删除网站文章信息
     * 
     * @param id 网站文章ID
     * @return 删除数量
     */
    Integer deleteWebArticle(Long id);
}
