package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebConfigPageQuery;
import com.maple.website.bean.WebConfig;
import com.maple.website.vo.model.WebConfigModel;
import com.maple.website.mapper.WebConfigMapper;
import com.maple.website.service.IWebConfigService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站用户Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements IWebConfigService {
    
    private final WebConfigMapper webConfigMapper;

    @Override
    public IPage<WebConfigModel> getPageList(WebConfigPageQuery query) {
        return webConfigMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebConfigModel getWebConfigById(Long id) {
        return TransformUtils.map(webConfigMapper.selectById(id), WebConfigModel.class);
    }

    @Override
    public Long createWebConfig(WebConfigModel model) {
        WebConfig webConfig = TransformUtils.map(model, WebConfig.class);
        webConfigMapper.insert(webConfig);
        return webConfig.getId();
    }

    @Override
    public void updateWebConfig(WebConfigModel model) {
        webConfigMapper.updateById(TransformUtils.map(model, WebConfig.class));
    }
    
    @Override
    public Integer deleteWebConfig(Long id) {
        return webConfigMapper.deleteById(id);
    }

}
