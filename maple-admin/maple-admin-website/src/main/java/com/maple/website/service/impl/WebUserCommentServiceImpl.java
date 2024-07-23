package com.maple.website.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCommonException;
import com.maple.common.util.JwtUtil;
import com.maple.common.util.TransformUtils;
import com.maple.website.bean.WebArticle;
import com.maple.website.bean.WebUser;
import com.maple.website.bean.WebUserComment;
import com.maple.website.common.enums.DataTypeEnum;
import com.maple.website.mapper.WebArticleMapper;
import com.maple.website.mapper.WebUserCommentMapper;
import com.maple.website.mapper.WebUserMapper;
import com.maple.website.service.IWebUserCommentService;
import com.maple.website.vo.model.WebUserCommentModel;
import com.maple.website.vo.query.WebUserCommentPageQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 网站用户评论Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebUserCommentServiceImpl extends ServiceImpl<WebUserCommentMapper, WebUserComment> implements IWebUserCommentService {

    private final WebUserCommentMapper userCommentMapper;

    private final WebUserMapper userMapper;

    private final WebArticleMapper articleMapper;

    @Override
    public IPage<WebUserCommentModel> getPageList(WebUserCommentPageQuery query) {
        IPage<WebUserCommentModel> userCommentPage = userCommentMapper.getPageList(query.getPage(), query.getQuery());
        userCommentPage.setRecords(transUser(userCommentPage.getRecords()));
        return userCommentPage;
    }

    @Override
    public WebUserCommentModel getWebUserCommentById(Long id) {
        return TransformUtils.map(userCommentMapper.selectById(id), WebUserCommentModel.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WebUserCommentModel createWebUserComment(WebUserCommentModel model) {
        WebUserComment comment = TransformUtils.map(model, WebUserComment.class);

        if (model.getCommentType().equals(DataTypeEnum.ARTICLE.getCode())) {
            articleMapper.update(null, Wrappers.lambdaUpdate(WebArticle.class)
                    .setSql("comment_num = comment_num + 1")
                    .eq(WebArticle::getId, model.getDataId()));
        }

        if (model.getParentId() != 0) {
            WebUserComment parentComment = userCommentMapper.selectById(model.getParentId());
            if (!Objects.equals(comment.getCommentType(), parentComment.getCommentType())
                    || !Objects.equals(comment.getDataId(), parentComment.getDataId())) {
                throw new MapleCommonException(ErrorCode.COMMON_ERROR, "评论数据有误，请重试");
            }
            comment.setTopId(parentComment.getTopId() == 0 ? model.getParentId() : parentComment.getTopId());
            comment.setToUserId(parentComment.getUserId());
            userCommentMapper.update(null, Wrappers.lambdaUpdate(WebUserComment.class)
                    .setSql("comment_num = comment_num + 1")
                    .eq(WebUserComment::getId, comment.getTopId()));
        }
        comment.setUserId(JwtUtil.getUserId());
        comment.setIsRead(Boolean.FALSE);
        comment.setStatus(1);
        userCommentMapper.insert(comment);
        return transUser(Collections.singletonList(TransformUtils.map(comment, WebUserCommentModel.class))).get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWebUserComment(Long id) {
        WebUserComment comment = userCommentMapper.selectById(id);
        if (Objects.isNull(comment)) {
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "数据不存在");
        }
        if (!Objects.equals(comment.getUserId(), JwtUtil.getUserId())) {
            throw new MapleCommonException(ErrorCode.COMMON_ERROR, "只能操作自己的数据哟");
        }
        int deleteCount = userCommentMapper.deleteById(id);

        if (comment.getTopId() == 0) {
            deleteCount += userCommentMapper.delete(Wrappers.lambdaQuery(WebUserComment.class)
                    .eq(WebUserComment::getTopId, comment.getId()));
        } else {
            userCommentMapper.update(null, Wrappers.lambdaUpdate(WebUserComment.class)
                    .setSql("comment_num = comment_num - 1")
                    .eq(WebUserComment::getId, comment.getTopId()));
        }

        if (comment.getCommentType().equals(DataTypeEnum.ARTICLE.getCode())) {
            articleMapper.update(null, Wrappers.lambdaUpdate(WebArticle.class)
                    .setSql("comment_num = comment_num - " + deleteCount)
                    .eq(WebArticle::getId, comment.getDataId()));
        }
        return deleteCount;
    }


    /**
     * 用户名转换
     */
    private List<WebUserCommentModel> transUser(List<WebUserCommentModel> userCommentVos) {
        if (CollectionUtils.isEmpty(userCommentVos)) {
            return userCommentVos;
        }
        Set<Long> userIds = new HashSet<>();
        userIds.addAll(userCommentVos.stream().map(WebUserCommentModel::getUserId).filter(Objects::nonNull).collect(Collectors.toSet()));
        userIds.addAll(userCommentVos.stream().map(WebUserCommentModel::getToUserId).filter(Objects::nonNull).collect(Collectors.toSet()));
        if (!CollectionUtils.isEmpty(userIds)) {
            List<WebUser> users = userMapper.selectBatchIds(userIds);
            Map<Long, WebUser> userMap = users.stream().collect(Collectors.toMap(WebUser::getId, u -> u));
            for (WebUserCommentModel commentVo : userCommentVos) {
                commentVo.setHeadPortraitLink(userMap.get(commentVo.getUserId()).getHeadPortraitLink());
                commentVo.setUserName(userMap.get(commentVo.getUserId()).getNickName());
                if (Objects.nonNull(commentVo.getToUserId())) {
                    commentVo.setToUserHeadPortraitLink(userMap.get(commentVo.getToUserId()).getHeadPortraitLink());
                    commentVo.setToUserName(userMap.get(commentVo.getToUserId()).getNickName());
                }
            }
        }
        return userCommentVos;
    }

}
