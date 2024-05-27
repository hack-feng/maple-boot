package com.maple.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.website.vo.query.WebUserOperationPageQuery;
import com.maple.website.bean.WebUserOperation;
import com.maple.website.vo.model.WebUserOperationModel;
import com.maple.website.mapper.WebUserOperationMapper;
import com.maple.website.service.IWebUserOperationService;
import com.maple.common.util.TransformUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 网站用户操作Service业务层处理
 *
 * @author www.xiaoxiaofeng.com
 * @date 2024-05-27
 */
@Service
@AllArgsConstructor
public class WebUserOperationServiceImpl extends ServiceImpl<WebUserOperationMapper, WebUserOperation> implements IWebUserOperationService {
    
    private final WebUserOperationMapper webUserOperationMapper;

    @Override
    public IPage<WebUserOperationModel> getPageList(WebUserOperationPageQuery query) {
        return webUserOperationMapper.getPageList(query.getPage(), query.getQuery());
    }

    @Override
    public WebUserOperationModel getWebUserOperationById(Long id) {
        return TransformUtils.map(webUserOperationMapper.selectById(id), WebUserOperationModel.class);
    }

    @Override
    public Long createWebUserOperation(WebUserOperationModel model) {
        WebUserOperation webUserOperation = TransformUtils.map(model, WebUserOperation.class);
        webUserOperationMapper.insert(webUserOperation);
        return webUserOperation.getId();
    }

    @Override
    public void updateWebUserOperation(WebUserOperationModel model) {
        webUserOperationMapper.updateById(TransformUtils.map(model, WebUserOperation.class));
    }
    
    @Override
    public Integer deleteWebUserOperation(Long id) {
        return webUserOperationMapper.deleteById(id);
    }

}
