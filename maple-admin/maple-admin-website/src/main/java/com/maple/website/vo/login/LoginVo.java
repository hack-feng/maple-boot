package com.maple.website.vo.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 笑小枫 <https://www.xiaoxiaofeng.com/>
 * @date 2023/1/30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {

    @ApiModelProperty("授权结果")
    private Boolean authResult;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像链接")
    private String headPortraitLink;

    @ApiModelProperty("积分")
    private Long points;

    @ApiModelProperty("是否开通java题库会员")
    private Boolean isJavaMember;

    @ApiModelProperty("用户token")
    private String token;

    @ApiModelProperty("小程序登录code")
    private String code;
}
