package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebUser;
import com.maple.website.vo.model.WebUserModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站用户Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebUserMapper extends BaseMapper<WebUser>{
    /**
     * 查询网站用户列表
     * 
     * @param page 分页信息
     * @param webUser 网站用户
     * @return 网站用户集合
     */
    IPage<WebUserModel> getPageList(@Param("page") Page<WebUserModel> page, @Param("webUser") WebUserModel webUser);
}
