package com.maple.system.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.system.vo.model.RoleModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * 用户中心-角色信息分页请求对象 usc_role
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-27
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户中心-角色信息分页请求对象", description = "system-用户中心-角色信息分页请求对象")
public class RolePageQuery {

    @ApiModelProperty(value = "分页信息")
    private Page<RoleModel> page;

    @ApiModelProperty(value = "请求信息")
    private RoleModel query;

}
