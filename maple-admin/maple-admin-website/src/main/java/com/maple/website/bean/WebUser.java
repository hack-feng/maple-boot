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
 * 网站用户对象 web_user
 * 
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("web_user")
@ApiModel(value = "网站用户对象", description = "website-网站用户信息表")
public class WebUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    
    @ApiModelProperty(value = "版本号")
    private Long version;
    
}
