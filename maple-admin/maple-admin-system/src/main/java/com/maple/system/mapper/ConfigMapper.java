package com.maple.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.system.bean.Config;
import com.maple.system.vo.model.ConfigModel;
import org.apache.ibatis.annotations.Param;

/**
 * 系统管理-参数配置Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
public interface ConfigMapper extends BaseMapper<Config>{
    /**
     * 查询系统管理-参数配置列表
     * 
     * @param page 分页信息
     * @param config 系统管理-参数配置
     * @return 系统管理-参数配置集合
     */
    IPage<ConfigModel> getPageList(@Param("page") Page<ConfigModel> page, @Param("config") ConfigModel config, @Param("dataScope") String dataScope);
}
