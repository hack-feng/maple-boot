package com.maple.system.vo.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.config.bean.BaseQuery;
import com.maple.system.vo.model.ConfigModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统管理-参数配置分页请求对象 sys_config
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-04-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "系统管理-参数配置分页请求对象", description = "system-系统管理-参数配置分页请求对象")
public class ConfigPageQuery extends BaseQuery {

    @ApiModelProperty(value = "分页信息")
    private Page<ConfigModel> page;

    @ApiModelProperty(value = "请求信息")
    private ConfigModel query;

}
