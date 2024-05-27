package com.maple.website.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.website.vo.model.WebUserOperationModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 网站用户操作分页请求对象 web_user_operation
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站用户操作分页请求对象", description = "website-网站用户操作分页请求对象")
public class WebUserOperationPageQuery {

    @ApiModelProperty(value = "分页信息")
    private Page<WebUserOperationModel> page;

    @ApiModelProperty(value = "请求信息")
    private WebUserOperationModel query;

}
