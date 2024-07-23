package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

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
    @NotNull(message = "回复的数据ID不能为空")
    private Long dataId;
    
    @ApiModelProperty(value = "数据名称")
    private String dataName;

    @ApiModelProperty(value = "祖评论ID")
    private Long topId;

    @ApiModelProperty("父节点ID集合")
    private List<Long> parentIds;

    @ApiModelProperty(value = "父评论ID")
    @NotNull(message = "回复的评论ID不能为空")
    private Long parentId;

    @ApiModelProperty("发表用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String userName;

    @ApiModelProperty("用户头像")
    private String headPortraitLink;

    @ApiModelProperty("被回复用户id")
    private Long toUserId;

    @ApiModelProperty("被回复用户名称")
    private String toUserName;

    @ApiModelProperty("被回复用户头像")
    private String toUserHeadPortraitLink;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty(value = "评论类型")
    @NotNull(message = "评论类型不能为空")
    private Integer commentType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;

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

    @ApiModelProperty("排序方式：默认：id降序  1：id升序")
    private Integer sortFlag;

    @ApiModelProperty("子评论列表")
    private List<WebUserCommentModel> replyList;

}
