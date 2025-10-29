const dataRouter = {
    path: 'data',
    name: 'data',
    component: () => import('@/views/home/layout.vue'),
    children: [
        {
            // 点位类型
            path: 'checkPointsType',
            name: 'data-checkPointsType',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'checkPointsTypeList',
                    component: () => import('@/views/data/checkPointsType/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'checkPointsType-add',
                    component: () => import('@/views/data/checkPointsType/add.vue')
                },
                {
                    path: '_edit',
                    name: 'checkPointsType-edit',
                    component: () => import('@/views/data/checkPointsType/edit.vue')
                },
                {
                    path: '_info',
                    name: 'checkPointsType-info',
                    component: () => import('@/views/data/checkPointsType/info.vue')
                }
            ]
        },
        {
            // 督查点位
            path: 'checkPoints',
            name: 'data-checkPoints',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'checkPointsList',
                    component: () => import('@/views/data/checkPoints/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'checkPoints-add',
                    component: () => import('@/views/data/checkPoints/add.vue')
                },
                {
                    path: '_edit',
                    name: 'checkPoints-edit',
                    component: () => import('@/views/data/checkPoints/edit.vue')
                },
                {
                    path: '_info',
                    name: 'checkPoints-info',
                    component: () => import('@/views/data/checkPoints/info.vue')
                }
            ]
        },
        {
            // 督查人员
            path: 'checkUser',
            name: 'data-checkUser',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'checkUserList',
                    component: () => import('@/views/data/checkUser/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'checkUser-add',
                    component: () => import('@/views/data/checkUser/add.vue')
                },
                {
                    path: '_edit',
                    name: 'checkUser-edit',
                    component: () => import('@/views/data/checkUser/edit.vue')
                },
                {
                    path: '_info',
                    name: 'checkUser-info',
                    component: () => import('@/views/data/checkUser/info.vue')
                }
            ]
        },
        {
            // 督查点位设备
            path: 'checkPointsDevice',
            name: 'data-checkPointsDevice',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'checkPointsDeviceList',
                    component: () => import('@/views/data/checkPointsDevice/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'checkPointsDeviceAdd',
                    component: () => import('@/views/data/checkPointsDevice/add.vue')
                },
                {
                    path: '_edit',
                    name: 'checkPointsDeviceEdit',
                    component: () => import('@/views/data/checkPointsDevice/edit.vue')
                },
                {
                    path: '_info',
                    name: 'checkPointsDeviceInfo',
                    component: () => import('@/views/data/checkPointsDevice/info.vue')
                }
            ]
        },
        {
            // 视频组织
            path: 'deviceTree',
            name: 'data-deviceTree',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'deviceTreeList',
                    component: () => import('@/views/data/deviceTree/list.vue'),
                    meta: { keepAlive: true }
                }
            ]
        },
        {
            // 我的视频组织
            path: 'deviceTreeMy',
            name: 'data-deviceTreeMy',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'deviceTreeMyList',
                    component: () => import('@/views/data/deviceTreeMy/list.vue'),
                    mete: {keepAlive: true}
                }
            ]
        },
        {
            // 视频设备
            path: 'device',
            name: 'data-device',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'deviceList',
                    component: () => import('@/views/data/device/list.vue'),
                    meta: { keepAlive: true }
                }
            ]
        },
        {
            // 文明村镇
            path: 'civilizationTown',
            name: 'data-civilizationTown',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'civilizationTownList',
                    component: () => import('@/views/data/civilizationTown/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'civilizationTown-add',
                    component: () => import('@/views/data/civilizationTown/add.vue')
                },
                {
                    path: '_edit',
                    name: 'civilizationTown-edit',
                    component: () => import('@/views/data/civilizationTown/edit.vue')
                },
                {
                    path: '_info',
                    name: 'civilizationTown-info',
                    component: () => import('@/views/data/civilizationTown/info.vue')
                }
            ]
        },
        {
            // 文明单位
            path: 'civilizationCompany',
            name: 'data-civilizationCompany',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'civilizationCompanyList',
                    component: () => import('@/views/data/civilizationCompany/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'civilizationCompany-add',
                    component: () => import('@/views/data/civilizationCompany/add.vue')
                },
                {
                    path: '_edit',
                    name: 'civilizationCompany-edit',
                    component: () => import('@/views/data/civilizationCompany/edit.vue')
                },
                {
                    path: '_info',
                    name: 'civilizationCompany-info',
                    component: () => import('@/views/data/civilizationCompany/info.vue')
                }
            ]
        },
        {
            // 文明校园
            path: 'civilizationSchool',
            name: 'data-civilizationSchool',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'civilizationSchoolList',
                    component: () => import('@/views/data/civilizationSchool/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'civilizationSchool-add',
                    component: () => import('@/views/data/civilizationSchool/add.vue')
                },
                {
                    path: '_edit',
                    name: 'civilizationSchool-edit',
                    component: () => import('@/views/data/civilizationSchool/edit.vue')
                },
                {
                    path: '_info',
                    name: 'civilizationSchool-info',
                    component: () => import('@/views/data/civilizationSchool/info.vue')
                }
            ]
        },
        {
            // 文明家庭
            path: 'civilizationFamily',
            name: 'data-civilizationFamily',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'civilizationFamilyList',
                    component: () => import('@/views/data/civilizationFamily/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'civilizationFamily-add',
                    component: () => import('@/views/data/civilizationFamily/add.vue')
                },
                {
                    path: '_edit',
                    name: 'civilizationFamily-edit',
                    component: () => import('@/views/data/civilizationFamily/edit.vue')
                },
                {
                    path: '_info',
                    name: 'civilizationFamily-info',
                    component: () => import('@/views/data/civilizationFamily/info.vue')
                }
            ]
        },
        {
            // 典型先进人员
            path: 'civilizationAdvanced',
            name: 'data-civilizationAdvanced',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'civilizationAdvanced',
                    component: () => import('@/views/data/civilizationAdvanced/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'civilizationAdvanced-add',
                    component: () => import('@/views/data/civilizationAdvanced/add.vue')
                },
                {
                    path: '_edit',
                    name: 'civilizationAdvanced-edit',
                    component: () => import('@/views/data/civilizationAdvanced/edit.vue')
                },
                {
                    path: '_info',
                    name: 'civilizationAdvanced-info',
                    component: () => import('@/views/data/civilizationAdvanced/info.vue')
                }
            ]
        },
        {
            // 志愿人员
            path: 'volunteer',
            name: 'data-volunteer',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'volunteerList',
                    component: () => import('@/views/data/volunteer/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'volunteer-add',
                    component: () => import('@/views/data/volunteer/add.vue')
                },
                {
                    path: '_edit',
                    name: 'volunteer-edit',
                    component: () => import('@/views/data/volunteer/edit.vue')
                },
                {
                    path: '_info',
                    name: 'volunteer-info',
                    component: () => import('@/views/data/volunteer/info.vue')
                }
            ]
        },
        {
            // 志愿团队
            path: 'volunteerTeam',
            name: 'data-volunteerTeam',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'volunteerTeamList',
                    component: () => import('@/views/data/volunteerTeam/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'volunteerTeam-add',
                    component: () => import('@/views/data/volunteerTeam/add.vue')
                },
                {
                    path: '_edit',
                    name: 'volunteerTeam-edit',
                    component: () => import('@/views/data/volunteerTeam/edit.vue')
                },
                {
                    path: '_info',
                    name: 'volunteerTeam-info',
                    component: () => import('@/views/data/volunteerTeam/info.vue')
                }
            ]
        },
        {
            // 志愿活动
            path: 'volunteerActivity',
            name: 'data-volunteerActivity',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'volunteerActivityList',
                    component: () => import('@/views/data/volunteerActivity/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'olunteerActivity-add',
                    component: () => import('@/views/data/volunteerActivity/add.vue')
                },
                {
                    path: '_edit',
                    name: 'olunteerActivity-edit',
                    component: () => import('@/views/data/volunteerActivity/edit.vue')
                },
                {
                    path: '_info',
                    name: 'olunteerActivity-info',
                    component: () => import('@/views/data/volunteerActivity/info.vue')
                }
            ]
        },
        {
            // 手环设备
            path: 'equipment',
            name: 'data-equipment',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'equipmentList',
                    component: () => import('@/views/data/equipment/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'equipment-add',
                    component: () => import('@/views/data/equipment/add.vue')
                },
                {
                    path: '_edit',
                    name: 'equipment-edit',
                    component: () => import('@/views/data/equipment/edit.vue')
                }
            ]
        },
        {
            // 知识库
            path: 'knowledge',
            name: 'data-knowledge',
            component: () => import('@/views/home/layout.vue'),
            children: [
                {
                    path: 'list',
                    name: 'knowledgeList',
                    component: () => import('@/views/data/knowledge/list.vue'),
                    meta: { keepAlive: true }
                },
                {
                    path: '_add',
                    name: 'knowledge-add',
                    component: () => import('@/views/data/knowledge/add.vue')
                },
                {
                    path: '_edit',
                    name: 'knowledge-edit',
                    component: () => import('@/views/data/knowledge/edit.vue')
                },
                {
                    path: '_info',
                    name: 'knowledge-info',
                    component: () => import('@/views/data/knowledge/info.vue')
                }
            ]
        }
    ]
}

export default dataRouter
