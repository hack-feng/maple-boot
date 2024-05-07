package com.maple.common.util;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 *
 * @author zhangfuzeng
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 查询数据所在表的部门ID，如为多表关联，请指定表的别名，如：d.dept_id
     */
    String deptAlias() default "belong_dept_id";

    /**
     * 查询数据所在表的用户ID，如为多表关联，请指定表的别名，如：u.create_id
     */
    String userAlias() default "create_id";
}