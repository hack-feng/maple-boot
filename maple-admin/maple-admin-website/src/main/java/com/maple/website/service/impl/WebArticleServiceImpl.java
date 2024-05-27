package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebArticlePageQuery;
import com.maple.website.bean.WebArticle;
import com.maple.website.vo.model.WebArticleModel;
import com.maple.website.mapper.WebArticleMapper;
import com.maple.website.service.IWebArticleService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站文章Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebArticleServiceImpl extends ServiceImpl<WebArticleMapper, WebArticle> implements IWebArticleService {
    
    private final WebArticleMapper webArticleMapper;

    @Override
    public IPage<WebArticleModel> getPageList(WebArticlePageQuery query) {
        return webArticleMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebArticleModel getWebArticleById(Long id) {
        return TransformUtils.map(webArticleMapper.selectById(id), WebArticleModel.class);
    }

    @Override
    public Long createWebArticle(WebArticleModel model) {
        WebArticle webArticle = TransformUtils.map(model, WebArticle.class);
        webArticleMapper.insert(webArticle);
        return webArticle.getId();
    }

    @Override
    public void updateWebArticle(WebArticleModel model) {
        webArticleMapper.updateById(TransformUtils.map(model, WebArticle.class));
    }
    
    @Override
    public Integer deleteWebArticle(Long id) {
        return webArticleMapper.deleteById(id);
    }

}
