package com.maple.system.vo.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2022/1/11
 */
@Data
@ApiModel(value = "角色请求对象", description = "用户中心-角色请求对象")
public class RoleQuery {

    private String roleName;

}
