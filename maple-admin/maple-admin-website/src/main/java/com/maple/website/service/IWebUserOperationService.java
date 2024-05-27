package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserOperationPageQuery;
import com.maple.website.vo.model.WebUserOperationModel;

/**
 * 网站用户操作Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebUserOperationService {
   
    /**
     * 分页查询网站用户操作列表
     *
     * @param query 请求参数
     * @return 网站用户操作集合
     */
    IPage<WebUserOperationModel> getPageList(WebUserOperationPageQuery query);

    /**
     * 根据id查询网站用户操作详情
     *
     * @param id 网站用户操作ID
     * @return 网站用户操作
     */
    WebUserOperationModel getWebUserOperationById(Long id);

    /**
     * 新增网站用户操作信息
     *
     * @param model 网站用户操作信息
     * @return 新增后的ID
     */
    Long createWebUserOperation(WebUserOperationModel model);
    
    /**
     * 修改网站用户操作信息
     *
     * @param model 网站用户操作信息
     */
    void updateWebUserOperation(WebUserOperationModel model);

    /**
     * 删除网站用户操作信息
     * 
     * @param id 网站用户操作ID
     * @return 删除数量
     */
    Integer deleteWebUserOperation(Long id);
}
