-- ----------------------------
-- sys_base_config 基础设置表
-- ----------------------------
CREATE TABLE `sys_base_config`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `auto_give` int(11) NULL DEFAULT 1 COMMENT '是否自动派发',
    `limit_time` int(11) NULL DEFAULT NULL COMMENT '处置时限',
    `limit_time_unit` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处置时限单位',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '系统名称',
    `examine_dept_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核部门id',
    `appeal_examine_dept_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `extension_examine_dept_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统配置值' ROW_FORMAT = Dynamic;

INSERT INTO `sys_base_config` VALUES ('1', NULL, '2023-10-19 00:22:07', 0, NULL, 'wmcsadmin', '2023-11-07 14:19:19.575000', 1, 1, '1', '滨湖区文明城市创建平台', NULL, NULL, NULL);

-- ----------------------------
-- sys_user 用户表
-- ----------------------------
CREATE TABLE `sys_user`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
    `dept_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '归属部门id',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `login_date` datetime(6) NULL DEFAULT NULL COMMENT '最后登陆时间',
    `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登陆IP',
    `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名称',
    `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录密码',
    `phonenumber` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
    `sex` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户性别',
    `status` int(11) NULL DEFAULT 0 COMMENT '用户状态',
    `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户账号',
    `birthday` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '生日',
    `position` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;
-- ----------------------------
-- 登录用户：wmcsadmin 密码：wmcsadmin147$PM
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', NULL, '2023-05-17 20:32:41', 0, NULL, NULL, '2023-05-17 20:32:41', NULL, NULL, NULL, NULL, NULL, '管理员', '$2a$10$lfRADoDXnjv2b51i2Z2qY.k7mFCs69z7Df/AhDVnXUgQt4q1YZPTS', NULL, NULL, 0, 'wmcsadmin', NULL, NULL);


-- ----------------------------
-- sys_role 角色表
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色权限字符串',
    `data_scope` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
    `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
    `status` int(11) NULL DEFAULT 0 COMMENT '状态',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_role` VALUES ('1000000001', 'wmcsadmin', '2023-07-27 15:21:34', 0, NULL, 'wmcsadmin', '2024-01-11 17:55:21.574000', 'system_admin', '1', '系统管理员', 0, 0);
INSERT INTO `sys_role` VALUES ('1000000002', 'wmcsadmin', '2023-07-27 15:21:34', 0, NULL, 'wmcsadmin', '2024-01-11 17:56:05.566000', 'technology', '1', '技术支撑', 1, 0);
INSERT INTO `sys_role` VALUES ('1000000003', 'wmcsadmin', '2023-07-27 15:21:34', 0, NULL, 'wmcsadmin', '2024-01-11 10:52:00.680000', 'default', '5', '角色初始化', 10, 0);
INSERT INTO `sys_role` VALUES ('1000000004', 'wmcsadmin', '2023-07-27 15:21:34', 0, NULL, 'wmcsadmin', '2024-01-11 10:53:08.261000', 'bigData', '1', '滨湖大数据', 10, 0);

