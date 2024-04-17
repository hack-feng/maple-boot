package com.maple.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.UserConstants;
import com.maple.common.util.TransformUtils;
import com.maple.system.bean.Menu;
import com.maple.system.bean.RoleMenu;
import com.maple.system.mapper.MenuMapper;
import com.maple.system.service.IMenuService;
import com.maple.system.service.IRoleMenuService;
import com.maple.system.vo.model.MenuModel;
import com.maple.system.vo.model.MetaModel;
import com.maple.system.vo.model.RouterModel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 用户中心-菜单权限表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private final MenuMapper menuMapper;

    private final IRoleMenuService roleMenuService;

    @Override
    public List<RouterModel> selectMenuTreeByUserId(Long userId) {
        List<MenuModel> menus = menuMapper.selectMenuTreeByUserId(userId);
        List<MenuModel> menuModelList = getChildPerms(menus, 0L);
        return buildMenus(menuModelList);
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    @Override
    public List<MenuModel> getTreeList(MenuModel menu) {
        List<MenuModel> list = menuMapper.getTreeList(menu);
        return getChildPerms(list, 0L);
    }
    
    @Override
    public MenuModel getMenuById(Long id) {
        return TransformUtils.map(menuMapper.selectById(id), MenuModel.class);
    }

    @Override
    public Long createMenu(MenuModel model) {
        Menu menu = TransformUtils.map(model, Menu.class);
        menuMapper.insert(menu);
        return menu.getId();
    }

    @Override
    public void updateMenu(MenuModel model) {
        Menu menu = TransformUtils.map(model, Menu.class);
        menuMapper.updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteMenu(Long id) {
        int count = menuMapper.deleteById(id);
        roleMenuService.remove(Wrappers.lambdaQuery(RoleMenu.class).eq(RoleMenu::getMenuId, id));
        return count;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<MenuModel> getChildPerms(List<MenuModel> list, Long parentId) {
        List<MenuModel> returnList = new ArrayList<>();
        for (MenuModel t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId().equals(parentId)) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<MenuModel> list, MenuModel t) {
        // 得到子节点列表
        List<MenuModel> childList = getChildList(list, t);
        t.setChildren(childList);
        for (MenuModel tChild : childList) {
            // 判断是否有子节点
            if (!CollectionUtils.isEmpty(getChildList(list, t))) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<MenuModel> getChildList(List<MenuModel> list, MenuModel t) {
        List<MenuModel> menuModelList = new ArrayList<>();
        for (MenuModel n : list) {
            if (n.getParentId().equals(t.getId())) {
                menuModelList.add(n);
            }
        }
        return menuModelList;
    }

    private List<RouterModel> buildMenus(List<MenuModel> menus) {
        List<RouterModel> routers = new LinkedList<>();
        for (MenuModel menu : menus) {
            RouterModel router = new RouterModel();
            router.setName(menu.getName());
            router.setPath(menu.getPath());
            router.setComponent(menu.getComponent());
            router.setMeta(MetaModel.builder()
                    .title(menu.getTitle())
                    .icon(menu.getIcon())
                    .isAffix(menu.getIsAffix())
                    .isHide(menu.getIsHide())
                    .isIframe(menu.getIsIframe())
                    .isLink(menu.getLinkUrl())
                    .isKeepAlive(menu.getIsKeepAlive())
                    .build());
            List<MenuModel> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setRedirect(menu.getRedirect());
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }
}
