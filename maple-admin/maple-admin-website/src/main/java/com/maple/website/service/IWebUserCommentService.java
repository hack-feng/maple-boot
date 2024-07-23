package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserCommentPageQuery;
import com.maple.website.vo.model.WebUserCommentModel;

/**
 * 网站用户评论Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebUserCommentService {
   
    /**
     * 分页查询网站用户评论列表
     *
     * @param query 请求参数
     * @return 网站用户评论集合
     */
    IPage<WebUserCommentModel> getPageList(WebUserCommentPageQuery query);

    /**
     * 根据id查询网站用户评论详情
     *
     * @param id 网站用户评论ID
     * @return 网站用户评论
     */
    WebUserCommentModel getWebUserCommentById(Long id);

    /**
     * 新增网站用户评论信息
     *
     * @param model 网站用户评论信息
     * @return 新增后的ID
     */
    WebUserCommentModel createWebUserComment(WebUserCommentModel model);

    /**
     * 删除网站用户评论信息
     * 
     * @param id 网站用户评论ID
     * @return 删除数量
     */
    Integer deleteWebUserComment(Long id);
}
