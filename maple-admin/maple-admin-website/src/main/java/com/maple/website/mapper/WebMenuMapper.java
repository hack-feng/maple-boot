package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.website.bean.WebMenu;
import com.maple.website.vo.model.WebMenuModel;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 网站菜单Mapper接口
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
public interface WebMenuMapper extends BaseMapper<WebMenu>{
    /**
     * 查询网站菜单列表
     *
     * @param webMenu 网站菜单
     * @return 网站菜单集合
     */
    List<WebMenuModel> getTreeList(@Param("webMenu") WebMenuModel webMenu);
}
