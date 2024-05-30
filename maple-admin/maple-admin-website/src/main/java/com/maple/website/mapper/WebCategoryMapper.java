package com.maple.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.website.bean.WebCategory;
import com.maple.website.vo.model.WebCategoryModel;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 网站类目Mapper接口
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
public interface WebCategoryMapper extends BaseMapper<WebCategory>{
    /**
     * 查询网站类目列表
     *
     * @param webCategory 网站类目
     * @return 网站类目集合
     */
    List<WebCategoryModel> getTreeList(@Param("webCategory") WebCategoryModel webCategory);
}
