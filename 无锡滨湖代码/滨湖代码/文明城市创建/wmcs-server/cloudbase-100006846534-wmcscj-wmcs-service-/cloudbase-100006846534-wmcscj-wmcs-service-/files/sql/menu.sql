/*
 Navicat Premium Data Transfer

 Source Server         : 井初mysql数据库
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : wxgis.cn:9999
 Source Schema         : civilization

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 14/06/2023 10:04:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
                             `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                             `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人员id',
                             `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
                             `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
                             `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
                             `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '修改人员id',
                             `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
                             `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '组件链接',
                             `frame_flag` int(11) NULL DEFAULT 0 COMMENT '是否外链',
                             `icon` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图标',
                             `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
                             `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单名称',
                             `order_num` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
                             `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '父级id',
                             `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由地址',
                             `perms` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '权限标识',
                             `query` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '路由参数',
                             `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单状态（0正常 1停用）',
                             `visible` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '菜单状态（0显示 1隐藏）',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('100', 'admin', '2023-06-14 09:18:39', 0, NULL, NULL, NULL, NULL, 1, NULL, 'M', '系统运维', 9, '0', 'system', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('1100', 'admin', '2023-06-14 09:22:04', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '菜单管理', 1, '100', 'menu', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('1200', 'admin', '2023-06-14 09:22:29', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '角色管理', 2, '100', 'role', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('1300', 'admin', '2023-06-14 09:22:29', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '单位管理', 1, '200', 'dept', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('1400', 'admin', '2023-06-14 09:22:50', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '用户管理', 3, '100', 'user', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('1500', 'admin', '2023-06-14 09:23:48', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '登陆日志', 4, '100', 'logininfor', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('1600', 'admin', '2023-06-14 09:24:05', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '操作日志', 5, '100', 'operlog', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('200', 'admin', '2023-06-14 09:19:42', 0, NULL, NULL, NULL, NULL, 1, NULL, 'M', '数据中心', 1, '0', 'data', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2100', 'admin', '2023-06-14 09:26:48', 0, NULL, NULL, NULL, NULL, 1, '', 'C', '监控设备', 2, '200', 'device', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2210', 'admin', '2023-06-14 09:29:22', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '督察点位', 3, '200', 'inspectionPoints', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2220', 'admin', '2023-06-14 09:29:44', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '督察人员', 4, '200', 'inspector', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2310', 'admin', '2023-06-14 09:30:14', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '文明村镇', 5, '200', 'civilizationTown', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2320', 'admin', '2023-06-14 09:30:39', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '文明单位', 6, '200', 'civilizationCompany', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2330', 'admin', '2023-06-14 09:30:52', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '文明校园', 7, '200', 'civilizationSchool', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2340', 'admin', '2023-06-14 09:31:21', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '文明家庭', 8, '200', 'civilizationFamily', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2410', 'admin', '2023-06-14 09:31:41', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '志愿人员', 9, '200', 'volunteer', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2420', 'admin', '2023-06-14 09:32:35', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '志愿团队', 10, '200', 'volunteerTeam', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('2430', 'admin', '2023-06-14 09:32:03', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '志愿活动', 11, '200', 'volunteerActivity', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('300', 'admin', '2023-06-14 09:20:01', 0, NULL, NULL, NULL, NULL, 1, NULL, 'M', '工单中心', 2, '0', 'event', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('3100', 'admin', '2023-06-14 09:37:01', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '工单总览', 1, '300', 'record', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('3200', 'admin', '2023-06-14 09:38:47', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '告警信息', 2, '300', 'warning', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('3300', 'admin', '2023-06-14 09:39:16', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '现象工单', 3, '300', 'appearance', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('3400', 'admin', '2023-06-14 09:39:54', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '统计分析', 4, '300', 'statistic', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('400', 'admin', '2023-06-14 09:20:20', 0, NULL, NULL, NULL, NULL, 1, NULL, 'M', '指标中心', 3, '0', 'quota', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('4100', 'admin', '2023-06-14 09:40:32', 0, NULL, NULL, NULL, NULL, 1, NULL, 'C', '事件类型', 1, '400', 'eventType', NULL, NULL, '0', '0');
INSERT INTO `sys_menu` VALUES ('500', 'admin', '2023-06-14 09:20:47', 0, NULL, NULL, NULL, NULL, 1, NULL, 'M', '考核中心', 4, '0', 'assess', NULL, NULL, '0', '0');

SET FOREIGN_KEY_CHECKS = 1;
