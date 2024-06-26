package com.maple.website.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.common.config.exception.ErrorCode;
import com.maple.common.config.exception.MapleCheckException;
import com.maple.common.util.TransformUtils;
import com.maple.website.bean.WebMenu;
import com.maple.website.bean.WebMenuCategory;
import com.maple.website.mapper.WebMenuCategoryMapper;
import com.maple.website.mapper.WebMenuMapper;
import com.maple.website.service.IWebMenuService;
import com.maple.website.vo.model.WebMenuModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 网站菜单Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-29
 */
@Service
@AllArgsConstructor
public class WebMenuServiceImpl extends ServiceImpl<WebMenuMapper, WebMenu> implements IWebMenuService {

    private final WebMenuMapper webMenuMapper;

    private final WebMenuCategoryMapper menuCategoryMapper;

    @Override
    public List<WebMenuModel> getTreeList(WebMenuModel webMenu) {
        List<WebMenuModel> list = webMenuMapper.getTreeList(webMenu);
        return getChildPerms(list, 0L);
    }

    @Override
    public WebMenuModel getWebMenuById(Long id) {
        WebMenuModel menuModel = TransformUtils.map(webMenuMapper.selectById(id), WebMenuModel.class);

        List<WebMenuCategory> menuCategoryList = menuCategoryMapper.selectList(Wrappers.lambdaUpdate(WebMenuCategory.class)
                .eq(WebMenuCategory::getWebMenuId, id));
        menuModel.setCategoryList(menuCategoryList.stream().map(WebMenuCategory::getCategoryId).collect(Collectors.toList()));
        return menuModel;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createWebMenu(WebMenuModel model) {

        Long count = webMenuMapper.selectCount(Wrappers.lambdaQuery(WebMenu.class).eq(WebMenu::getPath, model.getPath()));
        if (count > 0) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "路由地址已存在");
        }

        WebMenu webMenu = TransformUtils.map(model, WebMenu.class);
        webMenuMapper.insert(webMenu);

        if (!CollectionUtils.isEmpty(model.getCategoryList())) {
            for (Long categoryId : model.getCategoryList()) {
                WebMenuCategory menuCategory = new WebMenuCategory();
                menuCategory.setCategoryId(categoryId);
                menuCategory.setWebMenuId(webMenu.getId());
                menuCategoryMapper.insert(menuCategory);
            }
        }
        return webMenu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWebMenu(WebMenuModel model) {
        Long count = webMenuMapper.selectCount(Wrappers.lambdaQuery(WebMenu.class)
                .ne(WebMenu::getId, model.getId())
                .eq(WebMenu::getPath, model.getPath()));
        if (count > 0) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR, "路由地址已存在");
        }

        webMenuMapper.updateById(TransformUtils.map(model, WebMenu.class));

        menuCategoryMapper.delete(Wrappers.lambdaUpdate(WebMenuCategory.class)
                .eq(WebMenuCategory::getWebMenuId, model.getId()));

        if (!CollectionUtils.isEmpty(model.getCategoryList())) {
            for (Long categoryId : model.getCategoryList()) {
                WebMenuCategory menuCategory = new WebMenuCategory();
                menuCategory.setCategoryId(categoryId);
                menuCategory.setWebMenuId(model.getId());
                menuCategoryMapper.insert(menuCategory);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteWebMenu(Long id) {
        menuCategoryMapper.delete(Wrappers.lambdaUpdate(WebMenuCategory.class)
                .eq(WebMenuCategory::getWebMenuId, id));
        return webMenuMapper.deleteById(id);
    }

    @Override
    public WebMenuModel getWebMenuByPath(String path) {
        return TransformUtils.map(
                webMenuMapper.selectOne(Wrappers.lambdaQuery(WebMenu.class).eq(WebMenu::getPath, path).last("LIMIT 1")),
                WebMenuModel.class);
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<WebMenuModel> getChildPerms(List<WebMenuModel> list, Long parentId) {
        List<WebMenuModel> returnList = new ArrayList<>();
        for (WebMenuModel model : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (model.getParentId().equals(parentId)) {
                recursionFn(list, model);
                returnList.add(model);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<WebMenuModel> list, WebMenuModel model) {
        // 得到子节点列表
        List<WebMenuModel> childList = getChildList(list, model);
        model.setChildren(childList);
        for (WebMenuModel child : childList) {
            // 判断是否有子节点
            if (!CollectionUtils.isEmpty(getChildList(list, model))) {
                recursionFn(list, child);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<WebMenuModel> getChildList(List<WebMenuModel> list, WebMenuModel model) {
        List<WebMenuModel> modelList = new ArrayList<>();
        for (WebMenuModel n : list) {
            if (n.getParentId().equals(model.getId())) {
                modelList.add(n);
            }
        }
        return modelList;
    }
}
