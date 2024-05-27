package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.bean.WebUserOperation;
import com.maple.website.vo.model.WebUserOperationModel;
import org.apache.ibatis.annotations.Param;

/**
 * 网站用户操作Mapper接口
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
public interface WebUserOperationMapper extends BaseMapper<WebUserOperation>{
    /**
     * 查询网站用户操作列表
     * 
     * @param page 分页信息
     * @param webUserOperation 网站用户操作
     * @return 网站用户操作集合
     */
    IPage<WebUserOperationModel> getPageList(@Param("page") Page<WebUserOperationModel> page, @Param("webUserOperation") WebUserOperationModel webUserOperation);
}
