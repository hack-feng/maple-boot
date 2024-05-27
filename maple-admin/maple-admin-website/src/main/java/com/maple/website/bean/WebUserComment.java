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
 * 网站用户评论对象 web_user_comment
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_user_comment")
@ApiModel(value = "网站用户评论对象", description = "website-网站用户评论信息表")
public class WebUserComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主数据ID")
    private Long dataId;
    
    @ApiModelProperty(value = "祖评论ID")
    private Long topId;
    
    @ApiModelProperty(value = "父评论ID")
    private Long parentId;
    
    @ApiModelProperty(value = "发表用户id")
    private Long userId;
    
    @ApiModelProperty(value = "回复用户id")
    private Long toUserId;
    
    @ApiModelProperty(value = "评论内容")
    private String content;
    
    @ApiModelProperty(value = "评论类型")
    private Integer commentType;
    
    @ApiModelProperty(value = "状态")
    private Integer status;
    
    @ApiModelProperty(value = "点赞数量")
    private Long likeNum;
    
    @ApiModelProperty(value = "评论数量")
    private Long commentNum;
    
    @ApiModelProperty(value = "是否阅读")
    private Boolean isRead;
    
    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDelete;
    
}
