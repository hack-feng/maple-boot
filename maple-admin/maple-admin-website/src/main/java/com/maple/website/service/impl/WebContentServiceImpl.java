package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebContentPageQuery;
import com.maple.website.bean.WebContent;
import com.maple.website.vo.model.WebContentModel;
import com.maple.website.mapper.WebContentMapper;
import com.maple.website.service.IWebContentService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站文章内容Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebContentServiceImpl extends ServiceImpl<WebContentMapper, WebContent> implements IWebContentService {
    
    private final WebContentMapper webContentMapper;

    @Override
    public IPage<WebContentModel> getPageList(WebContentPageQuery query) {
        return webContentMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebContentModel getWebContentById(Long id) {
        return TransformUtils.map(webContentMapper.selectById(id), WebContentModel.class);
    }

    @Override
    public Long createWebContent(WebContentModel model) {
        WebContent webContent = TransformUtils.map(model, WebContent.class);
        webContentMapper.insert(webContent);
        return webContent.getId();
    }

    @Override
    public void updateWebContent(WebContentModel model) {
        webContentMapper.updateById(TransformUtils.map(model, WebContent.class));
    }
    
    @Override
    public Integer deleteWebContent(Long id) {
        return webContentMapper.deleteById(id);
    }

}
