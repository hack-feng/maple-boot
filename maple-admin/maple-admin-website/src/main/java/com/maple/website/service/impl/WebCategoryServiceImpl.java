package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.website.bean.WebCategory;
import com.maple.website.vo.model.WebCategoryModel;
import com.maple.website.mapper.WebCategoryMapper;
import com.maple.website.service.IWebCategoryService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网站类目Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
@Service
@AllArgsConstructor
public class WebCategoryServiceImpl extends ServiceImpl<WebCategoryMapper, WebCategory> implements IWebCategoryService {

    private final WebCategoryMapper webCategoryMapper;

    @Override
    public List<WebCategoryModel> getTreeList(WebCategoryModel webCategory) {
        List<WebCategoryModel> list = webCategoryMapper.getTreeList(webCategory);
        return getChildPerms(list, 0L);
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


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<WebCategoryModel> getChildPerms(List<WebCategoryModel> list, Long parentId) {
        List<WebCategoryModel> returnList = new ArrayList<>();
        for (WebCategoryModel model : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (model.getParentId().equals(parentId)) {
                recursionFn(list, model);
                returnList.add(model);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<WebCategoryModel> list, WebCategoryModel model) {
        // 得到子节点列表
        List<WebCategoryModel> childList = getChildList(list, model);
        model.setChildren(childList);
        for (WebCategoryModel child : childList) {
            // 判断是否有子节点
            if (!CollectionUtils.isEmpty(getChildList(list, model))) {
                recursionFn(list, child);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<WebCategoryModel> getChildList(List<WebCategoryModel> list, WebCategoryModel model) {
        List<WebCategoryModel> modelList = new ArrayList<>();
        for (WebCategoryModel n : list) {
            if (n.getParentId().equals(model.getId())) {
                modelList.add(n);
            }
        }
        return modelList;
    }
}
