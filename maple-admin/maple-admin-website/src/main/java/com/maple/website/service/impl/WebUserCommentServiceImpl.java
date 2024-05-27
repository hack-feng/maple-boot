package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserCommentPageQuery;
import com.maple.website.bean.WebUserComment;
import com.maple.website.vo.model.WebUserCommentModel;
import com.maple.website.mapper.WebUserCommentMapper;
import com.maple.website.service.IWebUserCommentService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站用户评论Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebUserCommentServiceImpl extends ServiceImpl<WebUserCommentMapper, WebUserComment> implements IWebUserCommentService {
    
    private final WebUserCommentMapper webUserCommentMapper;

    @Override
    public IPage<WebUserCommentModel> getPageList(WebUserCommentPageQuery query) {
        return webUserCommentMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebUserCommentModel getWebUserCommentById(Long id) {
        return TransformUtils.map(webUserCommentMapper.selectById(id), WebUserCommentModel.class);
    }

    @Override
    public Long createWebUserComment(WebUserCommentModel model) {
        WebUserComment webUserComment = TransformUtils.map(model, WebUserComment.class);
        webUserCommentMapper.insert(webUserComment);
        return webUserComment.getId();
    }

    @Override
    public void updateWebUserComment(WebUserCommentModel model) {
        webUserCommentMapper.updateById(TransformUtils.map(model, WebUserComment.class));
    }
    
    @Override
    public Integer deleteWebUserComment(Long id) {
        return webUserCommentMapper.deleteById(id);
    }

}
