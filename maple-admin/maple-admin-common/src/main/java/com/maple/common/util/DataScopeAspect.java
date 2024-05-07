package com.maple.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.maple.common.config.bean.BaseQuery;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 数据过滤处理
 *
 * @author zhangfuzeng
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    @Before("@annotation(controllerDataScope)")
    public void doBefore(JoinPoint point, DataScope controllerDataScope) {
        clearDataScope(point);
        handleDataScope(point, controllerDataScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, DataScope controllerDataScope) {
        // 如果是超级管理员，则不过滤数据
        if (Objects.nonNull(JwtUtil.getUserId()) && BooleanUtils.isNotTrue(JwtUtil.isAdmin())) {
            dataScopeFilter(joinPoint, controllerDataScope.deptAlias(), controllerDataScope.userAlias());
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint  切点
     * @param user       用户
     * @param deptAlias  部门别名
     * @param userAlias  用户别名
     * @param permission 权限字符
     */
    public static void dataScopeFilter(JoinPoint joinPoint, String deptAlias, String userAlias) {
        StringBuilder sqlString = new StringBuilder();
        List<String> conditions = new ArrayList<>();

        for (JSONObject role : Objects.requireNonNull(JSON.parseArray(JwtUtil.getRoleList(), JSONObject.class))) {
            String dataScope = role.getString("dataScope");
            Long roleId = role.getLong("id");
            if (!DATA_SCOPE_CUSTOM.equals(dataScope) && conditions.contains(dataScope)) {
                continue;
            }
            if (DATA_SCOPE_ALL.equals(dataScope)) {
                sqlString = new StringBuilder();
                conditions.add(dataScope);
                break;
            } else if (DATA_SCOPE_CUSTOM.equals(dataScope)) {
                sqlString.append(String.format(
                        " OR %s IN ( SELECT dept_id FROM usc_role_dept WHERE role_id = %d ) ", deptAlias, roleId));
            } else if (DATA_SCOPE_DEPT.equals(dataScope)) {
                sqlString.append(String.format(" OR %s = %d ", deptAlias, JwtUtil.getDeptId()));
            } else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope)) {
                sqlString.append(String.format(
                        " OR %s IN ( SELECT id FROM usc_dept WHERE id = %d or find_in_set( %d , ancestors ) )",
                        deptAlias, JwtUtil.getDeptId(), JwtUtil.getDeptId()));
            } else if (DATA_SCOPE_SELF.equals(dataScope)) {
                if (StringUtils.isNotBlank(userAlias)) {
                    sqlString.append(String.format(" OR %s = %d ", userAlias, JwtUtil.getUserId()));
                } else {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(String.format(" OR %s = 0 ", deptAlias));
                }
            }
            conditions.add(dataScope);
        }

        // 多角色情况下，所有角色都不包含传递过来的权限字符，这个时候sqlString也会为空，所以要限制一下,不查询任何数据
        if (CollectionUtils.isEmpty(conditions)) {
            sqlString.append(String.format(" OR %s = 0 ", deptAlias));
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            Object params = joinPoint.getArgs()[0];
            if (Objects.nonNull(params) && params instanceof BaseQuery) {
                BaseQuery baseQuery = (BaseQuery) params;
                // 从第4位开始取，去掉第一个` OR `
                baseQuery.setDataScope(" AND (" + sqlString.substring(4) + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (Objects.nonNull(params) && params instanceof BaseQuery) {
            BaseQuery baseQuery = (BaseQuery) params;
            baseQuery.setDataScope("");
        }
    }
}