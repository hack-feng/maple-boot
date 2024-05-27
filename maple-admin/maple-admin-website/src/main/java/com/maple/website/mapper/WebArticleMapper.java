package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebArticle;
import com.maple.website.vo.model.WebArticleModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站文章Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebArticleMapper extends BaseMapper<WebArticle>{
    /**
     * 查询网站文章列表
     * 
     * @param page 分页信息
     * @param webArticle 网站文章
     * @return 网站文章集合
     */
    IPage<WebArticleModel> getPageList(@Param("page") Page<WebArticleModel> page, @Param("webArticle") WebArticleModel webArticle);
}