-- ----------------------------
-- sys_user_role 用户角色配置表
-- ----------------------------
CREATE TABLE `sys_user_role`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色',
    `user_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- sys_menu 菜单资源
-- ----------------------------
CREATE TABLE `sys_menu`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件链接',
    `frame_flag` int(11) NULL DEFAULT 0 COMMENT '是否外链',
    `icon` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
    `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
    `order_num` int(11) NULL DEFAULT 10 COMMENT '显示顺序',
    `parent_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '父级id',
    `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
    `perms` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
    `query` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由参数',
    `status` int(11) NULL DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
    `visible` int(11) NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
    `link` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单地址：全路径地址',
    `platform` int(11) NULL DEFAULT 1 COMMENT '平台：1PC；2APP',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_menu` VALUES ('100100000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '配置中心', 999, '0', NULL, 'Config', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100100001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '资源配置', 10, '100100000000', NULL, 'MenuConfig', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100100002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '角色管理', 20, '100100000000', NULL, 'RoleConfig', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100100003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '字典管理', 30, '100100000000', NULL, 'DictConfig', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100100004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '系统配置', 40, '100100000000', NULL, 'projectPublish', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100101000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-indent', 'C', '首页', 0, '0', NULL, NULL, NULL, 0, 0, '/home/statis/total/index', 1);
INSERT INTO `sys_menu` VALUES ('100102000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-cog', 'M', '运维中心', 60, '0', NULL, NULL, NULL, 0, 0, '/home/system', 1);
INSERT INTO `sys_menu` VALUES ('100102001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-map', 'C', '街道管理', 10, '100102000000', NULL, NULL, NULL, 0, 0, '/home/system/region/list', 1);
INSERT INTO `sys_menu` VALUES ('100102002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-user', 'C', '电子工牌', 20, '100102000000', NULL, NULL, NULL, 0, 0, '/home/system/employee/list', 1);
INSERT INTO `sys_menu` VALUES ('100102003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-cloud-download', 'C', '同步账号', 40, '100102000000', NULL, NULL, NULL, 0, 0, '/home/synch/unityUser/list', 1);
INSERT INTO `sys_menu` VALUES ('100102004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-steam-square', 'C', '设备管理', 30, '100102000000', NULL, NULL, NULL, 0, 0, '/home/data/equipment/list', 1);
INSERT INTO `sys_menu` VALUES ('100103000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-window-restore', 'M', '数据中心', 10, '0', NULL, NULL, NULL, 0, 0, '/home/data', 1);
INSERT INTO `sys_menu` VALUES ('100103001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-user-circle', 'C', '督察员管理', 100, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/checkUser/list', 1);
INSERT INTO `sys_menu` VALUES ('100103002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-flag-checkered', 'C', '督查点位管理', 110, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/checkPoints/list', 1);
INSERT INTO `sys_menu` VALUES ('100103003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-cubes', 'C', '督查点位类型', 120, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/checkPointsType/list', 1);
INSERT INTO `sys_menu` VALUES ('100103004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-compass', 'C', '单位管理', 130, '100103000000', NULL, NULL, NULL, 0, 0, '/home/system/dept/list', 1);
INSERT INTO `sys_menu` VALUES ('100103005000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-codepen', 'C', '文明村镇', 200, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/civilizationTown/list', 1);
INSERT INTO `sys_menu` VALUES ('100103006000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-university', 'C', '文明单位', 210, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/civilizationCompany/list', 1);
INSERT INTO `sys_menu` VALUES ('100103007000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-mortar-board', 'C', '文明校园', 220, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/civilizationSchool/list', 1);
INSERT INTO `sys_menu` VALUES ('100103008000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-facebook-official', 'C', '文明家庭', 230, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/civilizationFamily/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-shield', 'C', '典型先进人物', 240, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/civilizationAdvanced/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009100', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-user-plus', 'C', '志愿者', 300, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/volunteer/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009200', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-users', 'C', '志愿团队', 310, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/volunteerTeam/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009300', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-bullhorn', 'C', '志愿服务活动', 320, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/volunteerActivity/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009400', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-video-camera', 'C', '视频监控', 410, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/device/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009500', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-calculator', 'C', '点位算法配置', 420, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/checkPointsDevice/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009600', 'wmcsadmin', '2023-12-27 18:02:34', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-street-view', 'C', '视频组织', 400, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/deviceTree/list', 1);
INSERT INTO `sys_menu` VALUES ('100103009700', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-folder', 'C', '知识库', 500, '100103000000', NULL, NULL, NULL, 0, 0, '/home/data/knowledge/list', 1);
INSERT INTO `sys_menu` VALUES ('100104000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-hand-o-right', 'M', '协同中心', 20, '0', NULL, NULL, NULL, 0, 0, '/home/disposal', 1);
INSERT INTO `sys_menu` VALUES ('100104001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa  fa-th', 'C', '事件总览', 100, '100104000000', NULL, NULL, NULL, 0, 0, '/home/analysis/all/list', 1);
INSERT INTO `sys_menu` VALUES ('100104002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-calendar-plus-o', 'C', '我的上报', 110, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/eventReport/list', 1);
INSERT INTO `sys_menu` VALUES ('100104003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa  fa-calendar-o', 'C', '待派发', 120, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/eventGive/list', 1);
INSERT INTO `sys_menu` VALUES ('100104004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa  fa-calendar-o', 'C', '待处理', 130, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/allocate/list', 1);
INSERT INTO `sys_menu` VALUES ('100104005000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-calendar-check-o', 'C', '已处理', 140, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/allocated/list', 1);
INSERT INTO `sys_menu` VALUES ('100104006000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-calendar-o', 'C', '待处置', 150, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/handle/list', 1);
INSERT INTO `sys_menu` VALUES ('100104007000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa  fa-calendar-check-o', 'C', '已处置', 160, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/handled/list', 1);
INSERT INTO `sys_menu` VALUES ('100104008000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-calendar-o', 'C', '待核查', 170, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/check/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-calendar-check-o', 'C', '已办结', 180, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/finished/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009100', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-question', 'C', '申诉工单', 200, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/appeal/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009200', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-expand', 'C', '延期事件', 210, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/extension/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009201', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '列表', 10, '100104009200', NULL, 'ExtensionList', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100104009202', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'F', '审核', 10, '100104009200', NULL, 'ExtensionExamine', NULL, 0, 0, '', 1);
INSERT INTO `sys_menu` VALUES ('100104009300', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-volume-control-phone', 'C', '转12345', 220, '100104000000', NULL, NULL, NULL, 0, 1, '/home/disposal/citizen/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009400', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-envelope', 'C', '通知消息', 300, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/notice/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009500', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-wpforms', 'C', '事项管理', 500, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/eventType/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009600', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-adjust', 'C', '考核周期', 510, '100104000000', NULL, NULL, NULL, 0, 0, '/home/disposal/assessCycle/list', 1);
INSERT INTO `sys_menu` VALUES ('100104009900', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-list-alt', 'C', '历史事件', 0, '100104000000', NULL, NULL, NULL, 0, 0, '/home/analysis/event/list', 1);
INSERT INTO `sys_menu` VALUES ('100105000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-bookmark', 'M', '工单中心', 30, '0', NULL, NULL, NULL, 0, 0, '/home/event', 1);
INSERT INTO `sys_menu` VALUES ('100105001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-warning', 'C', '告警管理', 10, '100105000000', NULL, NULL, NULL, 0, 0, '/home/event/warning/list', 1);
INSERT INTO `sys_menu` VALUES ('100105002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-book', 'C', '工单管理', 20, '100105000000', NULL, NULL, NULL, 0, 0, '/home/event/record/list', 1);
INSERT INTO `sys_menu` VALUES ('100105003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-check-square-o', 'C', '办结审核', 30, '100105000000', NULL, NULL, NULL, 0, 0, '/home/event/finishConfirm/list', 1);
INSERT INTO `sys_menu` VALUES ('100105004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-anchor', 'C', '现象工单', 40, '100105000000', NULL, NULL, NULL, 0, 0, '/home/event/appearance/list', 1);
INSERT INTO `sys_menu` VALUES ('100105005000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-bar-chart-o', 'C', '统计分析', 50, '100105000000', NULL, NULL, NULL, 0, 0, '/home/analysis/statistic/list', 1);
INSERT INTO `sys_menu` VALUES ('100105006000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-hourglass-half', 'C', '阈值管理', 100, '100105000000', NULL, NULL, NULL, 0, 0, '/home/event/threshold/list', 1);
INSERT INTO `sys_menu` VALUES ('100105007000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-space-shuttle', 'C', '时间配置', 110, '100105000000', NULL, NULL, NULL, 0, 0, '/home/event/timeConfig/list', 1);
INSERT INTO `sys_menu` VALUES ('100105008000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-bicycle', 'C', '巡查任务', 210, '100105000000', NULL, NULL, NULL, 0, 0, '/home/disposal/taskpublish/list', 1);
INSERT INTO `sys_menu` VALUES ('100106000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'el-icon-s-home', 'M', '指标中心', 40, '0', NULL, NULL, NULL, 0, 0, '/home/quota', 1);
INSERT INTO `sys_menu` VALUES ('100106001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-server', 'C', '点位检查项', 10, '100106000000', NULL, NULL, NULL, 0, 0, '/home/quota/checkItem/list', 1);
INSERT INTO `sys_menu` VALUES ('100106002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-tumblr-square', 'C', '分类管理', 20, '100106000000', NULL, NULL, NULL, 0, 0, '/home/quota/indicatorType/list', 1);
INSERT INTO `sys_menu` VALUES ('100106003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-list-ol', 'C', '指标管理', 30, '100106000000', NULL, NULL, NULL, 0, 0, '/home/quota/indicator/list', 1);
INSERT INTO `sys_menu` VALUES ('100106004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-pie-chart', 'C', '权重配置', 40, '100106000000', NULL, NULL, NULL, 0, 0, '/home/quota/weight/list', 1);
INSERT INTO `sys_menu` VALUES ('100107000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-tags', 'M', '考核中心', 50, '0', NULL, NULL, NULL, 0, 0, '/home/assess', 1);
INSERT INTO `sys_menu` VALUES ('100107001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-star', 'C', '区指数', 10, '100107000000', NULL, NULL, NULL, 0, 0, '/home/assess/regionIndex/list', 1);
INSERT INTO `sys_menu` VALUES ('100107002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-trophy', 'C', '文明程度排行', 20, '100107000000', NULL, NULL, NULL, 0, 0, '/home/assess/civilizationLevel/list', 1);
INSERT INTO `sys_menu` VALUES ('100107003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-pencil', 'C', '月度评测', 100, '100107000000', NULL, NULL, NULL, 0, 0, '/home/assess/month/list', 1);
INSERT INTO `sys_menu` VALUES ('100107004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-pencil-square', 'C', '季度评测', 110, '100107000000', NULL, NULL, NULL, 0, 0, '/home/assess/evaluating/list', 1);
INSERT INTO `sys_menu` VALUES ('100107005000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-pencil-square-o', 'C', '年度评测', 120, '100107000000', NULL, NULL, NULL, 0, 0, '/home/assess/year/list', 1);
INSERT INTO `sys_menu` VALUES ('100108000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-industry', 'M', '统计分析', 90, '0', NULL, NULL, NULL, 0, 0, '/statistics', 1);
INSERT INTO `sys_menu` VALUES ('100108001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-vimeo-square', 'C', '问题集中点', 10, '100108000000', NULL, NULL, NULL, 0, 0, '/home/keyPoints/jz/list', 1);
INSERT INTO `sys_menu` VALUES ('100108002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-h-square', 'C', '问题高发点', 20, '100108000000', NULL, NULL, NULL, 0, 0, '/home/keyPoints/gf/list', 1);
INSERT INTO `sys_menu` VALUES ('100108003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-bug', 'C', '顽症分析', 30, '100108000000', NULL, NULL, NULL, 0, 0, '/home/keyPoints/wz/list', 1);
INSERT INTO `sys_menu` VALUES ('100108004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-line-chart', 'C', '区域指数统计分析', 200, '100108000000', NULL, NULL, NULL, 0, 0, '/home/analysis/trend/list', 1);
INSERT INTO `sys_menu` VALUES ('100108005000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-line-chart', 'C', '单位指数统计分析', 210, '100108000000', NULL, NULL, NULL, 0, 0, '/home/analysis/deptunit/list', 1);
INSERT INTO `sys_menu` VALUES ('100109000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-connectdevelop', 'M', '专项追踪', 100, '0', NULL, NULL, NULL, 0, 0, '/main', 1);
INSERT INTO `sys_menu` VALUES ('100109001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-pinterest-square', 'C', '专项监测', 10, '100109000000', NULL, NULL, NULL, 0, 0, '/home/specialTracking/specialMonitoring/list', 1);
INSERT INTO `sys_menu` VALUES ('100109002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-question-circle', 'C', '重点问题追踪', 20, '100109000000', NULL, NULL, NULL, 0, 0, '/home/specialTracking/mainEventList/list', 1);
INSERT INTO `sys_menu` VALUES ('100109003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'fa fa-rebel', 'C', '重点点位追踪', 30, '100109000000', NULL, NULL, NULL, 0, 0, '/home/specialTracking/mainPointsList/list', 1);
INSERT INTO `sys_menu` VALUES ('100201000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'M', '领导驾驶舱', 10, '0', NULL, NULL, NULL, 0, 0, '/', 2);
INSERT INTO `sys_menu` VALUES ('100201001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/dwcj.png', 'C', '分析总览', 10, '100201000000', NULL, NULL, NULL, 0, 0, './../screen/home', 2);
INSERT INTO `sys_menu` VALUES ('100201002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/kqdkdate.png', 'C', '事件总览', 30, '100201000000', NULL, NULL, '{}', 0, 0, './../gongdan/zonglan', 2);
INSERT INTO `sys_menu` VALUES ('100201003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/wdwg.png', 'C', '绩效考核', 20, '100201000000', NULL, NULL, NULL, 0, 0, './../screen/jixiaokaohe', 2);
INSERT INTO `sys_menu` VALUES ('100201004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/kqdk.png', 'C', '历史工单', 40, '100201000000', NULL, NULL, NULL, 0, 0, './../screen/gongdan', 2);
INSERT INTO `sys_menu` VALUES ('100202000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'M', '文明创建', 20, '0', NULL, NULL, NULL, 0, 0, '/', 2);
INSERT INTO `sys_menu` VALUES ('100202001000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/add.png', 'C', '工单上报', 10, '100202000000', NULL, NULL, NULL, 0, 0, './../gongdan/shangbao', 2);
INSERT INTO `sys_menu` VALUES ('100202002000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/xxyd.png', 'C', '我的上报', 20, '100202000000', NULL, NULL, NULL, 0, 0, './../gongdan/wodeshangbao', 2);
INSERT INTO `sys_menu` VALUES ('100202003000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/sjsb.png', 'C', '待处理', 30, '100202000000', NULL, NULL, '{\"flag\":true}', 0, 0, './../gongdan/daifenbo', 2);
INSERT INTO `sys_menu` VALUES ('100202004000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/sqjzry.png', 'C', '已处理', 40, '100202000000', NULL, NULL, '{\"flag\":false}', 0, 0, './../gongdan/daifenbo', 2);
INSERT INTO `sys_menu` VALUES ('100202005000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/sjsb.png', 'C', '待处置', 50, '100202000000', NULL, NULL, '{\"flag\":true}', 0, 0, './../gongdan/daichuzhi', 2);
INSERT INTO `sys_menu` VALUES ('100202006000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/xcrw.png', 'C', '已处置', 60, '100202000000', NULL, NULL, '{\"flag\":false}', 0, 0, './../gongdan/daichuzhi', 2);
INSERT INTO `sys_menu` VALUES ('100202007000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/tzgg.png', 'C', '待核查', 70, '100202000000', NULL, NULL, '{\"flag\":true}', 0, 0, './../gongdan/daihecha', 2);
INSERT INTO `sys_menu` VALUES ('100202008000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/wdpm.png', 'C', '已办结', 80, '100202000000', NULL, NULL, '{\"flag\":false}', 0, 0, './../gongdan/daihecha', 2);
INSERT INTO `sys_menu` VALUES ('100202009100', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/dbjs.png', 'C', '延期事件', 110, '100202000000', NULL, NULL, NULL, 0, 0, './../gongdan/yanqishijian', 2);
INSERT INTO `sys_menu` VALUES ('100202009200', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/fwdj.png', 'C', '转12345', 120, '100202000000', NULL, NULL, NULL, 0, 1, './../gongdan/z12345', 2);
INSERT INTO `sys_menu` VALUES ('100203000000', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, '', 'M', '基础数据', 0, '0', NULL, NULL, NULL, 0, 0, '/', 2);
INSERT INTO `sys_menu` VALUES ('100203000100', 'wmcsadmin', '2024-01-11 18:30:02', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/wdwg.png', 'C', '责任单位', 10, '100203000000', NULL, NULL, NULL, 0, 0, './../data/zerendanwei', 2);
INSERT INTO `sys_menu` VALUES ('100203000200', 'wmcsadmin', '2024-01-11 18:30:42', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/zdqyxc.png', 'C', '点位类型', 20, '100203000000', NULL, NULL, NULL, 0, 0, './../data/dianweileixing', 2);
INSERT INTO `sys_menu` VALUES ('100203000300', 'wmcsadmin', '2024-01-11 18:31:07', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/wghdtj.png', 'C', '点位检查项', 30, '100203000000', NULL, NULL, NULL, 0, 0, './../data/jianchaxiang', 2);
INSERT INTO `sys_menu` VALUES ('100203000800', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/kqdkdate.png', 'C', '我的定位', 300, '100203000000', NULL, NULL, NULL, 0, 0, './../gongdan/mygps', 2);
INSERT INTO `sys_menu` VALUES ('100203000900', 'wmcsadmin', '2023-06-27 14:30:57', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/kqdkdate.png', 'C', '巡查点位', 310, '100203000000', NULL, NULL, NULL, 0, 0, './../gongdan/mypatrol', 2);
INSERT INTO `sys_menu` VALUES ('100204000000', 'wmcsadmin', '2024-01-11 18:27:56', 0, NULL, NULL, NULL, NULL, 0, '', 'M', '实践培育', 30, '0', NULL, NULL, NULL, 0, 0, '/', 2);
INSERT INTO `sys_menu` VALUES ('100204000100', 'wmcsadmin', '2024-01-11 18:31:48', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/qthd.png', 'C', '文明村镇', 10, '100204000000', NULL, NULL, NULL, 0, 0, './../data/wenmingjiating', 2);
INSERT INTO `sys_menu` VALUES ('100204000200', 'wmcsadmin', '2024-01-11 18:32:09', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/sqhd.png', 'C', '文明单位', 20, '100204000000', NULL, NULL, NULL, 0, 0, './../data/wenmingdanwei', 2);
INSERT INTO `sys_menu` VALUES ('100204000300', 'wmcsadmin', '2024-01-11 18:32:33', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/wgxc.png', 'C', '文明校园', 30, '100204000000', NULL, NULL, NULL, 0, 0, './../data/wenmingxiaoyuan', 2);
INSERT INTO `sys_menu` VALUES ('100204000400', 'wmcsadmin', '2024-01-11 18:33:02', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/qthd.png', 'C', '文明家庭', 40, '100204000000', NULL, NULL, NULL, 0, 0, './../data/wenmingjiating', 2);
INSERT INTO `sys_menu` VALUES ('100204000500', 'wmcsadmin', '2024-01-11 18:33:27', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/sqjzry.png', 'C', '典型先进人物', 50, '100204000000', NULL, NULL, NULL, 0, 0, './../data/dianxingxianjinrenwu', 2);
INSERT INTO `sys_menu` VALUES ('100204000600', 'wmcsadmin', '2024-01-11 18:33:55', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/zxxx.png', 'C', '志愿活动', 60, '100204000000', NULL, NULL, NULL, 0, 0, './../data/zhiyuanhuodong', 2);
INSERT INTO `sys_menu` VALUES ('100204000700', 'wmcsadmin', '2024-01-11 18:34:36', 0, NULL, NULL, NULL, NULL, 0, 'static/images/workDesk/wgxc.png', 'C', '志愿者', 100, '100204000000', NULL, NULL, NULL, 0, 0, './../data/zhiyuanzhe', 2);


-- ----------------------------
-- sys_dept 部门表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `ancestors` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '祖级列表',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人',
    `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名称',
    `order_num` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
    `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
    `status` int(11) NULL DEFAULT 0 COMMENT '部门状态（0正常 1停用）',
    `type` int(11) NULL DEFAULT 0 COMMENT '类型: 1-职能部门单位; 2-区划;',
    `wechat_dept_id` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '绑定政务微信部门id-用于和事件对接分派单位id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_dept` VALUES ('1', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.688000', '0,1', NULL, NULL, '滨湖区', 0, '0', NULL, 0, 2, '373');
INSERT INTO `sys_dept` VALUES ('100', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.690000', '0,100', NULL, NULL, '区商务局', 0, '0', NULL, 0, 1, '4549');
INSERT INTO `sys_dept` VALUES ('101', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.690000', '0,101', NULL, NULL, '区文体旅游局', 0, '0', NULL, 0, 1, '4566');
INSERT INTO `sys_dept` VALUES ('102', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.691000', '0,102', NULL, NULL, '区卫生健康委', 0, '0', NULL, 0, 1, '4578');
INSERT INTO `sys_dept` VALUES ('103', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.691000', '0,103', NULL, NULL, '区行政审批局', 0, '0', NULL, 0, 1, '4627');
INSERT INTO `sys_dept` VALUES ('104', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.691000', '0,104', NULL, NULL, '区市场监管局', 0, '0', NULL, 0, 1, '4642');
INSERT INTO `sys_dept` VALUES ('105', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.692000', '0,105', NULL, NULL, '区地方金融监管局', 0, '0', NULL, 0, 1, '5901');
INSERT INTO `sys_dept` VALUES ('106', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.692000', '0,106', NULL, NULL, '区公安分局', 0, '0', NULL, 0, 1, '4723');
INSERT INTO `sys_dept` VALUES ('107', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.694000', '0,107', NULL, NULL, '区消防救援大队', 0, '0', NULL, 0, 1, '4774');
INSERT INTO `sys_dept` VALUES ('108', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.694000', '0,108', NULL, NULL, '国投公司', 0, '0', NULL, 0, 1, '4790');
INSERT INTO `sys_dept` VALUES ('109', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.694000', '0,1,109', NULL, NULL, '马山街道', 1, '1', NULL, 0, 2, '4784');
INSERT INTO `sys_dept` VALUES ('112', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.695000', '0,1,112', NULL, NULL, '雪浪街道', 2, '1', NULL, 0, 2, '4785');
INSERT INTO `sys_dept` VALUES ('113', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.695000', '0,1,113', NULL, NULL, '蠡园街道', 3, '1', NULL, 0, 2, '5876');
INSERT INTO `sys_dept` VALUES ('114', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.695000', '0,1,114', NULL, NULL, '胡埭镇', 4, '1', NULL, 0, 2, '4787');
INSERT INTO `sys_dept` VALUES ('115', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.695000', '0,1,115', NULL, NULL, '河埒街道', 5, '1', NULL, 0, 2, '4788');
INSERT INTO `sys_dept` VALUES ('116', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.696000', '0,1,116', NULL, NULL, '荣巷街道', 6, '1', NULL, 0, 2, '4789');
INSERT INTO `sys_dept` VALUES ('117', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.697000', '0,1,117', NULL, NULL, '蠡湖街道', 7, '1', NULL, 0, 2, '4791');
INSERT INTO `sys_dept` VALUES ('118', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.697000', '0,118', NULL, NULL, '滨湖交警大队', 0, '0', NULL, 0, 1, '4773');
INSERT INTO `sys_dept` VALUES ('119', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.698000', '0,119', NULL, NULL, '区宣传部', 0, '0', NULL, 0, 1, '4328');
INSERT INTO `sys_dept` VALUES ('200', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.698000', '0,1,115,200', NULL, NULL, '产山社区', 1, '115', NULL, 0, 2, '6731');
INSERT INTO `sys_dept` VALUES ('201', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.699000', '0,1,115,201', NULL, NULL, '惠河社区', 2, '115', NULL, 0, 2, '6722');
INSERT INTO `sys_dept` VALUES ('202', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.699000', '0,1,115,202', NULL, NULL, '河埒社区', 3, '115', NULL, 0, 2, '6728');
INSERT INTO `sys_dept` VALUES ('203', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.699000', '0,1,115,203', NULL, NULL, '溪南社区', 4, '115', NULL, 0, 2, '6726');
INSERT INTO `sys_dept` VALUES ('204', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.700000', '0,1,115,204', NULL, NULL, '水秀社区', 5, '115', NULL, 0, 2, '6729');
INSERT INTO `sys_dept` VALUES ('205', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.700000', '0,1,115,205', NULL, NULL, '孙蒋社区', 6, '115', NULL, 0, 2, '6723');
INSERT INTO `sys_dept` VALUES ('206', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.700000', '0,1,115,206', NULL, NULL, '蠡桥社区', 7, '115', NULL, 0, 2, '6725');
INSERT INTO `sys_dept` VALUES ('207', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.700000', '0,1,115,207', NULL, NULL, '协民社区', 8, '115', NULL, 0, 2, '6724');
INSERT INTO `sys_dept` VALUES ('208', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.701000', '0,1,115,208', NULL, NULL, '稻香社区', 9, '115', NULL, 0, 2, '6730');
INSERT INTO `sys_dept` VALUES ('209', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.701000', '0,1,115,209', NULL, NULL, '北桥社区', 10, '115', NULL, 0, 2, '6727');
INSERT INTO `sys_dept` VALUES ('210', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.701000', '0,1,116,210', NULL, NULL, '荣巷社区', 1, '116', NULL, 0, 2, '5482');
INSERT INTO `sys_dept` VALUES ('211', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.743000', '0,1,116,211', NULL, NULL, '太康社区', 2, '116', NULL, 0, 2, '5479');
INSERT INTO `sys_dept` VALUES ('212', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.744000', '0,1,116,212', NULL, NULL, '梅园社区', 3, '116', NULL, 0, 2, '5483');
INSERT INTO `sys_dept` VALUES ('213', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.744000', '0,1,116,213', NULL, NULL, '青龙山社区', 4, '116', NULL, 0, 2, '5484');
INSERT INTO `sys_dept` VALUES ('214', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.744000', '0,1,116,214', NULL, NULL, '勤新社区', 5, '116', NULL, 0, 2, '5485');
INSERT INTO `sys_dept` VALUES ('215', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.745000', '0,1,116,215', NULL, NULL, '桃源社区', 6, '116', NULL, 0, 2, '5480');
INSERT INTO `sys_dept` VALUES ('216', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.745000', '0,1,116,216', NULL, NULL, '龙山社区', 7, '116', NULL, 0, 2, '5481');
INSERT INTO `sys_dept` VALUES ('217', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.745000', '0,1,116,217', NULL, NULL, '梁溪社区', 8, '116', NULL, 0, 2, '5486');
INSERT INTO `sys_dept` VALUES ('218', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.745000', '0,1,116,218', NULL, NULL, '新峰社区', 9, '116', NULL, 0, 2, '5487');
INSERT INTO `sys_dept` VALUES ('219', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.745000', '0,1,113,219', NULL, NULL, '隐秀苑社区', 1, '113', NULL, 0, 2, '5883');
INSERT INTO `sys_dept` VALUES ('220', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.746000', '0,1,113,220', NULL, NULL, '环湖社区', 2, '113', NULL, 0, 2, '5882');
INSERT INTO `sys_dept` VALUES ('221', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.746000', '0,1,113,221', NULL, NULL, '湖滨苑社区', 3, '113', NULL, 0, 2, '5881');
INSERT INTO `sys_dept` VALUES ('222', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.746000', '0,1,113,222', NULL, NULL, '大箕山社区', 4, '113', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('223', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.746000', '0,1,113,223', NULL, NULL, '湖景社区', 5, '113', NULL, 0, 2, '5880');
INSERT INTO `sys_dept` VALUES ('224', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.747000', '0,1,113,224', NULL, NULL, '渔港社区', 6, '113', NULL, 0, 2, '5879');
INSERT INTO `sys_dept` VALUES ('225', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.747000', '0,1,113,225', NULL, NULL, '西园社区', 7, '113', NULL, 0, 2, '5884');
INSERT INTO `sys_dept` VALUES ('226', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.747000', '0,1,113,226', NULL, NULL, '湖山社区', 8, '113', NULL, 0, 2, '5878');
INSERT INTO `sys_dept` VALUES ('227', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.747000', '0,1,117,227', NULL, NULL, '震泽社区', 1, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('228', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.748000', '0,1,117,228', NULL, NULL, '山明社区', 2, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('229', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.748000', '0,1,117,229', NULL, NULL, '景丽东苑社区', 3, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('230', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.748000', '0,1,117,230', NULL, NULL, '蠡湖社区', 4, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('231', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.748000', '0,1,117,231', NULL, NULL, '蠡园社区', 5, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('232', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.749000', '0,1,117,232', NULL, NULL, '双虹社区', 6, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('233', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.749000', '0,1,117,233', NULL, NULL, '夏家边社区', 7, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('234', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.749000', '0,1,117,234', NULL, NULL, '中南社区', 8, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('235', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.749000', '0,1,117,235', NULL, NULL, '蠡溪社区', 9, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('236', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.749000', '0,1,117,236', NULL, NULL, '美湖社区', 10, '117', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('237', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.749000', '0,1,112,237', NULL, NULL, '长广社区', 1, '112', NULL, 0, 2, '7049');
INSERT INTO `sys_dept` VALUES ('238', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.750000', '0,1,112,238', NULL, NULL, '石塘社区', 2, '112', NULL, 0, 2, '7050');
INSERT INTO `sys_dept` VALUES ('239', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.750000', '0,1,112,239', NULL, NULL, '望溪社区', 3, '112', NULL, 0, 2, '7047');
INSERT INTO `sys_dept` VALUES ('240', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.750000', '0,1,112,240', NULL, NULL, '许舍社区', 4, '112', NULL, 0, 2, '7061');
INSERT INTO `sys_dept` VALUES ('241', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.770000', '0,1,112,241', NULL, NULL, '葛埭社区', 5, '112', NULL, 0, 2, '7046');
INSERT INTO `sys_dept` VALUES ('242', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.771000', '0,1,112,242', NULL, NULL, '板桥社区', 6, '112', NULL, 0, 2, '7048');
INSERT INTO `sys_dept` VALUES ('243', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.771000', '0,1,112,243', NULL, NULL, '雪浪社区', 7, '112', NULL, 0, 2, '7051');
INSERT INTO `sys_dept` VALUES ('244', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.771000', '0,1,112,244', NULL, NULL, '南苑社区', 8, '112', NULL, 0, 2, '7063');
INSERT INTO `sys_dept` VALUES ('245', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.771000', '0,1,112,245', NULL, NULL, '大浮社区', 9, '112', NULL, 0, 2, '7060');
INSERT INTO `sys_dept` VALUES ('246', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.771000', '0,1,112,246', NULL, NULL, '军嶂社区', 10, '112', NULL, 0, 2, '7056');
INSERT INTO `sys_dept` VALUES ('247', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.772000', '0,1,112,247', NULL, NULL, '西林社区', 11, '112', NULL, 0, 2, '7062');
INSERT INTO `sys_dept` VALUES ('248', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.772000', '0,1,112,248', NULL, NULL, '吴塘社区', 12, '112', NULL, 0, 2, '7053');
INSERT INTO `sys_dept` VALUES ('249', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.772000', '0,1,112,249', NULL, NULL, '滨湖社区', 13, '112', NULL, 0, 2, '7052');
INSERT INTO `sys_dept` VALUES ('250', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.772000', '0,1,112,250', NULL, NULL, '裕新社区', 14, '112', NULL, 0, 2, '7057');
INSERT INTO `sys_dept` VALUES ('251', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.772000', '0,1,112,251', NULL, NULL, '南泉社区', 15, '112', NULL, 0, 2, '7059');
INSERT INTO `sys_dept` VALUES ('252', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.773000', '0,1,112,252', NULL, NULL, '南湖社区', 16, '112', NULL, 0, 2, '7055');
INSERT INTO `sys_dept` VALUES ('253', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.773000', '0,1,112,253', NULL, NULL, '贡湖社区', 17, '112', NULL, 0, 2, '7054');
INSERT INTO `sys_dept` VALUES ('254', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.773000', '0,1,112,254', NULL, NULL, '壬港社区', 18, '112', NULL, 0, 2, '7058');
INSERT INTO `sys_dept` VALUES ('255', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.773000', '0,1,112,255', NULL, NULL, '融创城社区', 19, '112', NULL, 0, 2, '7064');
INSERT INTO `sys_dept` VALUES ('256', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.773000', '0,1,112,256', NULL, NULL, '雪浪科教园社区', 20, '112', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('257', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.773000', '0,1,109,257', NULL, NULL, '峰影社区', 1, '109', NULL, 0, 2, '5861');
INSERT INTO `sys_dept` VALUES ('258', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.774000', '0,1,109,258', NULL, NULL, '乐山社区', 2, '109', NULL, 0, 2, '5862');
INSERT INTO `sys_dept` VALUES ('259', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.774000', '0,1,109,259', NULL, NULL, '迎晖社区', 3, '109', NULL, 0, 2, '5863');
INSERT INTO `sys_dept` VALUES ('260', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.774000', '0,1,109,260', NULL, NULL, '古竹社区', 4, '109', NULL, 0, 2, '5854');
INSERT INTO `sys_dept` VALUES ('261', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.774000', '0,1,109,261', NULL, NULL, '群丰社区', 5, '109', NULL, 0, 2, '5899');
INSERT INTO `sys_dept` VALUES ('262', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.774000', '0,1,109,262', NULL, NULL, '嶂青社区', 6, '109', NULL, 0, 2, '5855');
INSERT INTO `sys_dept` VALUES ('263', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.775000', '0,1,109,263', NULL, NULL, '西村社区', 7, '109', NULL, 0, 2, '5856');
INSERT INTO `sys_dept` VALUES ('264', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.775000', '0,1,109,264', NULL, NULL, '和平社区', 8, '109', NULL, 0, 2, '5857');
INSERT INTO `sys_dept` VALUES ('265', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.775000', '0,1,109,265', NULL, NULL, '耿湾社区', 9, '109', NULL, 0, 2, '5859');
INSERT INTO `sys_dept` VALUES ('266', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.775000', '0,1,109,266', NULL, NULL, '万丰社区', 10, '109', NULL, 0, 2, '5858');
INSERT INTO `sys_dept` VALUES ('267', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.775000', '0,1,109,267', NULL, NULL, '阖闾社区', 11, '109', NULL, 0, 2, '5864');
INSERT INTO `sys_dept` VALUES ('268', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.775000', '0,1,109,268', NULL, NULL, '栖云社区', 12, '109', NULL, 0, 2, '5860');
INSERT INTO `sys_dept` VALUES ('269', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.776000', '0,1,114,269', NULL, NULL, '花汇苑社区', 1, '114', NULL, 0, 2, '11905');
INSERT INTO `sys_dept` VALUES ('270', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.776000', '0,1,114,270', NULL, NULL, '富润社区', 2, '114', NULL, 0, 2, '11907');
INSERT INTO `sys_dept` VALUES ('271', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.795000', '0,1,114,271', NULL, NULL, '立人社区', 3, '114', NULL, 0, 2, '11904');
INSERT INTO `sys_dept` VALUES ('272', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.795000', '0,1,114,272', NULL, NULL, '胡埭村', 4, '114', NULL, 0, 2, '11908');
INSERT INTO `sys_dept` VALUES ('273', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.795000', '0,1,114,273', NULL, NULL, '鸿翔村', 5, '114', NULL, 0, 2, '11909');
INSERT INTO `sys_dept` VALUES ('274', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.795000', '0,1,114,274', NULL, NULL, '张舍村', 6, '114', NULL, 0, 2, '11906');
INSERT INTO `sys_dept` VALUES ('275', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.796000', '0,1,114,275', NULL, NULL, '龙延村', 7, '114', NULL, 0, 2, '11910');
INSERT INTO `sys_dept` VALUES ('276', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.796000', '0,1,114,276', NULL, NULL, '富安村', 8, '114', NULL, 0, 2, '11911');
INSERT INTO `sys_dept` VALUES ('277', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.796000', '0,1,114,277', NULL, NULL, '夏渎村', 9, '114', NULL, 0, 2, '11913');
INSERT INTO `sys_dept` VALUES ('278', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.796000', '0,1,114,278', NULL, NULL, '马鞍村', 10, '114', NULL, 0, 2, '11912');
INSERT INTO `sys_dept` VALUES ('279', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.796000', '0,1,114,279', NULL, NULL, '胡埭工业园社区', 11, '114', NULL, 0, 2, NULL);
INSERT INTO `sys_dept` VALUES ('93', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.796000', '0,93', NULL, NULL, '区教育局', 0, '0', NULL, 0, 1, '4380');
INSERT INTO `sys_dept` VALUES ('94', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.797000', '0,94', NULL, NULL, '区工业和信息化局', 0, '0', NULL, 0, 1, '4403');
INSERT INTO `sys_dept` VALUES ('95', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.797000', '0,95', NULL, NULL, '区住房城乡建设局', 0, '0', NULL, 0, 1, '4459');
INSERT INTO `sys_dept` VALUES ('96', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.797000', '0,96', NULL, NULL, '区城管局', 0, '0', NULL, 0, 1, '4480');
INSERT INTO `sys_dept` VALUES ('97', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.797000', '0,97', NULL, NULL, '区交通运输局', 0, '0', NULL, 0, 1, '5498');
INSERT INTO `sys_dept` VALUES ('98', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.797000', '0,98', NULL, NULL, '区水利局', 0, '0', NULL, 0, 1, '4532');
INSERT INTO `sys_dept` VALUES ('99', 'wmcsadmin', '2023-08-26 15:27:28', 0, NULL, 'wmcsadmin', '2023-12-28 15:39:38.798000', '0,99', NULL, NULL, '区农业农村局', 0, '0', NULL, 0, 1, '4546');


-- ----------------------------
-- sys_dict_type 字典类型表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
    `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型',
    `status` int(11) NULL DEFAULT 0 COMMENT '字典类型状态：0启用；1不启用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('0e8e7b3319c1c05b916ddf48144e55b7', 'wmcsadmin', '2024-01-11 18:56:31', 0, '', NULL, NULL, '数据中心-志愿活动类型', 'data_volunteer_activity_type', 0);
INSERT INTO `sys_dict_type` VALUES ('132362296054775808', 'wmcsadmin', '2023-07-17 17:38:31', 0, '', NULL, NULL, '事件来源', 'event_source', 0);
INSERT INTO `sys_dict_type` VALUES ('132364022178643968', 'wmcsadmin', '2023-07-17 17:45:22', 0, '', NULL, NULL, '事件等级', 'event_level', 0);
INSERT INTO `sys_dict_type` VALUES ('137380572791046144', 'wmcsadmin', '2023-07-31 13:59:21', 0, '', NULL, NULL, '数据中心-文明单位类型', 'data_civilization_company_type', 0);
INSERT INTO `sys_dict_type` VALUES ('151834328899256320', 'wmcsadmin', '2023-09-09 11:13:25', 0, '', NULL, NULL, '数据中心-文明村镇类型', 'data_civilization_town_type', 0);
INSERT INTO `sys_dict_type` VALUES ('151891062095347712', 'wmcsadmin', '2023-09-09 14:58:51', 0, '', NULL, NULL, '数据中心-先进典型任务推荐类型', 'data_recommend_type', 0);
INSERT INTO `sys_dict_type` VALUES ('151894379756257280', 'wmcsadmin', '2023-09-09 15:12:02', 0, '', 'wmcsadmin', '2023-09-09 15:27:34.915000', '数据中心-先进典型人物-类型', 'data_civilization_advanced_type', 0);
INSERT INTO `sys_dict_type` VALUES ('151900427875516416', 'wmcsadmin', '2023-09-09 15:36:04', 0, '', NULL, NULL, '数据中心-文明评选层级', 'data_civilization_select_level', 0);
INSERT INTO `sys_dict_type` VALUES ('151925492394491904', 'wmcsadmin', '2023-09-09 17:15:40', 0, '', NULL, NULL, '数据中心-文明校园类型', 'data_civilization_school_type', 0);
INSERT INTO `sys_dict_type` VALUES ('151953673319088128', 'wmcsadmin', '2023-09-09 19:07:39', 0, '', NULL, NULL, '数据中心-文明校园属性', 'data_civilization_school_attribute', 0);
INSERT INTO `sys_dict_type` VALUES ('153062066184257536', 'wmcsadmin', '2023-09-12 20:32:00', 0, '', NULL, NULL, '工单中心-阈值管理AI告警类型', 'work_alarm_type', 0);
INSERT INTO `sys_dict_type` VALUES ('a1b4a5d2aa1fc33c4e49911df12a0b46', 'wmcsadmin', '2023-11-16 11:09:16', 0, '', NULL, NULL, '巡查任务状态类型', 'work_check_task_status', 0);
INSERT INTO `sys_dict_type` VALUES ('e58b5c8114f41850aaae4559ef8c80a4', 'wmcsadmin', '2024-01-11 18:55:29', 0, '', NULL, NULL, '数据中心-志愿团队类型', 'data_volunteer_team_type', 0);
INSERT INTO `sys_dict_type` VALUES ('f679529911fb412c01b5f16cdd9f4c4d', 'wmcsadmin', '2024-01-11 18:51:09', 0, '', NULL, NULL, '数据中心-督察员类型', 'data_inspector_type', 0);


-- ----------------------------
-- sys_dict_data 字典值表
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
    `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人员id',
    `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
    `del_flag` int(11) NULL DEFAULT 0 COMMENT '逻辑删除；0-正常，1-已删除',
    `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
    `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人员id',
    `update_time` datetime(6) NULL DEFAULT NULL COMMENT '修改时间',
    `default_flag` int(11) NULL DEFAULT 0 COMMENT '是否默认：0否；1是',
    `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典标签',
    `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典类型',
    `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
    `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
    `status` int(11) NULL DEFAULT 0 COMMENT '字典数据状态：0启用；1不启用',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

INSERT INTO `sys_dict_data` VALUES ('09b1416d3d847f2a11e396450e9a0aef', 'wmcsadmin', '2024-01-11 18:56:55', 0, NULL, NULL, NULL, 0, '公益', 'data_volunteer_activity_type', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('137380701296132096', '1', '2023-07-31 13:59:52', 0, NULL, NULL, NULL, 0, '其它单位', 'data_civilization_company_type', '0', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151834406959448064', '1', '2023-09-09 11:13:44', 0, NULL, NULL, NULL, 0, '文明乡镇', 'data_civilization_town_type', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151834406959448065', '1', '2023-09-09 11:13:44', 0, NULL, NULL, NULL, 0, '文明村', 'data_civilization_town_type', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('151836086757228544', '1', '2023-09-09 11:20:24', 0, NULL, NULL, NULL, 0, '党政机关', 'data_civilization_company_type', '1', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('151876754707841024', '1', '2023-09-09 14:02:00', 0, NULL, NULL, NULL, 0, '事业单位', 'data_civilization_company_type', '2', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('151878355669483520', '1', '2023-09-09 14:08:22', 0, NULL, NULL, NULL, 0, '政府机关', 'data_civilization_company_type', '3', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('151878688399425536', '1', '2023-09-09 14:09:41', 0, NULL, NULL, NULL, 0, '国有企业', 'data_civilization_company_type', '4', 5, 0);
INSERT INTO `sys_dict_data` VALUES ('151894222830567424', '1', '2023-09-09 15:11:25', 0, NULL, NULL, NULL, 0, '新时代好少年', 'data_recommend_type', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151894222830567425', '1', '2023-09-09 15:11:25', 0, NULL, NULL, NULL, 0, '身边好人', 'data_recommend_type', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('151894222830567426', '1', '2023-09-09 15:11:25', 0, NULL, NULL, NULL, 0, '最美人物', 'data_recommend_type', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('151894222830567427', '1', '2023-09-09 15:11:25', 0, NULL, NULL, NULL, 0, '岗位学雷锋标兵', 'data_recommend_type', '4', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803932180480', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '助人为乐', 'data_civilization_advanced_type', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803932180481', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '见义勇为', 'data_civilization_advanced_type', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803932180482', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '诚实守信', 'data_civilization_advanced_type', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803932180483', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '孝老爱亲', 'data_civilization_advanced_type', '4', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803932180484', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '敬业奉献', 'data_civilization_advanced_type', '5', 5, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803932180485', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '善行义举榜', 'data_civilization_advanced_type', '6', 6, 0);
INSERT INTO `sys_dict_data` VALUES ('151895803936374784', '1', '2023-09-09 15:17:42', 0, NULL, NULL, NULL, 0, '抗疫典型', 'data_civilization_advanced_type', '7', 7, 0);
INSERT INTO `sys_dict_data` VALUES ('151900510704631808', '1', '2023-09-09 15:36:24', 0, NULL, NULL, NULL, 0, '国家级', 'data_civilization_select_level', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151900510704631809', '1', '2023-09-09 15:36:24', 0, NULL, NULL, NULL, 0, '省级', 'data_civilization_select_level', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('151900510704631810', '1', '2023-09-09 15:36:24', 0, NULL, NULL, NULL, 0, '市级', 'data_civilization_select_level', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('151936644084137984', '1', '2023-09-09 17:59:59', 0, NULL, NULL, NULL, 0, '非公有制经济组织', 'data_civilization_company_type', '5', 6, 0);
INSERT INTO `sys_dict_data` VALUES ('151953491655393280', '1', '2023-09-09 19:06:56', 0, NULL, NULL, NULL, 0, '小学', 'data_civilization_school_type', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151953491655393281', '1', '2023-09-09 19:06:56', 0, NULL, NULL, NULL, 0, '普通中学', 'data_civilization_school_type', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('151953491655393282', '1', '2023-09-09 19:06:56', 0, NULL, NULL, NULL, 0, '高校', 'data_civilization_school_type', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('151953491655393283', '1', '2023-09-09 19:06:56', 0, NULL, NULL, NULL, 0, '中职学校', 'data_civilization_school_type', '4', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('151953491655393284', '1', '2023-09-09 19:06:56', 0, NULL, NULL, NULL, 0, '九年一贯制', 'data_civilization_school_type', '5', 5, 0);
INSERT INTO `sys_dict_data` VALUES ('151953831213662208', '1', '2023-09-09 19:08:17', 0, NULL, NULL, NULL, 0, '公办', 'data_civilization_school_attribute', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('151953831213662209', '1', '2023-09-09 19:08:17', 0, NULL, NULL, NULL, 0, '民办', 'data_civilization_school_attribute', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('153062278680281088', '1', '2023-09-12 20:32:51', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.031000', 0, '井盖丢失破损', 'work_alarm_type', '4010', 5, 0);
INSERT INTO `sys_dict_data` VALUES ('153062278684475392', '1', '2023-09-12 20:32:51', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.031000', 0, '张贴小广告', 'work_alarm_type', '4006', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('153062278684475393', '1', '2023-09-12 20:32:51', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.031000', 0, '违规晾晒', 'work_alarm_type', '4004', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('153062278684475394', '1', '2023-09-12 20:32:51', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.030000', 0, '堆物', 'work_alarm_type', '4003', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('153062278684475395', '1', '2023-09-12 20:32:51', 0, NULL, 'wmcsadmin', '2024-01-10 12:07:19.177000', 0, '垃圾桶满溢', 'work_alarm_type', '4002', 1, 1);
INSERT INTO `sys_dict_data` VALUES ('153062278684475396', '1', '2023-09-12 20:32:51', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.032000', 0, '非机动车违停', 'work_alarm_type', '5002', 13, 0);
INSERT INTO `sys_dict_data` VALUES ('166019078098518016', NULL, '2023-10-18 14:38:32', 0, NULL, NULL, NULL, 0, '自主巡查', 'event_source', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('166019078098518017', NULL, '2023-10-18 14:38:32', 0, NULL, NULL, NULL, 0, '智能AI分析', 'event_source', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('166019078098518018', NULL, '2023-10-18 14:38:32', 0, NULL, NULL, NULL, 0, '专班巡查', 'event_source', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('166019078098518019', NULL, '2023-10-18 14:38:32', 0, NULL, NULL, NULL, 0, '市级下发', 'event_source', '4', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('167842983280902144', NULL, '2023-10-23 15:26:05', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.032000', 0, '机动车违停', 'work_alarm_type', '5001', 12, 0);
INSERT INTO `sys_dict_data` VALUES ('28a7847bd9c564e5f2172d473c827981', 'wmcsadmin', '2023-11-16 11:10:11', 0, NULL, NULL, NULL, 0, '待巡查', 'work_check_task_status', '0', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('36c069885c9879528d8eef4917980579', 'wmcsadmin', '2024-01-11 18:52:47', 0, NULL, NULL, NULL, 0, '第三方巡查', 'data_inspector_type', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('3b727c0a659cab87e923231995e60fca', 'wmcsadmin', '2024-01-11 18:52:47', 0, NULL, NULL, NULL, 0, '专班巡查', 'data_inspector_type', '2', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('3ce8e22f0abedbe90264ec948eec40ce', 'wmcsadmin', '2023-11-16 11:10:11', 0, NULL, NULL, NULL, 0, '合格', 'work_check_task_status', '1', 2, 0);
INSERT INTO `sys_dict_data` VALUES ('68229d685d2873e0811c8132b78a7c63', 'wmcsadmin', '2023-11-27 19:07:42', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.032000', 0, '裸土识别', 'work_alarm_type', '4030', 11, 0);
INSERT INTO `sys_dict_data` VALUES ('7c83243e32ba25270993d741637e0595', 'wmcsadmin', '2023-11-16 11:10:11', 0, NULL, NULL, NULL, 0, '不合格处理中', 'work_check_task_status', '3', 3, 0);
INSERT INTO `sys_dict_data` VALUES ('7dae6a2b6a23d0eb0c0fcbef6ad6182a', 'wmcsadmin', '2024-01-10 11:53:23', 0, NULL, NULL, NULL, 0, '积水', 'work_alarm_type', '4005', 16, 0);
INSERT INTO `sys_dict_data` VALUES ('81670257049c9f98b9c7d9a8784cf24f', 'wmcsadmin', '2024-01-10 11:51:10', 0, NULL, NULL, NULL, 0, '悬挂横幅', 'work_alarm_type', '4022', 10, 0);
INSERT INTO `sys_dict_data` VALUES ('85eec36cdde06a828d5f6a1ff1b16364', 'wmcsadmin', '2024-01-11 18:56:04', 0, NULL, NULL, NULL, 0, '社会招募', 'data_volunteer_team_type', '1', 1, 0);
INSERT INTO `sys_dict_data` VALUES ('8a2de861b7c59d3e520a91e027d17b2d', 'wmcsadmin', '2023-11-16 11:10:11', 0, NULL, NULL, NULL, 0, '不合格待处理', 'work_check_task_status', '4', 4, 0);
INSERT INTO `sys_dict_data` VALUES ('8e47e25b945948e0038eec9c9c1f8d85', 'wmcsadmin', '2024-01-10 11:51:10', 0, NULL, NULL, NULL, 0, '小动物入侵', 'work_alarm_type', '4015', 6, 0);
INSERT INTO `sys_dict_data` VALUES ('8eb3a72d927a93d3ac80d4e37bccfff1', 'wmcsadmin', '2024-01-10 11:51:10', 0, NULL, NULL, NULL, 0, '非机动车逆行', 'work_alarm_type', '5005', 15, 0);
INSERT INTO `sys_dict_data` VALUES ('a95806baeeb465b558392ffaed0ad0a8', 'wmcsadmin', '2024-01-10 11:51:10', 0, NULL, NULL, NULL, 0, '打包垃圾', 'work_alarm_type', '4017', 7, 0);
INSERT INTO `sys_dict_data` VALUES ('ae1f93b5c86ef3ed0317172bcd4388e9', 'wmcsadmin', '2023-12-27 17:52:38', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.031000', 0, '暴露垃圾', 'work_alarm_type', '4018', 8, 0);
INSERT INTO `sys_dict_data` VALUES ('b029e398a82ec04f7b7b7dd1c8ca2e32', 'wmcsadmin', '2024-01-10 11:51:10', 0, NULL, NULL, NULL, 0, '机动车逆行', 'work_alarm_type', '5004', 14, 0);
INSERT INTO `sys_dict_data` VALUES ('b3483b3991b58277f3817653cfa74bc1', 'wmcsadmin', '2023-11-27 19:07:42', 0, NULL, 'wmcsadmin', '2024-01-10 11:51:10.032000', 0, '私搭乱建', 'work_alarm_type', '4020', 9, 0);
INSERT INTO `sys_dict_data` VALUES ('c898b043fd3590e5b21ac20537485c29', 'wmcsadmin', '2024-01-10 11:54:31', 0, NULL, NULL, NULL, 0, '压实线行驶', 'work_alarm_type', '5003', 18, 0);
INSERT INTO `sys_dict_data` VALUES ('cb8c4b1a613682719631eed6c68ac177', 'wmcsadmin', '2024-01-10 11:53:23', 0, NULL, NULL, NULL, 0, '施工废料', 'work_alarm_type', '4012', 17, 0);
INSERT INTO `sys_dict_data` VALUES ('e689b4c5da3c98d95d5da11d789e496e', 'wmcsadmin', '2024-01-11 18:52:47', 0, NULL, NULL, NULL, 0, '专职督察', 'data_inspector_type', '1', 1, 0);
