package com.maple.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.model.WebConfigModel;
import com.maple.website.vo.query.WebConfigPageQuery;

import java.util.List;

/**
 * 网站配置Service接口
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface IWebConfigService {

    /**
     * 分页查询网站配置列表
     *
     * @param query 请求参数
     * @return 网站配置集合
     */
    IPage<WebConfigModel> getPageList(WebConfigPageQuery query);

    /**
     * 查询网站配置列表
     *
     * @return 网站配置集合
     */
    List<WebConfigModel> getAllConfigList();

    /**
     * 根据id查询网站配置详情
     *
     * @param id 网站配置ID
     * @return 网站配置
     */
    WebConfigModel getWebConfigById(Long id);

    /**
     * 新增网站配置信息
     *
     * @param model 网站配置信息
     * @return 新增后的ID
     */
    Long createWebConfig(WebConfigModel model);

    /**
     * 修改网站配置信息
     *
     * @param model 网站配置信息
     */
    void updateWebConfig(WebConfigModel model);

    /**
     * 删除网站配置信息
     *
     * @param id 网站配置ID
     * @return 删除数量
     */
    Integer deleteWebConfig(Long id);
}
