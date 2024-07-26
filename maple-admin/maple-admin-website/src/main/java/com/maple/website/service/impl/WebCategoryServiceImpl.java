package com.maple.website.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.lucene.LuceneDataModel;
import com.maple.common.lucene.LuceneUtils;
import com.maple.common.model.IdNumList;
import com.maple.common.util.TransformUtils;
import com.maple.website.bean.WebCategory;
import com.maple.website.mapper.WebArticleMapper;
import com.maple.website.mapper.WebCategoryMapper;
import com.maple.website.service.IWebCategoryService;
import com.maple.website.vo.model.WebCategoryModel;
import com.maple.website.vo.query.WebCategoryPageQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private final WebArticleMapper webArticleMapper;
    
    private final LuceneUtils luceneUtils;

    @Override
    public IPage<WebCategoryModel> getPageCategory(WebCategoryPageQuery query) {
        return webCategoryMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public List<WebCategoryModel> getTreeList(WebCategoryModel webCategory) {
        List<WebCategoryModel> list = webCategoryMapper.getTreeList(webCategory);
        if (StringUtils.isNotBlank(webCategory.getName())) {
            return list;
        }
        return getChildPerms(list, 0L);
    }

    @Override
    public WebCategoryModel getCategoryInfo(Long id) {
        WebCategory category = webCategoryMapper.selectById(id);
        if (Objects.isNull(category)) {
            return null;
        }
        WebCategoryModel temp = webArticleMapper.selectReadAndCollectNum(id);
        WebCategoryModel categoryVo = TransformUtils.map(category, WebCategoryModel.class);
        categoryVo.setTitleNum(temp.getTitleNum());
        categoryVo.setCollectNum(temp.getCollectNum());
        categoryVo.setReadNum(temp.getReadNum());
        return categoryVo;
    }

    @Override
    public WebCategoryModel getWebCategoryById(Long id) {
        return TransformUtils.map(webCategoryMapper.selectById(id), WebCategoryModel.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createWebCategory(WebCategoryModel model) {
        WebCategory webCategory = TransformUtils.map(model, WebCategory.class);
        webCategoryMapper.insert(webCategory);

        luceneUtils.addLuceneData(LuceneDataModel.builder()
                .id(webCategory.getId())
                .type(0)
                .title(webCategory.getName())
                .description(webCategory.getDescription())
                .imageUrl(webCategory.getIcon())
                .build());
        return webCategory.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebCategory(WebCategoryModel model) {
        webCategoryMapper.updateById(TransformUtils.map(model, WebCategory.class));
        luceneUtils.updateLuceneData(LuceneDataModel.builder()
                .id(model.getId())
                .type(0)
                .title(model.getName())
                .description(model.getDescription())
                .imageUrl(model.getIcon())
                .build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWebCategory(Long id) {
        int count = webCategoryMapper.deleteById(id);
        luceneUtils.deleteLuceneData(LuceneDataModel.builder()
                .id(id)
                .type(0)
                .build());
        return count;
    }

    @Override
    public List<WebCategoryModel> getCategoryList(WebCategoryModel query) {
        if (Objects.isNull(query)) {
            query = new WebCategoryModel();
            query.setParentId(-1L);
        }
        List<WebCategory> list = webCategoryMapper.selectList(Wrappers.lambdaQuery(WebCategory.class)
                .eq(Objects.nonNull(query.getParentId()), WebCategory::getParentId, query.getParentId())
                .eq(WebCategory::getIsValid, true)
                .orderByDesc(WebCategory::getIsTop)
                .orderByAsc(WebCategory::getSortNum)
                .orderByDesc(WebCategory::getId));
        List<WebCategoryModel> result = TransformUtils.mapList(list, WebCategoryModel.class);

        List<IdNumList> idNumLists = webCategoryMapper.selectCountGroupByCategory();
        Map<Long, Long> categoryMap = idNumLists.stream().collect(Collectors.toMap(IdNumList::getId, IdNumList::getNum));
        for (WebCategoryModel categoryModel : result) {
            categoryModel.setTitleNum(categoryMap.get(categoryModel.getId()));
        }
        return result;
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
