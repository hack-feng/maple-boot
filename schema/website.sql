CREATE TABLE `web_user`
(
    `id`                 BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_no`            VARCHAR(64) NULL DEFAULT NULL COMMENT '用户账号',
    `openid`             VARCHAR(64) NOT NULL COMMENT '微信小程序唯一标识符',
    `unionid`            VARCHAR(50) NULL DEFAULT NULL COMMENT '微信开放平台唯一标识符',
    `nick_name`          VARCHAR(64) NULL DEFAULT NULL COMMENT '昵称',
    `sex`                TINYINT(4) NULL DEFAULT NULL COMMENT '性别',
    `email`              VARCHAR(128) NULL DEFAULT NULL COMMENT '邮箱',
    `wechat_name`        VARCHAR(64) NULL DEFAULT NULL COMMENT '微信名称',
    `user_desc`          VARCHAR(255) NULL DEFAULT NULL COMMENT '用户描述',
    `inviter_user_id`    BIGINT(20) NULL DEFAULT NULL COMMENT '邀请用户ID',
    `head_portrait_link` VARCHAR(2000) NULL DEFAULT NULL COMMENT '头像链接',
    `create_id`          BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`        DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`          BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`        DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    `version`            BIGINT(20) NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `idx_openid` (`openid`) USING BTREE
) COMMENT='网站用户表' COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `web_config`
(
    `id`              BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `title`           VARCHAR(64) NULL DEFAULT NULL COMMENT '网站标题',
    `description`     VARCHAR(64) NOT NULL COMMENT '网站描述',
    `web_img`         VARCHAR(2000) NULL DEFAULT NULL COMMENT '网站图标',
    `icp`             VARCHAR(50) NULL DEFAULT NULL COMMENT '备案号',
    `create_id`       BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`     DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`       BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`     DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    `version`         BIGINT(20) NULL DEFAULT '0' COMMENT '版本号',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='网站用户表' COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `web_menu`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`        varchar(50) NULL DEFAULT NULL COMMENT '路由名称',
    `title`       varchar(50) NULL DEFAULT NULL COMMENT '菜单名称',
    `image`       varchar(255) NULL DEFAULT NULL COMMENT '菜单大图',
    `menu_type`   char(1) NULL DEFAULT '' COMMENT '菜单类型（H页头菜单 F页脚菜单）',
    `parent_id`   bigint(20) DEFAULT '0' COMMENT '父菜单ID',
    `ancestors`   varchar(255) DEFAULT NULL COMMENT '祖级菜单',
    `sort_num`    bigint(20) DEFAULT NULL COMMENT '显示顺序',
    `path`        varchar(200) DEFAULT '' COMMENT '路由地址',
    `component`   varchar(255) DEFAULT NULL COMMENT '组件路径',
    `link_url`    varchar(255) DEFAULT NULL COMMENT '链接地址',
    `is_link`     tinyint(1) DEFAULT '0' COMMENT '是否外链',
    `status`      tinyint(4) DEFAULT NULL COMMENT '菜单状态（0正常 1停用）',
    `icon`        varchar(100) DEFAULT '#' COMMENT '菜单图标',
    `remark`      varchar(500) DEFAULT '' COMMENT '备注',
    `create_id`   bigint(20) DEFAULT NULL COMMENT '创建人id',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_id`   bigint(20) DEFAULT NULL COMMENT '修改人id',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户中心-菜单权限表';

CREATE TABLE `web_resource`
(
    `id`            BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `resource_type` TINYINT(4) NOT NULL DEFAULT '0' COMMENT '资源类型',
    `resource_name` VARCHAR(64) NULL DEFAULT NULL COMMENT '模板名称',
    `resource_desc` LONGTEXT NULL DEFAULT NULL COMMENT '模板描述',
    `resource_img`  VARCHAR(255) NULL DEFAULT NULL COMMENT '图片',
    `down_type`     TINYINT(1) NULL DEFAULT NULL COMMENT '下载类型 1:又拍云  2：百度云',
    `down_url`      VARCHAR(255) NOT NULL COMMENT '下载地址',
    `file_suffix`   VARCHAR(255) NOT NULL COMMENT '文件后缀',
    `visit_url`     VARCHAR(255) NULL DEFAULT NULL COMMENT '预览地址',
    `sort_num`      BIGINT(20) NULL DEFAULT '0' COMMENT '排序',
    `is_top`        TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否置顶',
    `is_hot`        TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否热门',
    `status`        TINYINT(1) NULL DEFAULT '1' COMMENT '状态',
    `download_num`  INT(11) NOT NULL DEFAULT '1' COMMENT '下载数量',
    `collect_num`   INT(11) NOT NULL DEFAULT '1' COMMENT '收藏数量',
    `title_id`      BIGINT(20) NOT NULL COMMENT '资源描述内容ID',
    `create_id`     BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`   DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`     BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`   DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='网站资源表' COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `web_friendly_link`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `link_type`   TINYINT(4) NULL DEFAULT NULL COMMENT '链接类型',
    `name`        VARCHAR(200) NULL DEFAULT NULL COMMENT '名称',
    `logo`        VARCHAR(200) NULL DEFAULT NULL COMMENT '图标',
    `url`         VARCHAR(500) NULL DEFAULT NULL COMMENT '链接地址',
    `sort_num`    BIGINT(20) NULL DEFAULT '0' COMMENT '排序',
    `is_top`      TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否置顶',
    `is_hot`      TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否热门',
    `link_num`    INT(11) NOT NULL DEFAULT '0' COMMENT '链接次数',
    `description` VARCHAR(500) NULL DEFAULT NULL COMMENT '描述',
    `remark`      VARCHAR(500) NULL DEFAULT NULL COMMENT '备注',
    `is_delete`   TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
    `create_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '创建人id',
    `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
    `update_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '修改人id',
    `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `UNI_URL` (`url`) USING BTREE
) COMMENT='网站链接表' COLLATE='utf8_general_ci' ENGINE=InnoDB;


