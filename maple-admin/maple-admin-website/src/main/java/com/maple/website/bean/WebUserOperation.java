package com.maple.website.bean;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.maple.common.config.bean.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 网站用户操作对象 web_user_operation
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_user_operation")
@ApiModel(value = "网站用户操作对象", description = "website-网站用户操作信息表")
public class WebUserOperation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型")
    private Integer dataType;
    
    @ApiModelProperty(value = "数据id")
    private Long dataId;
    
    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    @ApiModelProperty(value = "是否阅读")
    private Boolean isRead;
    
    @ApiModelProperty(value = "是否点赞")
    private Boolean isLike;
    
    @ApiModelProperty(value = "是否收藏")
    private Boolean isCollect;
    
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;
    
}
