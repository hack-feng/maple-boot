package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.model.WebArticleModel;
import com.maple.website.vo.home.HomeData;
import com.maple.website.vo.query.WebArticlePageQuery;

import javax.servlet.http.HttpServletResponse;

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
     * @param id        网站文章ID
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

    /**
     * 根据ID获取文章信息
     *
     * @param id 文章ID
     * @return 文章信息
     */
    WebArticleModel getById(Long id);

    /**
     * 获取首页数据
     *
     * @return 首页数据
     */
    HomeData getHomeData();

    /**
     * 下载资源
     * @param response   返回文件
     * @param articleModel 下载数据
     */
    void downResource(HttpServletResponse response, WebArticleModel articleModel);
}
