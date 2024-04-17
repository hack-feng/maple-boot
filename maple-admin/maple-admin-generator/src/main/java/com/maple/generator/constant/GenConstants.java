package com.maple.generator.constant;

/**
 * @author 笑小枫-https://www.xiaoxiaofeng.com
 * @date 2024/2/4
 */
public class GenConstants {

    /**
     * 单表（增删改查）
     */
    public static final String TPL_CRUD = "crud";

    /**
     * 树表（增删改查）
     */
    public static final String TPL_TREE = "tree";

    /**
     * 主子表（增删改查）
     */
    public static final String TPL_SUB = "sub";

    /**
     * 树编码字段
     */
    public static final String TREE_CODE = "treeCode";

    /**
     * 树父编码字段
     */
    public static final String TREE_PARENT_CODE = "treeParentCode";

    /**
     * 树名称字段
     */
    public static final String TREE_NAME = "treeName";

    /**
     * 上级菜单ID字段
     */
    public static final String PARENT_MENU_ID = "parentMenuId";

    /**
     * 上级菜单名称字段
     */
    public static final String PARENT_MENU_NAME = "parentMenuName";

    /**
     * 数据库字符串类型
     */
    public static final String[] COLUMNTYPE_STR = {"char", "varchar", "nvarchar", "varchar2"};

    /**
     * 数据库文本类型
     */
    public static final String[] COLUMNTYPE_TEXT = {"tinytext", "text", "mediumtext", "longtext"};

    /**
     * 数据库时间类型
     */
    public static final String[] COLUMNTYPE_TIME = {"datetime", "time", "date", "timestamp"};
    
    public static final String COLUMNTYPE_TINYINT = "tinyint";
    
    /**
     * 数据库数字类型
     */
    public static final String[] COLUMNTYPE_NUMBER = {"tinyint", "smallint", "mediumint", "int", "number", "integer",
            "bit", "bigint", "float", "double", "decimal"};

    /**
     * 页面不需要添加字段
     */
    public static final String[] COLUMNNAME_NOT_ADD = {"id", "create_id", "create_time", "update_id",
            "update_time", "is_delete", "ancestors", "parent_id"};

    /**
     * 页面不需要编辑字段
     */
    public static final String[] COLUMNNAME_NOT_EDIT = {"id", "create_id", "create_time", "update_id",
            "update_time", "is_delete", "ancestors", "parent_id"};

    /**
     * 页面不需要显示的列表字段
     */
    public static final String[] COLUMNNAME_NOT_LIST = {"id", "create_id", "create_time", "update_id",
            "update_time", "is_delete", "ancestors", "parent_id"};

    /**
     * 页面不需要查询字段
     */
    public static final String[] COLUMNNAME_NOT_QUERY = {"id", "create_id", "create_time", "update_id", "sort_num", "remark",
            "update_time", "is_delete", "ancestors", "parent_id"};

    /**
     * Entity基类字段
     */
    public static final String[] BASE_ENTITY = {"id", "createId", "createTime", "updateId", "updateTime"};

    /**
     * Tree基类字段
     */
    public static final String[] TREE_ENTITY = {"id", "parentName", "parentId", "sortNum", "ancestors"};

    /**
     * 文本框
     */
    public static final String HTML_INPUT = "input";

    /**
     * 文本域
     */
    public static final String HTML_TEXTAREA = "textarea";

    /**
     * 下拉框
     */
    public static final String HTML_SELECT = "select";

    /**
     * 单选框
     */
    public static final String HTML_RADIO = "radio";

    /**
     * 复选框
     */
    public static final String HTML_CHECKBOX = "checkbox";

    /**
     * 日期控件
     */
    public static final String HTML_DATETIME = "datetime";

    /**
     * 上传控件
     */
    public static final String HTML_UPLOAD = "upload";

    /**
     * 富文本控件
     */
    public static final String HTML_SUMMERNOTE = "summernote";

    /**
     * 字符串类型
     */
    public static final String TYPE_STRING = "String";

    /**
     * 整型
     */
    public static final String TYPE_INTEGER = "Integer";

    /**
     * 长整型
     */
    public static final String TYPE_LONG = "Long";

    /**
     * 浮点型
     */
    public static final String TYPE_DOUBLE = "Double";

    /**
     * 布尔型
     */
    public static final String TYPE_BOOLEAN = "Boolean";

    /**
     * 高精度计算类型
     */
    public static final String TYPE_BIGDECIMAL = "BigDecimal";

    /**
     * 时间类型
     */
    public static final String TYPE_DATE = "Date";

    /**
     * 模糊查询
     */
    public static final String QUERY_LIKE = "LIKE";

    /**
     * 相等查询
     */
    public static final String QUERY_EQ = "EQ";

    /**
     * 需要
     */
    public static final Boolean REQUIRE = true;
    
}
