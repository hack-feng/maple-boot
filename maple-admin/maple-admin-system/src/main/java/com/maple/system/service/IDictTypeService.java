package com.maple.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.system.vo.model.DictTypeModel;
import com.maple.system.vo.query.DictTypePageQuery;

import java.util.List;

/**
 * 系统管理-字典类型Service接口
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-02-20
 */
public interface IDictTypeService {

    /**
     * 分页查询系统管理-字典类型列表
     *
     * @param query 请求参数
     * @return 系统管理-字典类型集合
     */
    IPage<DictTypeModel> getPageList(DictTypePageQuery query);

    /**
     * 根据id查询系统管理-字典类型详情
     *
     * @param id 系统管理-字典类型ID
     * @return 系统管理-字典类型
     */
    DictTypeModel getDictTypeById(Long id);

    /**
     * 新增系统管理-字典类型信息
     *
     * @param model 系统管理-字典类型信息
     * @return 新增后的ID
     */
    Long createDictType(DictTypeModel model);

    /**
     * 修改系统管理-字典类型信息
     *
     * @param model 系统管理-字典类型信息
     */
    void updateDictType(DictTypeModel model);

    /**
     * 删除系统管理-字典类型信息
     *
     * @param id 系统管理-字典类型ID
     * @return 删除数量
     */
    Integer deleteDictType(Long id);

    /**
     * 获取字典类型列表
     * @return 字典类型列表
     */
    List<DictTypeModel> getDictTypeList();
    
}