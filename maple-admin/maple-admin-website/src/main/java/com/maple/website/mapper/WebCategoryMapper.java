package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebCategory;
import com.maple.website.vo.model.WebCategoryModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站类目Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebCategoryMapper extends BaseMapper<WebCategory>{
    /**
     * 查询网站类目列表
     * 
     * @param page 分页信息
     * @param webCategory 网站类目
     * @return 网站类目集合
     */
    IPage<WebCategoryModel> getPageList(@Param("page") Page<WebCategoryModel> page, @Param("webCategory") WebCategoryModel webCategory);
}
