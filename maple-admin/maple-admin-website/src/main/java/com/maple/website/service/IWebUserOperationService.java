package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.bean.WebUserOperation;
import com.maple.website.common.enums.DataTypeEnum;
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
     * 收藏/取消收藏
     *
     * @param dataId    数据ID
     * @param isCollect 是否收藏
     * @param typeEnum  数据类型
     */
    void collect(Long dataId, Boolean isCollect, Integer typeEnum);

    /**
     * 点赞/取消收藏
     */
    void like(Long dataId, Boolean isLike, Integer typeEnum);

    /**
     * 阅读记录
     *
     * @param dataId   数据ID
     * @param typeEnum 数据类型
     */
    void read(Long dataId, Integer typeEnum);

    /**
     * 解锁记录
     *
     * @param model    数据详情
     * @param typeEnum 数据类型
     */
    void download(WebUserOperationModel model, Integer typeEnum);
}
