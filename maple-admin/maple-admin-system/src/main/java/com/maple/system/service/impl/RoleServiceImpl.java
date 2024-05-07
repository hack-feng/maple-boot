package com.maple.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.MapleConstants;
import com.maple.common.util.TransformUtils;
import com.maple.system.bean.Role;
import com.maple.system.bean.RoleDept;
import com.maple.system.bean.RoleMenu;
import com.maple.system.bean.UserRole;
import com.maple.system.mapper.RoleMapper;
import com.maple.system.service.IRoleDeptService;
import com.maple.system.service.IRoleMenuService;
import com.maple.system.service.IRoleService;
import com.maple.system.service.IUserRoleService;
import com.maple.system.vo.model.RoleModel;
import com.maple.system.vo.query.RolePageQuery;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户中心-角色信息表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
@Service
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    private final RoleMapper roleMapper;

    private final IRoleMenuService roleMenuService;

    private final IUserRoleService userRoleService;

    private final IRoleDeptService roleDeptService;

    @Override
    public IPage<RoleModel> getPageList(RolePageQuery roleReq) {
        return roleMapper.getPageList(roleReq.getPage(), roleReq.getQuery());
    }

    @Override
    public List<RoleModel> getRoleList() {
        return TransformUtils.mapList(roleMapper.selectList(Wrappers.lambdaQuery(Role.class)
                .eq(Role::getStatus, MapleConstants.STATUS_TRUE)
                .orderByAsc(Role::getSortNum)
                .orderByDesc(Role::getId)), RoleModel.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleModel model) {
        Role role = TransformUtils.map(model, Role.class);
        roleMapper.insert(role);
        saveRoleMenu(role.getId(), model.getMenuIds());
        saveRoleDept(role.getId(), model.getDeptIds());
        return role.getId();
    }

    @Override
    public void updateRole(RoleModel model) {
        roleMapper.updateById(TransformUtils.map(model, Role.class));
        roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleId, model.getId()));
        saveRoleMenu(model.getId(), model.getMenuIds());
        roleDeptService.remove(Wrappers.lambdaQuery(RoleDept.class).eq(RoleDept::getRoleId, model.getId()));
        saveRoleDept(model.getId(), model.getDeptIds());
    }

    @Override
    public RoleModel getRoleById(Long id) {
        RoleModel roleModel = TransformUtils.map(roleMapper.selectById(id), RoleModel.class);
        List<Long> menuIds = roleMenuService.list(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleId, id))
                .stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        roleModel.setMenuIds(menuIds);
        List<Long> deptIds = roleDeptService.list(Wrappers.lambdaQuery(RoleDept.class).eq(RoleDept::getRoleId, id))
                .stream().map(RoleDept::getDeptId).collect(Collectors.toList());
        roleModel.setDeptIds(deptIds);
        return roleModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteRole(Long id) {
        int count = roleMapper.deleteById(id);
        roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getRoleId, id));
        roleDeptService.remove(Wrappers.lambdaQuery(RoleDept.class).eq(RoleDept::getRoleId, id));
        userRoleService.remove(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getRoleId, id));
        return count;
    }

    private void saveRoleMenu(Long roleId, List<Long> menuIds) {
        if (CollectionUtils.isEmpty(menuIds)) {
            return;
        }
        List<RoleMenu> roleMenuList = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        roleMenuService.saveBatch(roleMenuList);
    }

    private void saveRoleDept(Long roleId, List<Long> deptIds) {
        if (CollectionUtils.isEmpty(deptIds)) {
            return;
        }
        List<RoleDept> roleDeptList = new ArrayList<>();
        for (Long deptId : deptIds) {
            RoleDept roleDept = new RoleDept();
            roleDept.setRoleId(roleId);
            roleDept.setDeptId(deptId);
            roleDeptList.add(roleDept);
        }
        roleDeptService.saveBatch(roleDeptList);
    }
}
