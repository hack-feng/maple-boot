package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebResourcePageQuery;
import com.maple.website.bean.WebResource;
import com.maple.website.vo.model.WebResourceModel;
import com.maple.website.mapper.WebResourceMapper;
import com.maple.website.service.IWebResourceService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站资源Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebResourceServiceImpl extends ServiceImpl<WebResourceMapper, WebResource> implements IWebResourceService {
    
    private final WebResourceMapper webResourceMapper;

    @Override
    public IPage<WebResourceModel> getPageList(WebResourcePageQuery query) {
        return webResourceMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebResourceModel getWebResourceById(Long id) {
        return TransformUtils.map(webResourceMapper.selectById(id), WebResourceModel.class);
    }

    @Override
    public Long createWebResource(WebResourceModel model) {
        WebResource webResource = TransformUtils.map(model, WebResource.class);
        webResourceMapper.insert(webResource);
        return webResource.getId();
    }

    @Override
    public void updateWebResource(WebResourceModel model) {
        webResourceMapper.updateById(TransformUtils.map(model, WebResource.class));
    }
    
    @Override
    public Integer deleteWebResource(Long id) {
        return webResourceMapper.deleteById(id);
    }

}
