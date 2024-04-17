package com.maple.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.system.bean.User;
import com.maple.system.vo.model.UserModel;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户中心-用户信息表 Mapper 接口
 * </p>
 *
 * @author Maple
 * @since 2021-12-07
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 分页获取用户信息
     *
     * @param page    分页信息
     * @param req 用户请求信息
     * @return 用户列表
     */
    IPage<UserModel> getPageList(Page<User> page, @Param("user") UserModel req);
}
