package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebFriendlyLink;
import com.maple.website.vo.model.WebFriendlyLinkModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站链接Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebFriendlyLinkMapper extends BaseMapper<WebFriendlyLink>{
    /**
     * 查询网站链接列表
     * 
     * @param page 分页信息
     * @param webFriendlyLink 网站链接
     * @return 网站链接集合
     */
    IPage<WebFriendlyLinkModel> getPageList(@Param("page") Page<WebFriendlyLinkModel> page, @Param("webFriendlyLink") WebFriendlyLinkModel webFriendlyLink);
}
