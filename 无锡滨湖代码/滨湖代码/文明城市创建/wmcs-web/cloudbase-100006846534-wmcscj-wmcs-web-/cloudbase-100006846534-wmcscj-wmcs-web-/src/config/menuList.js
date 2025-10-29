// 本地菜单配置
function MenuId() {
    this.id = 1;
    this.getMenuId = () => { return this.id++ }
}
let menu = new MenuId()
export const menuList = [
    {
        id: menu.getMenuId(),
        name: '首页',
        link: '/home/statis/total/index',
        icon: 'fa fa-indent'
    },
    {
        id: menu.getMenuId(),
        name: '数据中心',
        link: '/home/data',
        icon: 'fa fa-window-restore',
        children: [
            {
                id: menu.getMenuId(),
                name: '点位类型',
                link: '/home/data/checkPointsType/list',
                icon: 'fa fa-cubes'
            },
            {
                id: menu.getMenuId(),
                name: '督察点位',
                link: '/home/data/checkPoints/list',
                icon: 'fa fa-flag-checkered'
            },
            {
                id: menu.getMenuId(),
                name: '督察人员',
                link: '/home/data/checkUser/list',
                icon: 'fa fa-user-circle'
            },
            {
                id: menu.getMenuId(),
                name: '视频设备',
                link: '/home/data/device/list',
                icon: 'fa fa-video-camera'
            },
            {
                id: menu.getMenuId(),
                name: '文明村镇',
                link: '/home/data/civilizationTown/list',
                icon: 'fa fa-codepen'
            },
            {
                id: menu.getMenuId(),
                name: '文明单位',
                link: '/home/data/civilizationCompany/list',
                icon: 'fa fa-university'
            },
            {
                id: menu.getMenuId(),
                name: '文明校园',
                link: '/home/data/civilizationSchool/list',
                icon: 'fa fa-mortar-board'
            },
            {
                id: menu.getMenuId(),
                name: '文明家庭',
                link: '/home/data/civilizationFamily/list',
                icon: 'fa fa-facebook-official'
            },
            {
                id: menu.getMenuId(),
                name: '典型先进人物',
                link: '/home/data/civilizationAdvanced/list',
                icon: 'fa fa-shield'
            },
            {
                id: menu.getMenuId(),
                name: '志愿人员',
                link: '/home/data/volunteer/list',
                icon: 'fa fa-user-plus'
            },
            {
                id: menu.getMenuId(),
                name: '志愿团队',
                link: '/home/data/volunteerTeam/list',
                icon: 'fa fa-users'
            },
            {
                id: menu.getMenuId(),
                name: '志愿服务活动',
                link: '/home/data/volunteerActivity/list',
                icon: 'fa fa-grav'
            }
        ]
    },
    {
        id: menu.getMenuId(),
        name: '事件统计',
        link: '/home/analysis',
        icon: 'fa fa-book',
        children: [
            {
                id: menu.getMenuId(),
                name: '事件总览',
                link: '/home/analysis/all/list',
                icon: 'fa fa-folder'
            },
            {
                id: menu.getMenuId(),
                name: '工单趋势',
                link: '/home/analysis/trend/list',
                icon: 'fa fa-line-chart'
            },
            {
                id: menu.getMenuId(),
                name: '统计分析',
                link: '/home/analysis/statistic/list',
                icon: 'fa fa-bar-chart-o'
            }
        ]
    },
    {
        id: menu.getMenuId(),
        name: '协同处置',
        link: '/home/disposal',
        icon: 'fa fa-hand-o-right',
        children: [
            {
                id: menu.getMenuId(),
                name: '我的上报',
                link: '/home/disposal/eventReport/list',
                icon: 'fa fa-calendar-plus-o'
            },
            {
                id: menu.getMenuId(),
                name: '待受理',
                link: '/home/disposal/allocate/list',
                icon: 'fa  fa-calendar-o'
            },
            {
                id: menu.getMenuId(),
                name: '已受理',
                link: '/home/disposal/allocated/list',
                icon: 'fa  fa-calendar-check-o'
            },
            {
                id: menu.getMenuId(),
                name: '待处置',
                link: '/home/disposal/handle/list',
                icon: 'fa fa-calendar-o'
            },
            {
                id: menu.getMenuId(),
                name: '已处置',
                link: '/home/disposal/handled/list',
                icon: 'fa  fa-calendar-check-o'
            },
            {
                id: menu.getMenuId(),
                name: '待核查',
                link: '/home/disposal/check/list',
                icon: 'fa fa-calendar-o'
            },
            {
                id: menu.getMenuId(),
                name: '已办结',
                link: '/home/disposal/finished/list',
                icon: 'fa fa-calendar-check-o'
            },
            {
                id: menu.getMenuId(),
                name: '延期管理',
                link: '/home/disposal/extension/list',
                icon: 'fa fa-expand'
            },
            {
                id: menu.getMenuId(),
                name: '事件类型',
                link: '/home/disposal/eventType/list',
                icon: 'fa fa-wpforms'
            },
            {
                id: menu.getMenuId(),
                name: '考核周期',
                link: '/home/disposal/assessCycle/list',
                icon: 'fa fa-adjust'
            }
        ]
    },
    {
        id: menu.getMenuId(),
        name: '工单中心',
        link: '/home/event',
        icon: 'fa fa-bookmark',
        children: [
            {
                id: menu.getMenuId(),
                name: '告警信息',
                link: '/home/event/warning/list',
                icon: 'fa fa-warning'
            },
            {
                id: menu.getMenuId(),
                name: '阈值管理',
                link: '/home/event/threshold/list',
                icon: 'fa fa-hourglass-half'
            },
            {
                id: menu.getMenuId(),
                name: '现象工单',
                link: '/home/event/appearance/list',
                icon: 'fa fa-anchor'
            },
            {
                id: menu.getMenuId(),
                name: '工单总览',
                link: '/home/event/record/list',
                icon: 'fa fa-book'
            },
            {
                id: menu.getMenuId(),
                name: '办结审核',
                link: '/home/event/finishConfirm/list',
                icon: 'fa fa-check-square-o'
            }
        ]
    },
    {
        id: menu.getMenuId(),
        name: '指标中心',
        link: '/home/quota',
        icon: 'el-icon-s-home',
        children: [
            {
                id: menu.getMenuId(),
                name: '点位检查项',
                link: '/home/quota/checkItem/list',
                icon: 'fa fa-server'
            },
            {
                id: menu.getMenuId(),
                name: '指标类型',
                link: '/home/quota/indicatorType/list',
                icon: 'fa fa-tumblr-square'
            },
            {
                id: menu.getMenuId(),
                name: '指标管理',
                link: '/home/quota/indicator/list',
                icon: 'fa fa-tumblr-square'
            },
            {
                id: menu.getMenuId(),
                name: '权重配置',
                link: '/home/quota/weight/list',
                icon: 'fa fa-pie-chart'
            }
        ]
    },
    {
        id: menu.getMenuId(),
        name: '考核中心',
        link: '/home/assess',
        icon: 'fa fa-tags',
        children: [
            {
                id: menu.getMenuId(),
                name: '文明城市指数',
                link: '/home/assess/month/list',
                icon: 'fa fa-pencil'
            },
            {
                id: menu.getMenuId(),
                name: '月度评测通报',
                link: '/home/assess/evaluating/list',
                icon: 'fa fa-pencil-square'
            },
            {
                id: menu.getMenuId(),
                name: '年度考核',
                link: '/home/assess/year/list',
                icon: 'fa fa-pencil-square-o'
            }
        ]
    },
    {
        id: menu.getMenuId(),
        name: '系统中心',
        link: '/home/system',
        icon: 'fa fa-cog',
        children: [
            {
                id: menu.getMenuId(),
                name: '街道管理',
                link: '/home/system/region/list',
                icon: 'fa fa-map'
            },
            {
                id: menu.getMenuId(),
                name: '单位管理',
                link: '/home/system/dept/list',
                icon: 'fa fa-compass'
            },
            {
                id: menu.getMenuId(),
                name: '账号管理',
                link: '/home/system/employee/list',
                icon: 'fa fa-user'
            },
            {
                id: menu.getMenuId(),
                name: '账号同步',
                link: '/home/system/unityUser/list',
                icon: 'fa el-icon-refresh'
            }
        ]
    }
]

