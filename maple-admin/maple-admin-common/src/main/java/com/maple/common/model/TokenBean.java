package com.maple.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author ZhangFZ
 * @date 2019/12/12 16:10
 **/
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class TokenBean {

    /**
     * 是否管理员用户，授予admin权限为管理员
     */
    private Boolean isAdmin;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * openId
     */
    private String openId;

    /**
     * 小程序sessionKey
     */
    private String sessionKey;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 角色ID拼接字符串
     */
    private String roleIdList;

    /**
     * 角色信息拼接字符串
     */
    private String roleList;
}
