package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebCategoryPageQuery;
import com.maple.website.bean.WebCategory;
import com.maple.website.vo.model.WebCategoryModel;
import com.maple.website.mapper.WebCategoryMapper;
import com.maple.website.service.IWebCategoryService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站类目Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebCategoryServiceImpl extends ServiceImpl<WebCategoryMapper, WebCategory> implements IWebCategoryService {
    
    private final WebCategoryMapper webCategoryMapper;

    @Override
    public IPage<WebCategoryModel> getPageList(WebCategoryPageQuery query) {
        return webCategoryMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebCategoryModel getWebCategoryById(Long id) {
        return TransformUtils.map(webCategoryMapper.selectById(id), WebCategoryModel.class);
    }

    @Override
    public Long createWebCategory(WebCategoryModel model) {
        WebCategory webCategory = TransformUtils.map(model, WebCategory.class);
        webCategoryMapper.insert(webCategory);
        return webCategory.getId();
    }

    @Override
    public void updateWebCategory(WebCategoryModel model) {
        webCategoryMapper.updateById(TransformUtils.map(model, WebCategory.class));
    }
    
    @Override
    public Integer deleteWebCategory(Long id) {
        return webCategoryMapper.deleteById(id);
    }

}
