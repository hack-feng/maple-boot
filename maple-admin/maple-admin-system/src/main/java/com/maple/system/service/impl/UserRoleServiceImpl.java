package com.maple.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.bean.UserRole;
import com.maple.system.mapper.UserRoleMapper;
import com.maple.system.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户中心-用户和角色关联表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
