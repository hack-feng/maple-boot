package com.maple.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.vo.query.ConfigPageQuery;
import com.maple.system.bean.Config;
import com.maple.system.vo.model.ConfigModel;
import com.maple.system.mapper.ConfigMapper;
import com.maple.system.service.IConfigService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 系统管理-参数配置Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
@Service
@AllArgsConstructor
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {
    
    private final ConfigMapper configMapper;

    @Override
    public IPage<ConfigModel> getPageList(ConfigPageQuery query) {
        return configMapper.getPageList(query.getPage(), query.getQuery(), query.getDataScope());
    }

    @Override
    public ConfigModel getConfigById(Long id) {
        return TransformUtils.map(configMapper.selectById(id), ConfigModel.class);
    }

    @Override
    public Long createConfig(ConfigModel model) {
        Config config = TransformUtils.map(model, Config.class);
        configMapper.insert(config);
        return config.getId();
    }

    @Override
    public void updateConfig(ConfigModel model) {
        configMapper.updateById(TransformUtils.map(model, Config.class));
    }
    
    @Override
    public Integer deleteConfig(Long id) {
        return configMapper.deleteById(id);
    }

}
