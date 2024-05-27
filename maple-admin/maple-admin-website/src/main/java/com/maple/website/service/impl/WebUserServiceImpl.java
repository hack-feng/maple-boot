package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserPageQuery;
import com.maple.website.bean.WebUser;
import com.maple.website.vo.model.WebUserModel;
import com.maple.website.mapper.WebUserMapper;
import com.maple.website.service.IWebUserService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站用户Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebUserServiceImpl extends ServiceImpl<WebUserMapper, WebUser> implements IWebUserService {
    
    private final WebUserMapper webUserMapper;

    @Override
    public IPage<WebUserModel> getPageList(WebUserPageQuery query) {
        return webUserMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebUserModel getWebUserById(Long id) {
        return TransformUtils.map(webUserMapper.selectById(id), WebUserModel.class);
    }

    @Override
    public Long createWebUser(WebUserModel model) {
        WebUser webUser = TransformUtils.map(model, WebUser.class);
        webUserMapper.insert(webUser);
        return webUser.getId();
    }

    @Override
    public void updateWebUser(WebUserModel model) {
        webUserMapper.updateById(TransformUtils.map(model, WebUser.class));
    }
    
    @Override
    public Integer deleteWebUser(Long id) {
        return webUserMapper.deleteById(id);
    }

}
