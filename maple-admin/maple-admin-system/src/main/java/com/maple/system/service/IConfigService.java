package com.maple.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.vo.query.ConfigPageQuery;
import com.maple.system.vo.model.ConfigModel;

/**
 * 系统管理-参数配置Service接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
public interface IConfigService {
   
    /**
     * 分页查询系统管理-参数配置列表
     *
     * @param query 请求参数
     * @return 系统管理-参数配置集合
     */
    IPage<ConfigModel> getPageList(ConfigPageQuery query);

    /**
     * 根据id查询系统管理-参数配置详情
     *
     * @param id 系统管理-参数配置ID
     * @return 系统管理-参数配置
     */
    ConfigModel getConfigById(Long id);

    /**
     * 新增系统管理-参数配置信息
     *
     * @param model 系统管理-参数配置信息
     * @return 新增后的ID
     */
    Long createConfig(ConfigModel model);
    
    /**
     * 修改系统管理-参数配置信息
     *
     * @param model 系统管理-参数配置信息
     */
    void updateConfig(ConfigModel model);

    /**
     * 删除系统管理-参数配置信息
     * 
     * @param id 系统管理-参数配置ID
     * @return 删除数量
     */
    Integer deleteConfig(Long id);
}
