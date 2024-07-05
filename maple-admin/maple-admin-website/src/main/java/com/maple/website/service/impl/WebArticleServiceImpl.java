package com.maple.website.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.TransformUtils;
import com.maple.website.bean.WebArticle;
import com.maple.website.bean.WebCategory;
import com.maple.website.bean.WebContent;
import com.maple.website.bean.WebUserOperation;
import com.maple.website.common.enums.DataTypeEnum;
import com.maple.website.mapper.WebArticleMapper;
import com.maple.website.mapper.WebCategoryMapper;
import com.maple.website.mapper.WebContentMapper;
import com.maple.website.mapper.WebUserOperationMapper;
import com.maple.website.service.IWebArticleService;
import com.maple.website.service.IWebUserOperationService;
import com.maple.website.vo.model.WebArticleModel;
import com.maple.website.vo.model.WebCategoryModel;
import com.maple.website.vo.model.WebContentModel;
import com.maple.website.vo.query.WebArticlePageQuery;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.maple.common.config.MapleConstants.LAST_LIMIT_1;

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

    private final WebContentMapper webContentMapper;

    private final WebUserOperationMapper userOperationMapper;

    private final WebCategoryMapper categoryMapper;

    private final IWebUserOperationService userOperationService;

    @Override
    public IPage<WebArticleModel> getPageList(WebArticlePageQuery query) {
        return webArticleMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebArticleModel getWebArticleById(Long id, Boolean isWebUser) {
        WebArticleModel articleModel = getTitleInfoById(id, isWebUser);
        if (Objects.isNull(articleModel) || Objects.isNull(articleModel.getId())) {
            return articleModel;
        }
        if (BooleanUtils.isTrue(isWebUser)) {
            webArticleMapper.update(null, Wrappers.lambdaUpdate(WebArticle.class)
                    .eq(WebArticle::getId, articleModel.getId())
                    .set(WebArticle::getReadNum, articleModel.getReadNum() + 1));
            if (Objects.nonNull(JwtUtil.getUserId())) {
                userOperationService.read(articleModel.getId(), articleModel.getArticleType());
            }
        }
        return articleModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createWebArticle(WebArticleModel model) {
        WebArticle webArticle = TransformUtils.map(model, WebArticle.class);
        if (Objects.isNull(webArticle.getSortNum())) {
            webArticle.setSortNum(webArticleMapper.getMaxSortNum(webArticle.getCategoryId()));
        }
        webArticleMapper.insert(webArticle);

        WebContent content = TransformUtils.map(model.getContentModel(), WebContent.class);
        content.setArticleId(webArticle.getId());
        webContentMapper.insert(content);
        return webArticle.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebArticle(WebArticleModel model) {
        webArticleMapper.updateById(TransformUtils.map(model, WebArticle.class));
        webContentMapper.updateById(TransformUtils.map(model.getContentModel(), WebContent.class));
    }

    @Override
    public Integer deleteWebArticle(Long id) {
        WebArticle article = webArticleMapper.selectById(id);
        article.setStatus(4);
        return webArticleMapper.updateById(article);
    }

    @Override
    public WebArticleModel getById(Long id) {
        return TransformUtils.map(webArticleMapper.selectById(id), WebArticleModel.class);
    }

    public WebArticleModel getTitleInfoById(Long id, Boolean isWebUser) {
        WebArticle article = webArticleMapper.selectById(id);
        WebContent content = webContentMapper.selectOne(Wrappers.lambdaQuery(WebContent.class)
                .eq(WebContent::getArticleId, id));
        if (Objects.isNull(article)) {
            return new WebArticleModel();
        }

        WebArticleModel articleModel = TransformUtils.map(article, WebArticleModel.class);
        if (Objects.nonNull(article.getRelevanceArticleId())) {
            WebContent relevanceContent = webContentMapper.selectOne(Wrappers.lambdaQuery(WebContent.class)
                    .eq(WebContent::getArticleId, article.getRelevanceArticleId()));
            if (Objects.nonNull(relevanceContent)) {
                articleModel.setRelevanceContent(relevanceContent.getOriginalContent());
            }
        }
        if (BooleanUtils.isTrue(isWebUser)) {
            WebUserOperation operation = userOperationMapper.selectOne(Wrappers.lambdaQuery(WebUserOperation.class)
                    .eq(WebUserOperation::getDataId, id)
                    .eq(WebUserOperation::getDataType, DataTypeEnum.ARTICLE.getCode())
                    .eq(WebUserOperation::getUserId, JwtUtil.getUserId())
                    .last(LAST_LIMIT_1));
            if (Objects.nonNull(operation)) {
                articleModel.setIsCollect(operation.getIsCollect());
                articleModel.setIsLike(operation.getIsLike());
            }
            // 获取上一篇、下一篇文章
            articleModel.setPreTitle(webArticleMapper.getPreviousTitle(articleModel));
            articleModel.setNextTitle(webArticleMapper.getNextTitle(articleModel));
        }
        articleModel.setContentModel(TransformUtils.map(content, WebContentModel.class));
        WebCategory blogCategory = categoryMapper.selectById(article.getCategoryId());
        articleModel.setCategoryModel(TransformUtils.map(blogCategory, WebCategoryModel.class));
        return articleModel;
    }

}