CREATE TABLE `web_category`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `icon`        VARCHAR(255) NULL DEFAULT NULL COMMENT '图标',
    `name`        VARCHAR(50) NOT NULL COMMENT '名称',
    `description` VARCHAR(255) NULL DEFAULT NULL COMMENT '描述',
    `parent_id`   BIGINT(20) NULL DEFAULT '0' COMMENT '父节点id',
    `ancestors`   varchar(255) DEFAULT NULL COMMENT '祖级菜单',
    `sort_num`    BIGINT(20) NULL DEFAULT '0' COMMENT '排序',
    `is_top`      TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否置顶',
    `is_hot`      TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否热门',
    `target`      VARCHAR(20) NULL DEFAULT '_self' COMMENT '目标地址',
    `url`         VARCHAR(1023) NULL DEFAULT NULL COMMENT '跳转地址',
    `is_valid`    TINYINT(1) NULL DEFAULT '1' COMMENT '是否有效',
    `create_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='网站类目表' COLLATE='utf8_general_ci' ENGINE=InnoDB;


CREATE TABLE `web_article`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `category_id`  BIGINT(20) NULL DEFAULT NULL COMMENT '所属类目id',
    `parent_id`    BIGINT(20) NULL DEFAULT '0' COMMENT '父节点id',
    `title`        VARCHAR(255) NOT NULL COMMENT '标题' COLLATE 'utf8mb4_general_ci',
    `description`  VARCHAR(255) NULL DEFAULT NULL COMMENT '描述' COLLATE 'utf8mb4_general_ci',
    `img`          VARCHAR(255) NULL DEFAULT NULL COMMENT '图片',
    `sort_num`     BIGINT(20) NULL DEFAULT '0' COMMENT '排序',
    `keywords`     VARCHAR(255) NULL DEFAULT NULL COMMENT '关键词，多个以英文逗号分割',
    `author`       VARCHAR(255) NULL DEFAULT NULL COMMENT '作者',
    `original_url` VARCHAR(255) NULL DEFAULT NULL COMMENT '原文地址',
    `is_top`       TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否置顶',
    `is_hot`       TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否热门',
    `status`       VARCHAR(50) NULL DEFAULT NULL COMMENT '状态',
    `source`       TINYINT(4) NOT NULL DEFAULT '1' COMMENT '来源',
    `level`        INT(11) NULL DEFAULT '1' COMMENT '星级',
    `read_num`     INT(11) NOT NULL DEFAULT '0' COMMENT '阅读数量',
    `collect_num`  INT(11) NOT NULL DEFAULT '0' COMMENT '收藏数量',
    `like_num`     INT(11) NOT NULL DEFAULT '0' COMMENT '点赞数量',
    `comment_num`  INT(11) NOT NULL DEFAULT '0' COMMENT '评论数量',
    `create_id`    BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`  DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`    BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`  DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `UNI_CATEGORY_SORT` (`category_id`, `sort_num`) USING BTREE
) COMMENT='网站文章表' COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `web_content`
(
    `id`               BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `article_id`       BIGINT(20) NOT NULL COMMENT '简介id',
    `content`          LONGTEXT NULL DEFAULT NULL COMMENT '内容' COLLATE 'utf8mb4_general_ci',
    `original_content` LONGTEXT NULL DEFAULT NULL COMMENT '原始内容' COLLATE 'utf8mb4_general_ci',
    `create_id`        BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`      DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`        BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`      DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='网站文章内容' COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `web_user_operation`
(
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `data_type`   TINYINT(4) NOT NULL DEFAULT '1' COMMENT '类型',
    `data_id`     BIGINT(20) NOT NULL COMMENT '数据id',
    `user_id`     BIGINT(20) NOT NULL COMMENT '用户id',
    `is_read`     TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否阅读',
    `is_like`     TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否点赞',
    `is_collect`  TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否收藏',
    `is_delete`   TINYINT(1) NULL DEFAULT '0' COMMENT '是否删除',
    `create_id`   BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time` DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`   BIGINT(20) NOT NULL COMMENT '更新人',
    `update_time` DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX         `idx_title_id` (`data_type`, `data_id`) USING BTREE
) COMMENT='网站用户操作表' COLLATE='utf8_general_ci' ENGINE=InnoDB;

CREATE TABLE `web_user_comment`
(
    `id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `data_id`      BIGINT(20) NULL DEFAULT NULL COMMENT '主数据ID',
    `top_id`       BIGINT(20) NULL DEFAULT '0' COMMENT '祖评论ID',
    `parent_id`    BIGINT(20) NULL DEFAULT '0' COMMENT '父评论ID',
    `user_id`      BIGINT(20) NOT NULL COMMENT '发表用户id',
    `to_user_id`   BIGINT(20) NOT NULL COMMENT '回复用户id',
    `content`      VARCHAR(255) NULL DEFAULT NULL COMMENT '评论内容' COLLATE 'utf8mb4_general_ci',
    `comment_type` TINYINT(4) NULL DEFAULT NULL COMMENT '评论类型',
    `status`       TINYINT(4) NULL DEFAULT NULL COMMENT '状态',
    `like_num`     INT(11) NULL DEFAULT '0' COMMENT '点赞数量',
    `comment_num`  INT(11) NULL DEFAULT '0' COMMENT '评论数量',
    `is_read`      TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否阅读',
    `is_delete`    TINYINT(1) NULL DEFAULT '0' COMMENT '是否删除',
    `create_id`    BIGINT(20) NULL DEFAULT NULL COMMENT '创建人',
    `create_time`  DATETIME NULL DEFAULT NULL COMMENT '创建日期',
    `update_id`    BIGINT(20) NULL DEFAULT NULL COMMENT '更新人',
    `update_time`  DATETIME NULL DEFAULT NULL COMMENT '更新日期',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT='网站用户评论表' COLLATE='utf8mb4_general_ci' ENGINE=InnoDB;





