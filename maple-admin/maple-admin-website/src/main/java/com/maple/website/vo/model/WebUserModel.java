package com.maple.website.vo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * 网站用户对象 web_user
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "网站用户对象", description = "website-网站用户信息表")
public class WebUserModel {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "用户账号")
    private String userNo;

    @ApiModelProperty(value = "微信小程序唯一标识符")
    private String openid;

    @ApiModelProperty(value = "微信开放平台唯一标识符")
    private String unionid;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "微信名称")
    private String wechatName;

    @ApiModelProperty(value = "用户描述")
    private String userDesc;

    @ApiModelProperty(value = "邀请用户ID")
    private Long inviterUserId;

    @ApiModelProperty(value = "头像链接")
    private String headPortraitLink;

    @ApiModelProperty(value = "创建人")
    private Long createId;

    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    private Long updateId;

    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    @ApiModelProperty(value = "版本号")
    private Long version;

}
