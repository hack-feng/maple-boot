package com.maple.system.service.impl;

import com.maple.system.service.IConfigService;
import com.maple.system.bean.Config;
import com.maple.system.mapper.ConfigMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统管理-参数配置表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements IConfigService {

}
