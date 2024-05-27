package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebMenuPageQuery;
import com.maple.website.bean.WebMenu;
import com.maple.website.vo.model.WebMenuModel;
import com.maple.website.mapper.WebMenuMapper;
import com.maple.website.service.IWebMenuService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站菜单Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebMenuServiceImpl extends ServiceImpl<WebMenuMapper, WebMenu> implements IWebMenuService {
    
    private final WebMenuMapper webMenuMapper;

    @Override
    public IPage<WebMenuModel> getPageList(WebMenuPageQuery query) {
        return webMenuMapper.getPageList(query.getPage(), query.getQuery());
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

}
