-- 导出  表 maple-boot.gen_table 结构
CREATE TABLE IF NOT EXISTS `gen_table` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_name` varchar(200) DEFAULT '' COMMENT '表名称',
    `table_comment` varchar(500) DEFAULT '' COMMENT '表描述',
    `sub_table_name` varchar(64) DEFAULT NULL COMMENT '关联子表的表名',
    `sub_table_fk_name` varchar(64) DEFAULT NULL COMMENT '子表关联的外键名',
    `class_name` varchar(100) DEFAULT '' COMMENT '实体类名称',
    `tpl_category` varchar(200) DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
    `package_name` varchar(100) DEFAULT NULL COMMENT '生成包路径',
    `module_name` varchar(30) DEFAULT NULL COMMENT '生成模块名',
    `business_name` varchar(30) DEFAULT NULL COMMENT '生成业务名',
    `function_name` varchar(50) DEFAULT NULL COMMENT '生成功能名',
    `function_author` varchar(50) DEFAULT NULL COMMENT '生成功能作者',
    `gen_type` char(1) DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
    `gen_path` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
    `tree_code` varchar(200) DEFAULT NULL COMMENT '树编码字段',
    `tree_parent_code` varchar(200) DEFAULT NULL COMMENT '树父编码字段',
    `tree_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '树名称字段',
    `parent_menu_id` bigint(20) DEFAULT NULL COMMENT '父菜单节点ID',
    `parent_menu_name` varchar(200) DEFAULT NULL COMMENT '父菜单节点名称',
    `options` varchar(1000) DEFAULT NULL COMMENT '其它生成选项',
    `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=2 ROW_FORMAT=DYNAMIC COMMENT='代码生成-代码生成业务表';

-- 正在导出表  maple-boot.gen_table 的数据：~7 rows (大约)
INSERT INTO `gen_table` (`id`, `table_name`, `table_comment`, `sub_table_name`, `sub_table_fk_name`, `class_name`, `tpl_category`, `package_name`, `module_name`, `business_name`, `function_name`, `function_author`, `gen_type`, `gen_path`, `tree_code`, `tree_parent_code`, `tree_name`, `parent_menu_id`, `parent_menu_name`, `options`, `remark`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES
(1, 'sys_config', '系统管理-参数配置表', NULL, NULL, 'Config', 'crud', 'com.maple.system', 'system', 'config', '系统管理-参数配置', 'www.xiaoxiaofeng.com', '0', '/', NULL, NULL, NULL, 2, NULL, NULL, NULL, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29');

-- 导出  表 maple-boot.gen_table_column 结构
CREATE TABLE IF NOT EXISTS `gen_table_column` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `table_id` bigint(20) DEFAULT NULL COMMENT '归属表编号',
    `column_name` varchar(200) DEFAULT NULL COMMENT '列名称',
    `column_comment` varchar(500) DEFAULT NULL COMMENT '列描述',
    `column_type` varchar(100) DEFAULT NULL COMMENT '列类型',
    `java_type` varchar(500) DEFAULT NULL COMMENT 'JAVA类型',
    `java_field` varchar(200) DEFAULT NULL COMMENT 'JAVA字段名',
    `is_pk` tinyint(1) DEFAULT '0' COMMENT '是否主键（1是）',
    `is_increment` tinyint(1) DEFAULT '0' COMMENT '是否自增（1是）',
    `is_required` tinyint(1) DEFAULT '0' COMMENT '是否必填（1是）',
    `is_insert` tinyint(1) DEFAULT '0' COMMENT '是否为插入字段（1是）',
    `is_edit` tinyint(1) DEFAULT '0' COMMENT '是否编辑字段（1是）',
    `is_list` tinyint(1) DEFAULT '0' COMMENT '是否列表字段（1是）',
    `is_query` tinyint(1) DEFAULT '0' COMMENT '是否查询字段（1是）',
    `query_type` varchar(200) DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
    `html_type` varchar(200) DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
    `dict_type` varchar(200) DEFAULT '' COMMENT '字典类型',
    `sort` int(11) DEFAULT NULL COMMENT '排序',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=11 ROW_FORMAT=DYNAMIC COMMENT='代码生成-代码生成业务表字段';

-- 正在导出表  maple-boot.gen_table_column 的数据：~0 rows (大约)
INSERT INTO `gen_table_column` (`id`, `table_id`, `column_name`, `column_comment`, `column_type`, `java_type`, `java_field`, `is_pk`, `is_increment`, `is_required`, `is_insert`, `is_edit`, `is_list`, `is_query`, `query_type`, `html_type`, `dict_type`, `sort`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES
(1, 1, 'id', '参数主键', 'bigint(20)', 'Long', 'id', 1, 1, 0, 0, 0, 0, 0, 'EQ', 'input', '', 1, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(2, 1, 'config_name', '参数名称', 'varchar(100)', 'String', 'configName', 0, 0, 0, 1, 1, 1, 1, 'EQ', 'editor', '', 2, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(3, 1, 'config_key', '参数键名', 'varchar(100)', 'String', 'configKey', 0, 0, 0, 1, 1, 1, 1, 'EQ', 'switch', '', 3, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(4, 1, 'config_value', '参数键值', 'varchar(500)', 'String', 'configValue', 0, 0, 0, 1, 1, 1, 1, 'EQ', 'radio', '', 4, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(5, 1, 'config_type', '系统内置（Y是 N否）', 'char(1)', 'String', 'configType', 0, 0, 0, 1, 1, 1, 1, 'EQ', 'checkbox', '', 5, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(6, 1, 'create_id', '创建人id', 'bigint(20)', 'Long', 'createId', 0, 0, 0, 0, 0, 0, 0, 'EQ', 'input', '', 6, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(7, 1, 'create_time', '创建时间', 'datetime', 'Date', 'createTime', 0, 0, 0, 0, 0, 0, 0, 'EQ', 'datetime', '', 7, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(8, 1, 'update_id', '修改人id', 'bigint(20)', 'Long', 'updateId', 0, 0, 0, 0, 0, 0, 0, 'EQ', 'input', '', 8, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(9, 1, 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', 0, 0, 0, 0, 0, 0, 0, 'EQ', 'datetime', '', 9, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29'),
(10, 1, 'remark', '备注', 'varchar(500)', 'String', 'remark', 0, 0, 0, 1, 1, 1, 0, 'EQ', 'textarea', '', 10, 1, '2024-04-11 15:48:09', 1, '2024-04-12 14:09:29');

-- 导出  表 maple-boot.sys_config 结构
CREATE TABLE IF NOT EXISTS `sys_config` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
    `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
    `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
    `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
    `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB ROW_FORMAT=DYNAMIC COMMENT='系统管理-参数配置表';

-- 正在导出表  maple-boot.sys_config 的数据：~3 rows (大约)

-- 导出  表 maple-boot.sys_dict_data 结构
CREATE TABLE IF NOT EXISTS `sys_dict_data` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
    `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
    `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
    `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
    `dict_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '字典类型',
    `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
    `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
    `is_default` tinyint(1) DEFAULT '0' COMMENT '是否默认（1是 0否）',
    `status` tinyint(4) DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=28 ROW_FORMAT=DYNAMIC COMMENT='系统管理-字典数据表';

-- 正在导出表  maple-boot.sys_dict_data 的数据：~31 rows (大约)
INSERT INTO `sys_dict_data` (`id`, `dict_sort`, `dict_label`, `dict_value`, `dict_code`, `css_class`, `list_class`, `is_default`, `status`, `create_id`, `create_time`, `update_id`, `update_time`, `remark`) VALUES
(1, 1, '男', '0', 'sys_user_sex', '', 'primary', 1, 1, 1, '2024-02-21 16:56:12', 1, '2024-02-21 16:56:12', '性别男'),
(2, 2, '女', '1', 'sys_user_sex', '', 'primary', 0, 1, 1, '2024-02-21 16:56:12', 1, '2024-02-21 16:56:12', '性别女'),
(3, 3, '未知', '2', 'sys_user_sex', '', 'info', 0, 1, 1, '2024-02-21 16:56:12', 1, '2024-02-21 16:56:12', '性别未知'),
(4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 1, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '显示菜单'),
(5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '隐藏菜单'),
(6, 1, '正常', '1', 'sys_normal_disable', '', 'primary', 1, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '正常状态'),
(7, 2, '停用', '0', 'sys_normal_disable', '', 'danger', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '停用状态'),
(8, 1, '启用', '1', 'sys_status', '', 'primary', 1, 1, 1, '2024-04-11 11:09:41', 1, '2024-04-11 11:09:41', ''),
(9, 2, '禁用', '0', 'sys_status', '', 'info', 0, 1, 1, '2024-04-11 11:09:41', 1, '2024-04-11 11:09:41', ''),
(10, 1, '新增', '1', 'sys_oper_type', '', 'info', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '新增操作'),
(11, 2, '修改', '2', 'sys_oper_type', '', 'info', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '修改操作'),
(12, 3, '删除', '3', 'sys_oper_type', '', 'danger', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '删除操作'),
(13, 4, '授权', '4', 'sys_oper_type', '', 'primary', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '授权操作'),
(14, 5, '导出', '5', 'sys_oper_type', '', 'warning', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '导出操作'),
(15, 6, '导入', '6', 'sys_oper_type', '', 'warning', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '导入操作'),
(16, 7, '强退', '7', 'sys_oper_type', '', 'danger', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '强退操作'),
(17, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '生成操作'),
(18, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '清空操作'),
(19, 0, '审核中', '0', 'approve_status', '', 'info', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '正常状态'),
(20, 1, '通过', '1', 'approve_status', '', 'primary', 0, 1, NULL, '2021-05-17 17:52:08', NULL, NULL, '停用状态'),
(21, 2, '拒绝', '2', 'approve_status', NULL, 'danger', 0, 1, NULL, '2024-02-26 13:39:48', NULL, NULL, NULL),
(22, 3, '删除', '3', 'approve_status', NULL, 'info', 0, 1, NULL, '2024-02-26 13:39:48', NULL, NULL, NULL),
(23, 1, '系统用户', '1', 'system_user_type', '', 'primary', 0, 1, 1, '2024-03-25 13:30:40', 1, '2024-03-25 13:30:40', ''),
(24, 2, '小程序用户', '2', 'system_user_type', '', 'success', 0, 1, 1, '2024-03-25 13:30:40', 1, '2024-03-25 13:30:40', ''),
(25, 1, '目录', 'M', 'menu_type', '', 'success', 0, 1, 1, '2024-03-27 14:24:51', 1, '2024-03-27 14:24:51', ''),
(26, 2, '菜单', 'C', 'menu_type', '', 'warning', 0, 1, 1, '2024-03-27 14:24:51', 1, '2024-03-27 14:24:51', ''),
(27, 3, '按钮', 'F', 'menu_type', '', 'danger', 0, 1, 1, '2024-03-27 14:24:51', 1, '2024-03-27 14:24:51', '');

-- 导出  表 maple-boot.sys_dict_type 结构
CREATE TABLE IF NOT EXISTS `sys_dict_type` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
    `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
    `dict_code` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '字典类型',
    `status` tinyint(4) DEFAULT '1' COMMENT '状态（1正常 0停用）',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `dict_type` (`dict_code`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=9 ROW_FORMAT=DYNAMIC COMMENT='系统管理-字典类型表';

-- 正在导出表  maple-boot.sys_dict_type 的数据：~9 rows (大约)
INSERT INTO `sys_dict_type` (`id`, `dict_name`, `dict_code`, `status`, `remark`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES
(1, '性别', 'sys_user_sex', 1, '用户性别列表', NULL, '2021-05-17 17:52:08', 1, '2024-02-21 16:56:12'),
(2, '菜单状态', 'sys_show_hide', 1, '菜单状态列表', NULL, '2021-05-17 17:52:08', NULL, NULL),
(3, '系统开关', 'sys_normal_disable', 1, '系统开关列表', NULL, '2021-05-17 17:52:08', NULL, NULL),
(4, '系统状态', 'sys_status', 1, '系统状态列表', NULL, '2021-05-17 17:52:08', 1, '2024-04-11 11:09:41'),
(5, '操作类型', 'sys_oper_type', 1, '操作类型列表', NULL, '2021-05-17 17:52:08', NULL, NULL),
(6, '审核状态', 'approve_status', 1, '审核状态列表', NULL, '2021-05-17 17:52:08', NULL, NULL),
(7, '系统用户类型', 'system_user_type', 1, '系统用户类型，后台的为系统用户，只可登录管理系统；web端的为小程序用户，必须有openId', 1, '2024-03-25 13:29:55', 1, '2024-03-25 13:30:40'),
(8, '菜单类型', 'menu_type', 1, '菜单类型（M目录 C菜单 F按钮）', 1, '2024-03-27 14:24:51', 1, '2024-03-27 14:24:51');

-- 导出  表 maple-boot.sys_notice 结构
CREATE TABLE IF NOT EXISTS `sys_notice` (
    `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
    `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
    `notice_content` text COMMENT '公告内容',
    `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_name` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_name` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `remark` varchar(255) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB ROW_FORMAT=DYNAMIC COMMENT='系统管理-通知公告表';

-- 正在导出表  maple-boot.sys_notice 的数据：~2 rows (大约)

-- 导出  表 maple-boot.sys_operate_log 结构
CREATE TABLE IF NOT EXISTS `sys_operate_log` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title` varchar(50) DEFAULT '' COMMENT '模块标题',
    `business_type` int(2) DEFAULT '0' COMMENT '业务类型（0查询 1新增 2修改 3删除 4其他）',
    `method` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '方法名称',
    `resp_time` bigint(20) DEFAULT NULL COMMENT '响应时间',
    `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
    `browser` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '浏览器类型',
    `operate_type` int(1) DEFAULT '0' COMMENT '操作类别（0网站用户 1后台用户 2小程序 3其他）',
    `operate_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '请求URL',
    `operate_ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '主机地址',
    `operate_location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '操作地点',
    `operate_param` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '请求参数',
    `json_result` text COMMENT '返回参数',
    `status` tinyint(4) DEFAULT NULL COMMENT '操作状态（0正常 1异常）',
    `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT '错误消息',
    `create_id` bigint(20) DEFAULT NULL COMMENT '操作人id',
    `create_time` datetime DEFAULT NULL COMMENT '操作时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '更新人',
    `update_time` datetime DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB ROW_FORMAT=DYNAMIC COMMENT='系统管理-操作日志记录';

-- 正在导出表  maple-boot.sys_operate_log 的数据：~0 rows (大约)

-- 导出  表 maple-boot.usc_dept 结构
CREATE TABLE IF NOT EXISTS `usc_dept` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
    `parent_id` bigint(20) DEFAULT '0' COMMENT '父部门id',
    `ancestors` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '祖级列表',
    `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
    `sort_num` bigint(20) DEFAULT NULL COMMENT '显示顺序',
    `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
    `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
    `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
    `status` tinyint(4) DEFAULT NULL COMMENT '部门状态（0正常 1停用）',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=3 ROW_FORMAT=DYNAMIC COMMENT='用户中心-部门表';

-- 正在导出表  maple-boot.usc_dept 的数据：~15 rows (大约)
INSERT INTO `usc_dept` (`id`, `parent_id`, `ancestors`, `dept_name`, `sort_num`, `leader`, `phone`, `email`, `status`, `create_id`, `create_time`, `update_id`, `update_time`, `is_delete`) VALUES
(1, 0, '[]', '笑小枫', 0, '笑小枫', '18300000000', '1150640979@qq.com', 1, NULL, '2024-03-25 16:47:02', NULL, '2024-03-25 16:47:02', 0),
(2, 1, '[1]', '管理部门', 1, '张三', '18888888888', NULL, 1, 1, '2024-04-11 11:06:16', 1, '2024-04-11 11:07:10', 0);

-- 导出  表 maple-boot.usc_menu 结构
CREATE TABLE IF NOT EXISTS `usc_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路由名称',
    `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
    `menu_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `parent_id` bigint(20) DEFAULT '0' COMMENT '父菜单ID',
    `ancestors` varchar(255) DEFAULT NULL COMMENT '祖级菜单',
    `sort_num` bigint(20) DEFAULT NULL COMMENT '显示顺序',
    `path` varchar(200) DEFAULT '' COMMENT '路由地址',
    `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
    `redirect` varchar(255) DEFAULT NULL COMMENT '重定向地址',
    `link_url` varchar(255) DEFAULT NULL COMMENT '链接地址',
    `is_iframe` tinyint(1) DEFAULT '0' COMMENT '是否内嵌窗口',
    `is_link` tinyint(1) DEFAULT '0' COMMENT '是否外链',
    `is_keep_alive` tinyint(1) DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
    `is_hide` tinyint(1) DEFAULT '0' COMMENT '是否隐藏',
    `is_affix` tinyint(1) DEFAULT '0' COMMENT '是否固定',
    `status` tinyint(4) DEFAULT NULL COMMENT '菜单状态（0正常 1停用）',
    `perms` varchar(100) DEFAULT NULL COMMENT '权限标识',
    `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
    `remark` varchar(500) DEFAULT '' COMMENT '备注',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=10 ROW_FORMAT=DYNAMIC COMMENT='用户中心-菜单权限表';

-- 正在导出表  maple-boot.usc_menu 的数据：~13 rows (大约)
INSERT INTO `usc_menu` (`id`, `name`, `title`, `menu_type`, `parent_id`, `ancestors`, `sort_num`, `path`, `component`, `redirect`, `link_url`, `is_iframe`, `is_link`, `is_keep_alive`, `is_hide`, `is_affix`, `status`, `perms`, `icon`, `remark`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES
(1, 'home', '首页', 'C', 0, '', 1, '/home', 'home/index', NULL, '', 0, 0, 1, 0, 1, 1, '', 'iconfont icon-shouye', '', 1, '2024-03-28 11:09:37', 1, '2024-03-28 13:04:26'),
(2, 'system', '系统设置', 'M', 0, '', 2, '/system', 'system/user/index', NULL, '', 0, 0, 1, 0, 1, 1, '', 'iconfont icon-xitongshezhi', '', 1, '2024-03-28 13:06:21', 1, '2024-03-28 13:09:51'),
(3, 'systemUser', '用户管理', 'C', 2, '[2]', 1, '/system/user', 'system/user/index', NULL, '', 0, 0, 1, 0, 0, 1, '', 'iconfont icon-icon-', '', 1, '2024-03-28 13:19:11', 1, '2024-03-28 13:21:01'),
(4, 'systemRole', '角色管理', 'C', 2, '[2]', 2, '/system/role', 'system/role/index', NULL, '', 0, 0, 1, 0, 0, 1, '', 'iconfont icon-shuxingtu', '', 1, '2024-03-28 15:11:42', 1, '2024-03-28 15:13:00'),
(5, 'systemMenu', '菜单管理', 'C', 2, '[2]', 3, '/system/menu', 'system/menu/index', NULL, '', 0, 0, 1, 0, 0, 1, '', 'iconfont icon-juxingkaobei', '', 1, '2024-03-28 15:11:42', 1, '2024-03-28 15:21:57'),
(6, 'systemDept', '部门管理', 'C', 2, '[2]', 4, '/system/dept', 'system/dept/index', NULL, '', 0, 0, 1, 0, 0, 1, '', 'ele-OfficeBuilding', '', 1, '2024-03-28 15:11:42', 1, '2024-03-28 15:13:00'),
(7, 'systemDic', '字典管理', 'C', 2, '[2]', 5, '/system/dic', 'system/dic/index', NULL, '', 0, 0, 1, 0, 0, 1, '', 'ele-SetUp', '', 1, '2024-03-28 15:11:42', 1, '2024-03-28 15:13:00'),
(8, 'tool', '生成工具', 'M', 0, '', 3, '/tool', 'tool/gen/index', NULL, '', 0, 0, 1, 0, 1, 1, '', 'iconfont icon-shouye_dongtaihui', '', 1, '2024-03-28 13:06:21', 1, '2024-03-28 16:12:08'),
(9, 'toolGen', '代码生成', 'C', 8, '[8]', 1, '/tool/gen', 'tool/gen/index', NULL, '', 0, 0, 1, 0, 0, 1, '', 'iconfont icon-zujian', '', 1, '2024-03-28 13:19:11', 1, '2024-03-28 16:13:10');

-- 导出  表 maple-boot.usc_post 结构
CREATE TABLE IF NOT EXISTS `usc_post` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
    `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
    `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
    `sort_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '显示顺序',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=5 ROW_FORMAT=DYNAMIC COMMENT='用户中心-岗位信息表';

-- 正在导出表  maple-boot.usc_post 的数据：~4 rows (大约)
INSERT INTO `usc_post` (`id`, `post_code`, `post_name`, `sort_num`, `status`, `remark`, `create_id`, `create_time`, `update_id`, `update_time`) VALUES
(1, 'ceo', '董事长', 1, 1, '', NULL, NULL, 1, '2021-05-17 16:42:19'),
(2, 'se', '部门经理', 2, 0, '', NULL, NULL, NULL, NULL),
(3, 'hr', '人力资源', 3, 0, '', NULL, NULL, NULL, NULL),
(4, 'user', '普通员工', 4, 0, '', NULL, NULL, NULL, NULL);

-- 导出  表 maple-boot.usc_role 结构
CREATE TABLE IF NOT EXISTS `usc_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name` varchar(30) NOT NULL COMMENT '角色名称',
    `role_key` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
    `sort_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '显示顺序',
    `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
    `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
    `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=3 ROW_FORMAT=DYNAMIC COMMENT='用户中心-角色信息表';

-- 正在导出表  maple-boot.usc_role 的数据：~2 rows (大约)
INSERT INTO `usc_role` (`id`, `role_name`, `role_key`, `sort_num`, `data_scope`, `menu_check_strictly`, `dept_check_strictly`, `status`, `remark`, `create_id`, `create_time`, `update_id`, `update_time`, `is_delete`) VALUES
(1, '超级管理员', 'admin', 1, '1', 1, 1, 1, '超级管理员', NULL, '2021-05-17 14:03:57', 1, '2024-04-11 16:45:08', 0),
(2, '1', '1', 1, '1', 1, 1, 1, '1', 1, '2024-04-11 16:02:40', 1, '2024-04-11 16:02:40', 1);

-- 导出  表 maple-boot.usc_role_dept 结构
CREATE TABLE IF NOT EXISTS `usc_role_dept` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB ROW_FORMAT=DYNAMIC COMMENT='用户中心-角色和部门关联表';

-- 正在导出表  maple-boot.usc_role_dept 的数据：~0 rows (大约)

-- 导出  表 maple-boot.usc_role_menu 结构
CREATE TABLE IF NOT EXISTS `usc_role_menu` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=10 ROW_FORMAT=DYNAMIC COMMENT='用户中心-角色和菜单关联表';

-- 正在导出表  maple-boot.usc_role_menu 的数据：~9 rows (大约)
INSERT INTO `usc_role_menu` (`id`, `role_id`, `menu_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9);

-- 导出  表 maple-boot.usc_user 结构
CREATE TABLE IF NOT EXISTS `usc_user` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `open_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '小程序的openId',
    `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
    `account` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户账号',
    `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户姓名',
    `nick_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户昵称',
    `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '00' COMMENT '用户类型（00系统用户,01小程序用户）',
    `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
    `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '手机号码',
    `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
    `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
    `salt` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户加密盐值',
    `password` varchar(100) DEFAULT '' COMMENT '密码',
    `status` tinyint(4) DEFAULT NULL COMMENT '帐号状态（0正常 1停用）',
    `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
    `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
    `create_id` bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_id` bigint(20) DEFAULT NULL COMMENT '更新人id',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `is_delete` tinyint(1) DEFAULT '0' COMMENT '删除标志',
    `remark` varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=2 ROW_FORMAT=DYNAMIC COMMENT='用户中心-用户信息表';

-- 正在导出表  maple-boot.usc_user 的数据：~2 rows (大约)
INSERT INTO `usc_user` (`id`, `open_id`, `dept_id`, `account`, `user_name`, `nick_name`, `user_type`, `email`, `phone`, `sex`, `avatar`, `salt`, `password`, `status`, `login_ip`, `login_date`, `create_id`, `create_time`, `update_id`, `update_time`, `is_delete`, `remark`) VALUES
(1, NULL, 2, 'admin', '管理员', '笑小枫', '1', '1150640979@qq.com', '18300000000', '0', 'https://image.xiaoxiaofeng.site/blog/2024/04/12/xxf-20240412102322.png?xxfjava', '439495', '27c3b87192fdaf6c54a0c3de1f339f1d', 1, '127.0.0.1', '2020-10-22 00:00:00', 1, '2020-10-22 14:27:04', 1, '2024-04-11 16:51:27', 0, '管理员');

-- 导出  表 maple-boot.usc_user_post 结构
CREATE TABLE IF NOT EXISTS `usc_user_post` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNI_USER_POST` (`user_id`,`post_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 ROW_FORMAT=DYNAMIC COMMENT='用户中心-用户与岗位关联表';

-- 正在导出表  maple-boot.usc_user_post 的数据：~6 rows (大约)
INSERT INTO `usc_user_post` (`id`, `user_id`, `post_id`) VALUES
(1, 1, 1);

-- 导出  表 maple-boot.usc_user_role 结构
CREATE TABLE IF NOT EXISTS `usc_user_role` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `UNI_USER_ROLE` (`user_id`,`role_id`) USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=5 ROW_FORMAT=DYNAMIC COMMENT='用户中心-用户和角色关联表';

-- 正在导出表  maple-boot.usc_user_role 的数据：~5 rows (大约)
INSERT INTO `usc_user_role` (`id`, `user_id`, `role_id`) VALUES
(4, 1, 1);
