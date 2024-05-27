package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebContent;
import com.maple.website.vo.model.WebContentModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站文章内容Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebContentMapper extends BaseMapper<WebContent>{
    /**
     * 查询网站文章内容列表
     * 
     * @param page 分页信息
     * @param webContent 网站文章内容
     * @return 网站文章内容集合
     */
    IPage<WebContentModel> getPageList(@Param("page") Page<WebContentModel> page, @Param("webContent") WebContentModel webContent);
}
