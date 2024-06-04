package com.maple.website.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网站用户操作对象 web_menu_category
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_menu_category")
@ApiModel(value = "网站用户操作对象", description = "system-网站用户操作信息表")
public class WebMenuCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单id")
    private Long webMenuId;

    @ApiModelProperty(value = "类目id")
    private Long categoryId;

}
