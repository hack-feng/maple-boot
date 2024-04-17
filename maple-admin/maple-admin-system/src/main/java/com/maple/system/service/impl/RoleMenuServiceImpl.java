package com.maple.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.system.bean.RoleMenu;
import com.maple.system.mapper.RoleMenuMapper;
import com.maple.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户中心-角色和菜单关联表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
