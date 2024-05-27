package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebMenu;
import com.maple.website.vo.model.WebMenuModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站菜单Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebMenuMapper extends BaseMapper<WebMenu>{
    /**
     * 查询网站菜单列表
     * 
     * @param page 分页信息
     * @param webMenu 网站菜单
     * @return 网站菜单集合
     */
    IPage<WebMenuModel> getPageList(@Param("page") Page<WebMenuModel> page, @Param("webMenu") WebMenuModel webMenu);
}
