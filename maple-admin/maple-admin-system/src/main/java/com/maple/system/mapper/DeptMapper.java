package com.maple.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maple.system.bean.Dept;
import com.maple.system.vo.model.DeptModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户中心-部门Mapper接口
 *
 * @author 笑小枫-www.xiaoxiaofeng.com
 * @date 2024-03-25
 */
public interface DeptMapper extends BaseMapper<Dept>{
    /**
     * 查询用户中心-部门列表
     *
     * @param dept 用户中心-部门
     * @return 用户中心-部门集合
     */
    List<DeptModel> getTreeList(@Param("dept") DeptModel dept);
}
