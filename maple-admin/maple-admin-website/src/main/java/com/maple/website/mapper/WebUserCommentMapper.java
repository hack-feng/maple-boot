package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebUserComment;
import com.maple.website.vo.model.WebUserCommentModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站用户评论Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebUserCommentMapper extends BaseMapper<WebUserComment>{
    /**
     * 查询网站用户评论列表
     * 
     * @param page 分页信息
     * @param webUserComment 网站用户评论
     * @return 网站用户评论集合
     */
    IPage<WebUserCommentModel> getPageList(@Param("page") Page<WebUserCommentModel> page, @Param("webUserComment") WebUserCommentModel webUserComment);
}
