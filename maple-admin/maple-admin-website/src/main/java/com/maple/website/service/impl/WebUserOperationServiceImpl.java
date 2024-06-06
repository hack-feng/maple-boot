package com.maple.website.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.TransformUtils;
import com.maple.website.bean.WebArticle;
import com.maple.website.bean.WebUserComment;
import com.maple.website.bean.WebUserOperation;
import com.maple.website.mapper.WebArticleMapper;
import com.maple.website.mapper.WebUserCommentMapper;
import com.maple.website.mapper.WebUserOperationMapper;
import com.maple.website.service.IWebUserOperationService;
import com.maple.website.vo.model.WebUserOperationModel;
import com.maple.website.vo.query.WebUserOperationPageQuery;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 网站用户操作Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebUserOperationServiceImpl extends ServiceImpl<WebUserOperationMapper, WebUserOperation> implements IWebUserOperationService {

    private final WebUserOperationMapper userOperationMapper;

    private final WebArticleMapper articleMapper;

    private final WebUserCommentMapper commentMapper;

    @Override
    public IPage<WebUserOperationModel> getPageList(WebUserOperationPageQuery query) {
        return userOperationMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebUserOperationModel getWebUserOperationById(Long id) {
        return TransformUtils.map(userOperationMapper.selectById(id), WebUserOperationModel.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void collect(Long dataId, Boolean isCollect, Integer typeEnum) {
        WebUserOperation userOperation = userOperationMapper.selectOne(Wrappers.lambdaQuery(WebUserOperation.class)
                .eq(WebUserOperation::getDataId, dataId)
                .eq(WebUserOperation::getDataType, typeEnum)
                .eq(WebUserOperation::getUserId, JwtUtil.getUserId()));
        if (Objects.isNull(userOperation)) {
            WebUserOperation saveBean = new WebUserOperation();
            saveBean.setDataType(typeEnum);
            saveBean.setIsCollect(isCollect);
            saveBean.setDataId(dataId);
            saveBean.setUserId(JwtUtil.getUserId());
            userOperationMapper.insert(saveBean);
        } else {
            userOperation.setIsCollect(isCollect);
            userOperationMapper.updateById(userOperation);
        }
        WebArticle article = articleMapper.selectById(dataId);
        article.setCollectNum(Boolean.TRUE.equals(isCollect)
                ? article.getCollectNum() + 1 : article.getCollectNum() - 1);
        articleMapper.updateById(article);
    }

    @Override
    public void like(Long dataId, Boolean isLike, Integer typeEnum) {
        WebUserOperation userOperation = userOperationMapper.selectOne(Wrappers.lambdaQuery(WebUserOperation.class)
                .eq(WebUserOperation::getDataId, dataId)
                .eq(WebUserOperation::getDataType, typeEnum)
                .eq(WebUserOperation::getUserId, JwtUtil.getUserId()));
        if (Objects.isNull(userOperation)) {
            WebUserOperation saveBean = new WebUserOperation();
            saveBean.setDataType(typeEnum);
            saveBean.setIsLike(isLike);
            saveBean.setDataId(dataId);
            saveBean.setUserId(JwtUtil.getUserId());
            userOperationMapper.insert(saveBean);
        } else {
            userOperation.setIsLike(isLike);
            userOperationMapper.updateById(userOperation);
        }
        switch (typeEnum) {
            case 1:
            case 2:
            case 3:
                WebArticle blogArticle = articleMapper.selectById(dataId);
                blogArticle.setLikeNum(Boolean.TRUE.equals(isLike)
                        ? blogArticle.getLikeNum() + 1 : blogArticle.getLikeNum() - 1);
                articleMapper.updateById(blogArticle);
                break;
            case 4:
                WebUserComment comment = commentMapper.selectById(dataId);
                comment.setLikeNum(Boolean.TRUE.equals(isLike)
                        ? comment.getLikeNum() + 1 : comment.getLikeNum() - 1);
                commentMapper.updateById(comment);
                break;
            default:
                break;
        }
    }

    @Override
    public void read(Long dataId, Integer typeEnum) {
        if (Objects.isNull(JwtUtil.getUserId())) {
            return;
        }
        WebUserOperation userOperation = userOperationMapper.selectOne(Wrappers.lambdaQuery(WebUserOperation.class)
                .eq(WebUserOperation::getDataId, dataId)
                .eq(WebUserOperation::getDataType, typeEnum)
                .eq(WebUserOperation::getUserId, JwtUtil.getUserId()));
        if (Objects.isNull(userOperation)) {
            WebUserOperation saveBean = new WebUserOperation();
            saveBean.setIsCollect(Boolean.FALSE);
            saveBean.setIsDownload(Boolean.FALSE);
            saveBean.setIsRead(Boolean.TRUE);
            saveBean.setDataType(typeEnum);
            saveBean.setDataId(dataId);
            saveBean.setUserId(JwtUtil.getUserId());
            userOperationMapper.insert(saveBean);
        } else if (BooleanUtils.isNotTrue(userOperation.getIsRead())) {
            userOperation.setIsRead(true);
            userOperationMapper.updateById(userOperation);
        }
    }

    @Override
    public void download(WebUserOperationModel model, Integer typeEnum) {
        WebUserOperation userOperation = userOperationMapper.selectOne(Wrappers.lambdaQuery(WebUserOperation.class)
                .eq(WebUserOperation::getDataId, model.getDataId())
                .eq(WebUserOperation::getDataType, typeEnum)
                .eq(WebUserOperation::getUserId, JwtUtil.getUserId()));

        if (Objects.isNull(userOperation)) {
            WebUserOperation saveBean = new WebUserOperation();
            saveBean.setIsCollect(Boolean.FALSE);
            saveBean.setIsDownload(Boolean.TRUE);
            saveBean.setIsRead(Boolean.FALSE);
            saveBean.setDataType(typeEnum);
            saveBean.setDataId(model.getDataId());
            saveBean.setUserId(JwtUtil.getUserId());
            userOperationMapper.insert(saveBean);
        } else if (BooleanUtils.isNotTrue(userOperation.getIsDownload())) {
            userOperation.setIsDownload(Boolean.TRUE);
            userOperationMapper.updateById(userOperation);
        }
    }

}
