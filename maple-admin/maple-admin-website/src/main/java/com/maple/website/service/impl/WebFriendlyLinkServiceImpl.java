package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebFriendlyLinkPageQuery;
import com.maple.website.bean.WebFriendlyLink;
import com.maple.website.vo.model.WebFriendlyLinkModel;
import com.maple.website.mapper.WebFriendlyLinkMapper;
import com.maple.website.service.IWebFriendlyLinkService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站链接Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebFriendlyLinkServiceImpl extends ServiceImpl<WebFriendlyLinkMapper, WebFriendlyLink> implements IWebFriendlyLinkService {
    
    private final WebFriendlyLinkMapper webFriendlyLinkMapper;

    @Override
    public IPage<WebFriendlyLinkModel> getPageList(WebFriendlyLinkPageQuery query) {
        return webFriendlyLinkMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebFriendlyLinkModel getWebFriendlyLinkById(Long id) {
        return TransformUtils.map(webFriendlyLinkMapper.selectById(id), WebFriendlyLinkModel.class);
    }

    @Override
    public Long createWebFriendlyLink(WebFriendlyLinkModel model) {
        WebFriendlyLink webFriendlyLink = TransformUtils.map(model, WebFriendlyLink.class);
        webFriendlyLinkMapper.insert(webFriendlyLink);
        return webFriendlyLink.getId();
    }

    @Override
    public void updateWebFriendlyLink(WebFriendlyLinkModel model) {
        webFriendlyLinkMapper.updateById(TransformUtils.map(model, WebFriendlyLink.class));
    }
    
    @Override
    public Integer deleteWebFriendlyLink(Long id) {
        return webFriendlyLinkMapper.deleteById(id);
    }

}
