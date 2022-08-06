/*
 Navicat Premium Data Transfer

 Source Server         : 本机-127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : 127.0.0.1:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 27/06/2022 02:10:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_api
-- ----------------------------
DROP TABLE IF EXISTS `admin_api`;
CREATE TABLE `admin_api`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '接口ID',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '接口路由',
  `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '接口唯一名称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '接口名称',
  `describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '接口描述',
  `is_enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '接口状态：1：正常，0：禁用',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `modify_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  UNIQUE INDEX `uk_key`(`key`) USING BTREE,
  UNIQUE INDEX `uk_path`(`path`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '接口权限关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_api
-- ----------------------------
INSERT INTO `admin_api` VALUES (1, '/admin/user/list', 'adminUser::list', '管理员列表', '', 1, '2022-06-24 22:31:21', '2022-06-24 22:31:21');
INSERT INTO `admin_api` VALUES (3, '/admin/user/detail', 'adminUser::detail', '管理员详情', '', 1, '2022-06-24 22:32:52', '2022-06-24 22:32:52');
INSERT INTO `admin_api` VALUES (4, '/admin/user/add', 'adminUser::add', '添加管理员', '', 1, '2022-06-24 22:34:04', '2022-06-24 22:34:04');
INSERT INTO `admin_api` VALUES (5, '/admin/user/edit', 'adminUser::edit', '编辑管理员', '', 1, '2022-06-24 22:34:11', '2022-06-24 22:34:11');
INSERT INTO `admin_api` VALUES (8, '/role/list', 'adminRole::list', '角色列表', '', 1, '2022-06-24 23:51:50', '2022-06-24 23:51:50');
INSERT INTO `admin_api` VALUES (10, '/role/add', 'adminRole::add', '添加角色', '', 1, '2022-06-24 23:51:53', '2022-06-24 23:51:53');
INSERT INTO `admin_api` VALUES (11, '/role/edit', 'adminRole::edit', '编辑角色', '', 1, '2022-06-24 23:51:54', '2022-06-24 23:51:54');
INSERT INTO `admin_api` VALUES (12, '/role/detail', 'adminRole::detail', '角色详情', '', 1, '2022-06-24 23:51:57', '2022-06-24 23:51:57');
INSERT INTO `admin_api` VALUES (26, '/admin/user/assign', 'adminUser::assign', '管理员权限列表', '', 1, '2022-06-25 21:53:20', '2022-06-25 21:53:20');
INSERT INTO `admin_api` VALUES (27, '/admin/user/role', 'adminUser::role', '管理员的角色列表', '', 1, '2022-06-24 22:34:54', '2022-06-24 22:34:54');
INSERT INTO `admin_api` VALUES (28, '/admin/role/enable', 'adminRole::enable', '启用禁用角色', '', 1, '2022-06-26 15:21:54', '2022-06-26 15:21:54');
INSERT INTO `admin_api` VALUES (29, '/admin/role/permission', 'adminRole::permission', '角色权限', '', 1, '2022-06-25 21:28:18', '2022-06-25 21:28:20');
INSERT INTO `admin_api` VALUES (30, '/admin/role/assign', 'adminRole::assign', '角色分配权限', '', 1, '2022-06-25 21:29:06', '2022-06-25 21:29:09');
INSERT INTO `admin_api` VALUES (31, '/admin/api/list', 'adminApi::list', '接口列表', '', 1, '2022-06-26 15:19:52', '2022-06-26 15:19:55');
INSERT INTO `admin_api` VALUES (32, '/admin/api/add', 'adminApi::add', '接口添加', '', 1, '2022-06-26 15:20:20', '2022-06-26 15:20:22');
INSERT INTO `admin_api` VALUES (33, '/admin/api/detail', 'adminApi::detail', '接口详情', '', 1, '2022-06-26 15:20:41', '2022-06-26 15:20:44');
INSERT INTO `admin_api` VALUES (34, '/admin/api/edit', 'adminApi::edit', '接口编辑', '接口编辑', 1, '2022-06-26 16:09:03', '2022-06-26 16:09:03');
INSERT INTO `admin_api` VALUES (35, '/admin/api/enable', 'adminApi::enable', '接口禁用启用', '', 1, '2022-06-26 15:21:42', '2022-06-26 15:21:45');
INSERT INTO `admin_api` VALUES (36, '/admin/api/delete', 'adminApi::delete', '接口删除', '', 1, '2022-06-26 15:22:23', '2022-06-26 15:22:25');
INSERT INTO `admin_api` VALUES (50, '/admin/menu/list', 'adminMenu::list', '菜单列表', '', 1, '2022-06-27 01:56:54', '2022-06-27 01:56:54');

-- ----------------------------
-- Table structure for admin_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_menu`;
CREATE TABLE `admin_menu`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `parent_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '父ID',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限中文名称',
  `key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '菜单的唯一键名',
  `describe` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '路径图标',
  `sort` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '排序值',
  `redirect` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '重定向路径',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '组件名称',
  `is_hide_in_menu` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否隐藏：0显示，1隐藏',
  `is_hide_children_in_menu` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否在children中隐藏：1隐藏，0显示',
  `is_enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '1：启用，0禁用',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  UNIQUE INDEX `uk_key`(`key`) USING BTREE,
  UNIQUE INDEX `uk_path`(`path`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '权限菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_menu
-- ----------------------------
INSERT INTO `admin_menu` VALUES (1, 0, '/admin/setting', '系统设置', 'adminSetting', '系统设置', '', 0, '/', '', 0, 0, 1, '2022-06-26 22:28:24', '2022-06-26 22:28:24');
INSERT INTO `admin_menu` VALUES (2, 1, '/admin/menu', '菜单管理', 'adminMenu', '菜单列表', '', 0, '/', '', 0, 0, 1, '2022-06-26 22:29:08', '2022-06-26 22:52:31');

-- ----------------------------
-- Table structure for admin_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission`;
CREATE TABLE `admin_permission`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `menu_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '所属菜单ID',
  `key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限唯一标识名称',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限显示名称',
  `type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT 'view' COMMENT '权限的操作类型\r\nview：查看（只读）\r\nedit：编辑（读写）',
  `describe` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '权限描述',
  `is_enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '是否启用：1启用，0禁用',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_key`(`key`) USING BTREE,
  UNIQUE INDEX `uk_permission`(`menu_id`, `type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_permission
-- ----------------------------
INSERT INTO `admin_permission` VALUES (1, 2, 'AdminMenuView', '菜单查看', 'view', '菜单查看', 1, '2022-06-27 01:24:10', '2022-06-27 01:24:10');
INSERT INTO `admin_permission` VALUES (4, 2, 'AdminMenuEdit', '菜单编辑', 'edit', '菜单编辑', 1, '2022-06-27 01:24:01', '2022-06-27 01:24:01');

-- ----------------------------
-- Table structure for admin_permission_api
-- ----------------------------
DROP TABLE IF EXISTS `admin_permission_api`;
CREATE TABLE `admin_permission_api`  (
  `permission_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '权限ID',
  `api_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '接口ID',
  UNIQUE INDEX `uk_permission_api`(`permission_id`, `api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '接口权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_permission_api
-- ----------------------------
INSERT INTO `admin_permission_api` VALUES (1, 50);

-- ----------------------------
-- Table structure for admin_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_role`;
CREATE TABLE `admin_role`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '角色名称',
  `describe` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '角色描述',
  `modify_admin_id` int(10) UNSIGNED NOT NULL COMMENT '修改人',
  `create_admin_id` int(10) UNSIGNED NOT NULL COMMENT '创建人',
  `is_enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '1：启用，0：禁用',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '管理员角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role
-- ----------------------------
INSERT INTO `admin_role` VALUES (2, '开发', '开发', 1, 1, 1, '2022-06-24 22:26:29', '2022-06-24 22:26:29');
INSERT INTO `admin_role` VALUES (5, '测试', '测试', 1, 1, 0, '2022-06-25 00:07:57', '2022-06-25 00:07:57');

-- ----------------------------
-- Table structure for admin_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `admin_role_permission`;
CREATE TABLE `admin_role_permission`  (
  `role_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  `permission_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '权限ID',
  UNIQUE INDEX `uk_role_permission`(`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '角色权限关系表（包含菜单和权限）' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_role_permission
-- ----------------------------
INSERT INTO `admin_role_permission` VALUES (5, 1);
INSERT INTO `admin_role_permission` VALUES (5, 2);
INSERT INTO `admin_role_permission` VALUES (5, 3);
INSERT INTO `admin_role_permission` VALUES (5, 4);
INSERT INTO `admin_role_permission` VALUES (5, 5);

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '管理员账号',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '管理昵称姓名',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '邮箱地址',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '登录密码',
  `avatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户头像',
  `login_total` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '登录次数',
  `last_login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '上次登录IP',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `is_enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '账户状态：1正常，0：禁用',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `modify_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '管理员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES (1, 'admin', 'admin-test', '', '$2a$10$eLtqoUPF9EpyXM1zlvBgG.erAygj155hlw4bP9C3W49AlhRTPZKQq', '', 787, '127.0.0.1,127.0.0.1', '2022-06-27 00:24:49', 1, '2022-06-27 00:24:48', '2022-06-27 00:24:48');
INSERT INTO `admin_user` VALUES (2, 'test', 'test', '', '$2a$10$cMLPoyArndwDtVXNTCKExe5dTAwHBxoNceiXrf95PoKO4UzFSdOaG', '', 0, '', NULL, 1, '2022-06-23 23:53:30', '2022-06-23 23:53:30');
INSERT INTO `admin_user` VALUES (4, 'wlp', 'wlp', '', '$2a$10$cMLPoyArndwDtVXNTCKExe5dTAwHBxoNceiXrf95PoKO4UzFSdOaG', '', 0, '', NULL, 1, '2022-06-23 23:53:33', '2022-06-23 23:53:33');
INSERT INTO `admin_user` VALUES (6, 'wll', 'wll', '', '$2a$10$x3nxXTRKI.MWJfbP.j2Ubu4c6NoFEpPLKgMJBWlHX/3FmbYo9UVQC', '', 0, '', NULL, 1, '2022-06-26 14:49:38', '2022-06-26 14:49:38');

-- ----------------------------
-- Table structure for admin_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_user_role`;
CREATE TABLE `admin_user_role`  (
  `admin_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '管理员ID',
  `role_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '角色ID',
  UNIQUE INDEX `uk_admin_id_role_id`(`admin_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '管理员-游戏-角色关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin_user_role
-- ----------------------------
INSERT INTO `admin_user_role` VALUES (2, 2);
INSERT INTO `admin_user_role` VALUES (2, 5);

SET FOREIGN_KEY_CHECKS = 1;
