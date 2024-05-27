package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebResource;
import com.maple.website.vo.model.WebResourceModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站资源Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebResourceMapper extends BaseMapper<WebResource>{
    /**
     * 查询网站资源列表
     * 
     * @param page 分页信息
     * @param webResource 网站资源
     * @return 网站资源集合
     */
    IPage<WebResourceModel> getPageList(@Param("page") Page<WebResourceModel> page, @Param("webResource") WebResourceModel webResource);
}
