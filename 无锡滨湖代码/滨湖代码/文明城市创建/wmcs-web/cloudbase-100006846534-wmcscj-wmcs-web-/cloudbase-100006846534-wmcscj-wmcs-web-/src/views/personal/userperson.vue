<template>
    <!-- 个人中心侧边 -->
    <div class="user-personal-sider-wapper">
        <div class="p-block-wapper">
            <div class="info-line">
                <!-- 信息, 默认头像 -->
                <img v-if="personelInfo && personelInfo.user && personelInfo.user.avatar" class="p-avator" :src="personelInfo.user.avatar" alt="" />
                <img v-else class="p-avator" :src="userAvatar" alt="" />
                <!-- <img class="p-avator" :src="personelInfo.avatar" alt="" /> -->
                <div class="con-wapper">
                    <div class="t-line av-name">{{ personelInfo && personelInfo.user && personelInfo.user.nickName || '系统用户' }}</div>
                    <div class="t-line">
                        <span class="av-service">{{ personelInfo && personelInfo.user && personelInfo.user.phonenumber || '-' }}</span>
                    </div>
                </div>
            </div>
        </div>
        <div v-if="personelInfo" class="p-block-wapper">
            <div class="info-line">
                <i class="fa fa-sitemap" />
                <div class="con-wapper">
                    <div class="t-line title">所属部门</div>
                    <div class="t-line">
                        <el-tooltip class="item" effect="dark" placement="top-start">
                            <span class="unline" style="cursor:pointer;">
                                {{ personelInfo.dept ? personelInfo.dept.name || '暂无部门' : '暂无部门' }}
                            </span>
                        </el-tooltip>
                    </div>
                </div>
            </div>
        </div>
        <div class="p-block-wapper">
            <div class="info-line">
                <i class="el-icon-user-solid" />
                <div class="con-wapper">
                    <div class="t-line title">当前登陆账号</div>
                    <div class="t-line">{{ personelInfo && personelInfo.user && personelInfo.user.userName || '' }}</div>
                </div>
            </div>
            <div class="info-line">
                <i class="el-icon-mobile-phone" />
                <div class="con-wapper">
                    <div class="t-line title">联系电话</div>
                    <div class="t-line">{{ personelInfo && personelInfo.user && personelInfo.user.phonenumber || '' }}</div>
                </div>
            </div>
            <div class="info-line">
                <i class="fa fa-address-card" />
                <div class="con-wapper">
                    <div class="t-line title">当前使用角色</div>
                    <div class="t-line margin-bottom-0">
                        <searchRoleM
                            v-if="personelInfo.roles"
                            noLabel
                            useType="form"
                            onlyShow
                            allWidth="100%"
                            disabled
                            :defaultVal="personelInfo.roles"
                        />
                    </div>
                </div>
            </div>
        </div>
        <!-- 登录日志 -->
        <div class="p-block-wapper">
            <div v-if="personelInfo.loginDate" class="info-line">
                <div class="con-wapper">
                    <div class="t-line title">上次登录时间</div>
                    <div class="t-line">{{ personelInfo.loginDate || '' }}</div>
                </div>
            </div>
            <div v-if="personelInfo.user.updateTime" class="info-line">
                <div class="con-wapper">
                    <div class="t-line title">上次修改时间</div>
                    <div class="t-line">{{ personelInfo.user.updateTime || '' }}</div>
                </div>
            </div>
            <div class="info-line">
                <div class="con-wapper">
                    <div class="t-line title">首次开通时间</div>
                    <div class="t-line">{{ personelInfo.user.createTime || '' }}</div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
export default {
     data() {
        return {
            userAvatar: require('@/assets/images/avatar.jpg')
        }
    },
    computed: {
        personelInfo() {
            let info = this.$store.state.userInfo.data
            if (!info) return {}
            return info
        }
    },
    methods: {
        treeToList(tree) {
            let _self = this;
            for (var i in tree) {
                var node = tree[i]
                var list = [] // 结果lsit
                list.push({
                    id: node.id,
                    name: node.name
                })
                if (node.children.length !== 0) {
                    // 遍历树的第一层,只有一个根结点
                    _self.toListDF(node.children, list); // 遍历子树,并加入到list中.
                }
            }
            return list
        },
        toListDF(tree, list) {
            let _self = this;
            for (var i in tree) { // 遍历最上层
                // 将当前树放入list中
                var node = tree[i]
                list.push({
                    id: node.id,
                    name: node.name
                });
                // 如果有子结点,再遍历子结点
                if (node.children.length !== 0) {
                    _self.toListDF(node.children, list) // 递归
                }
            }
        }
    }
}
</script>
<style lang="scss" scoped>
.user-personal-sider-wapper{
    height: 100%;
    width: 260px;
    overflow: auto;
    background: #fff;
    padding: 0 20px;
    display: flex;
    flex-direction: column;
    border-right: 1px solid #eceef5;
    .p-block-wapper{
        padding: 20px 0 0 0;
        border-bottom: 1px dashed #DCDFE6;
        width: 100%;
        display: flex;
        flex-direction: column;
        &:last-child{
            border-bottom: none;
        }
        .info-line{
            width: 100%;
            display: flex;
            flex-direction: row;
            margin-bottom: 10px;
            .p-avator{
                width: 60px;
                height: 60px;
                border-radius: 10px;
            }
            .con-wapper{
                flex:1;
                width: 0;
                display: flex;
                flex-direction: column;
                padding-left: 10px;
                .t-line{
                    width: 100%;
                    margin-bottom: 5px;
                    overflow: hidden;
                    text-overflow:ellipsis;
                    white-space: nowrap;
                    color: #909399;
                    font-size: 14px;
                    &.title{
                        color: #606266;
                    }
                    &.av-name{
                        color: #303133;
                        font-size: 20px;
                        font-weight: bold;
                        margin-bottom: 10px;
                    }
                    .av-service{
                        display: inline-flex;
                        border:1px solid var(--td-brand-color-hover);
                        padding: 3px 5px;
                        border-radius: 5px;
                        color: var(--td-brand-color-hover);
                    }
                }
            }
        }
    }
}
</style>

