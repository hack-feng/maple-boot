package com.maple.website.service;

import java.util.List;
import com.maple.website.vo.model.WebCategoryModel;

/**
 * 网站类目Service接口
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
public interface IWebCategoryService {

    /**
     * 查询网站类目树结构
     *
     * @param model 请求参数
     * @return 网站类目集合
     */
    List<WebCategoryModel> getTreeList(WebCategoryModel model);

    /**
     * 根据id查询网站类目详情
     *
     * @param id 网站类目ID
     * @return 网站类目
     */
    WebCategoryModel getWebCategoryById(Long id);


    /**
     * 获取类目详情
     *
     * @param id 主键ID
     * @return 类目信息
     */
    WebCategoryModel getCategoryInfo(Long id);

    /**
     * 新增网站类目信息
     *
     * @param model 网站类目信息
     * @return 新增后的ID
     */
    Long createWebCategory(WebCategoryModel model);

    /**
     * 修改网站类目信息
     *
     * @param model 网站类目信息
     */
    void updateWebCategory(WebCategoryModel model);

    /**
     * 删除网站类目信息
     *
     * @param id 网站类目ID
     * @return 删除数量
     */
    Integer deleteWebCategory(Long id);

    /**
     * 获取类目信息
     *
     * @param query 请求参数
     * @return 类目信息
     */
    List<WebCategoryModel> getCategoryList(WebCategoryModel query);
}
