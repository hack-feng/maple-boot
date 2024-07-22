package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站用户操作对象 web_user_operation
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站用户操作对象", description = "website-网站用户操作信息表")
public class WebUserOperationModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "类型")
    private Integer dataType;

    @ApiModelProperty(value = "数据id")
    private Long dataId;
    
    @ApiModelProperty(value = "数据名称")
    private String dataName;

    @ApiModelProperty(value = "用户id")
    private Long userId;
    
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "是否阅读")
    private Boolean isRead;

    @ApiModelProperty(value = "是否下载")
    private Boolean isDownload;

    @ApiModelProperty(value = "是否点赞")
    private Boolean isLike;

    @ApiModelProperty(value = "是否收藏")
    private Boolean isCollect;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

}
