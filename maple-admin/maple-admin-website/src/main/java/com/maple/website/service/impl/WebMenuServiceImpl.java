package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.website.bean.WebMenu;
import com.maple.website.vo.model.WebMenuModel;
import com.maple.website.mapper.WebMenuMapper;
import com.maple.website.service.IWebMenuService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<WebMenuModel> getTreeList(WebMenuModel webMenu) {
        List<WebMenuModel> list = webMenuMapper.getTreeList(webMenu);
        return getChildPerms(list, 0L);
    }

    @Override
    public WebMenuModel getWebMenuById(Long id) {
        return TransformUtils.map(webMenuMapper.selectById(id), WebMenuModel.class);
    }

    @Override
    public Long createWebMenu(WebMenuModel model) {
        WebMenu webMenu = TransformUtils.map(model, WebMenu.class);
        webMenuMapper.insert(webMenu);
        return webMenu.getId();
    }

    @Override
    public void updateWebMenu(WebMenuModel model) {
        webMenuMapper.updateById(TransformUtils.map(model, WebMenu.class));
    }

    @Override
    public Integer deleteWebMenu(Long id) {
        return webMenuMapper.deleteById(id);
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
