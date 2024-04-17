package com.maple.system.vo.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/9
 */
@Data
@ApiModel(value = "用户登录请求对象", description = "用户中心-用户登录请求对象")
public class LoginQuery {

    @ApiModelProperty(value = "登录账号")
    private String account;
    
    @ApiModelProperty(value = "登录密码")
    private String password;
}
