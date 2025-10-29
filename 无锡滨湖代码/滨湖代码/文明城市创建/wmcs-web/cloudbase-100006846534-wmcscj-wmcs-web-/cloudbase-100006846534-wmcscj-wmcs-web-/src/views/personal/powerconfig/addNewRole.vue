<template>
    <el-dialog :title="confirmType==='Create' ? '新增角色' : accInfo.name" :visible.sync="show" append-to-body width="1000px" :before-close="cancel" @close="cancel">
        <div v-loading="v_loading" class="dia-bd-hei per-add-new-account">
            <el-form ref="ruleForm" :model="accInfo" :rules="rules" size="small">
                <el-row class="show-flex-r wrap w-100" :gutter="20">
                    <el-col :span="12">
                        <el-form-item label="角色名称:" prop="name" :label-width="formLabelWidth">
                            <el-input v-model="accInfo.name" placeholder="请填写角色名称" autocomplete="off" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="权限字符:" prop="code" :label-width="formLabelWidth">
                            <el-input v-model="accInfo.code" placeholder="请填写权限字符" autocomplete="off" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="排序:" prop="orderNum" :label-width="formLabelWidth">
                            <el-input v-model="accInfo.orderNum" placeholder="请填写排序" autocomplete="off" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="数据范围:" prop="dataScope" :label-width="formLabelWidth">
                            <div style="width:178px">
                                <el-select v-model="accInfo.dataScope" placeholder="请选择" size="mini">
                                    <el-option value="1" label="所有数据权限" />
                                    <el-option value="2" label="自定义数据权限" />
                                    <el-option value="3" label="本部门数据权限" />
                                    <el-option value="4" label="本部门及以下数据权限" />
                                    <el-option value="5" label="仅本人数据权限" />
                                </el-select>
                            </div>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item label="操作权限:" :label-width="formLabelWidth">
                    <div class="power-kanban-wapper">
                        <platformTree
                            v-for="(pla, index) in permsissons"
                            :key="index"
                            :ref="'plat' + index"
                            :pla="pla"
                            :defaultKey="defaultPlaKeys"
                        />
                    </div>
                </el-form-item>
            </el-form>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
            <t-button theme="primary" @click="submitForm('ruleForm')">确定</t-button>
            <!-- <el-button size="mini" @click="cancel">取 消</el-button>
            <el-button size="mini" type="bi" @click="submitForm('ruleForm')">确 定</el-button> -->
        </div>
    </el-dialog>
</template>
<script>
import { getResourceList, addRole, updateRole, getRoleDeatil } from '@/api/personal'
import platformTree from './tree/platformTree.vue'
export default {
    components: { platformTree },
    props: {
        show: {
            type: Boolean,
            default: false
        },
        confirmType: {
            type: String,
            default: 'Create'
        },
        account: {
            type: Object,
            default: () => {
                return {}
            }
        }
    },
    data() {
        return {
            v_loading: false,
            rules: {
                name: [
                    { required: true, message: '请输入角色名称', trigger: 'blur' }
                ],
                dataScope: [{ required: true, message: '请输入角色名称', trigger: 'blur' }]
            },
            permsissons: [], // 平台
            accInfo: {
                id: '',
                name: '',
                code: '',
                orderNum: 10,
                dataScope: '5',
                remark: '',
                sourceIds: [],
                menus: []
            },
            defaultPlaKeys: [],
            formLabelWidth: '130px',
            props: {
                label: 'name',
                children: 'children'
            }
        }
    },
    mounted() {
        let _this = this
        let useP = []
        // 无论新增还是修改,需要获取所有资源列表
        useP.push(getResourceList({platform: 1})) // PC
        useP.push(getResourceList({platform: 2})) // 政务微信
        // 修改需要获取回显的资源
        if (this.confirmType === 'Update') {
            _this.accInfo.id = _this.account.id
            _this.accInfo.name = _this.account.name
            _this.accInfo.code = _this.account.code
            _this.accInfo.orderNum = _this.account.orderNum
            _this.accInfo.dataScope = _this.account.dataScope
            _this.accInfo.remark = _this.account.remark
            useP.push(getRoleDeatil(_this.account.id))
        }
        this.v_loading = true
        Promise.all(useP)
        .then(data => {
            // pc资源
            let pcPla = data[0].data.records
            let appPla = data[1].data.records
            if (pcPla.length >= 0) {
                _this.permsissons.push({
                    name: '系统资源',
                    list: pcPla
                })
            }
            if (appPla.length >= 0) {
                _this.permsissons.push({
                    name: '政务微信',
                    list: appPla
                })
            }
            if (this.confirmType === 'Update') {
                let sourcesIds = data[data.length - 1].data.records
                _this.$nextTick(() => {
                    _this.defaultPlaKeys = sourcesIds
                })
            }
        })
        .finally(e => {
            _this.v_loading = false
        })
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        getUpVal() {
            let menuIds = []
            this.permsissons.forEach((i, index) => {
                let refName = 'plat' + index
                let tree = this.$refs[refName][0]
                let id1 = tree.getAllSelectKey()
                menuIds = menuIds.concat(id1)
            })
            // 去重: 过滤的步骤等看实际数据结构
            this.accInfo.sourceIds = Array.from(new Set(menuIds))
            this.accInfo.menus = Array.from(new Set(menuIds))
        },
        submitForm(formName) {
            let _this = this
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.getUpVal()
                    if (this.v_loading) return
                    this.v_loading = true
                    if (this.confirmType == 'Create') {
                        addRole(this.accInfo)
                        .then(d => {
                            _this.$emit('submitForm')
                        })
                        .finally(e => {
                            _this.v_loading = false
                        })
                    }
                    if (this.confirmType == 'Update') {
                        updateRole(this.accInfo)
                        .then(d => {
                            _this.$emit('submitForm')
                        })
                        .finally(e => {
                            _this.v_loading = false
                        })
                    }
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        treeToList(tree) {
            let _self = this
            var list = [] // 结果lsit
            tree.forEach((item) => {
                list.push({
                    id: item.id,
                    name: item.name
                })
                if (item.children.length !== 0) { // 遍历树的第一层,只有一个根结点
                    _self.toListDF(item.children, list); // 遍历子树,并加入到list中.
                }
            })
            return list
        },
        toListDF(tree, list) {
            let _self = this
            tree.forEach((item) => {
                // 遍历最上层
                // 将当前树放入list中
                list.push({
                    id: item.id,
                    name: item.name
                })
                // 如果有子结点,再遍历子结点
                if (item.children.length !== 0) {
                    _self.toListDF(item.children, list) // 递归
                }
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.per-add-new-account{
    .power-kanban-wapper{
        width: 100%;
        // width: 330px;
        display: flex;
        flex-direction: row;
        flex-wrap: wrap;
        .kanban-item{
            padding: 10px;
            width: 270px;
            // height: 300px;
            margin-top:10px;
            margin-right: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            overflow: auto;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
        }
    }
}
</style>
