package com.maple.system.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.system.bean.User;
import com.maple.system.vo.model.UserModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2021/12/28
 */
@Data
@Builder
@ApiModel(value = "用户分页请求对象", description = "用户中心-用户分页请求对象")
public class UserPageQuery {

    @ApiModelProperty(value = "分页信息")
    private Page<User> page;

    @ApiModelProperty(value = "用户请求信息")
    private UserModel query;
}
