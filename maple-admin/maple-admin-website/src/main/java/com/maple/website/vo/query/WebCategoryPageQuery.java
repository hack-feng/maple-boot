package com.maple.website.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.vo.model.WebCategoryModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 网站类目分页请求对象 web_category
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站类目分页请求对象", description = "website-网站类目分页请求对象")
public class WebCategoryPageQuery {

    @ApiModelProperty(value = "分页信息")
    private Page<WebCategoryModel> page;

    @ApiModelProperty(value = "请求信息")
    private WebCategoryModel query;

}
