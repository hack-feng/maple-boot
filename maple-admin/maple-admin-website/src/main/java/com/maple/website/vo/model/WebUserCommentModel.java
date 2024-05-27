package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站用户评论对象 web_user_comment
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站用户评论对象", description = "website-网站用户评论信息表")
public class WebUserCommentModel {

    @ApiModelProperty(value = "主键")
    private Long id;

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


    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

}
