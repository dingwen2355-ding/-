<template>
    <el-dialog :title="useType == 'add' ? '新增资源' : item.name" :visible.sync="show" append-to-body width="800px" :before-close="cancel">
        <div v-loading="upLoading" class="dia-bd-hei">
            <el-form ref="ruleForm" :model="item" :rules="rules" size="small">
                <el-form-item label="上级资源:" prop="parentId" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <div>{{ parentName || '根目录' }}</div>
                    </div>
                </el-form-item>
                <el-form-item label="类型:" prop="type" :label-width="formLabelWidth">
                    <div style="width:500px" class="zr-100-el-wapper">
                        <el-radio-group v-model="item.type">
                            <el-radio label="C">菜单</el-radio>
                            <el-radio label="M">目录</el-radio>
                            <el-radio label="F">功能</el-radio>
                        </el-radio-group>
                        <!-- <el-select v-model="item.type" placeholder="请选择">
                            <el-option label="目录" value="M" />
                            <el-option label="菜单" value="C" />
                            <el-option label="功能" value="F" />
                        </el-select> -->
                    </div>
                </el-form-item>
                <el-form-item label="资源名称:" prop="name" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.name" placeholder="请填写资源名称" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item label="排序:" prop="orderNum" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.orderNum" placeholder="请填写排序" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item v-if="item.type == 'F'" label="权限字符:" prop="perms" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.perms" placeholder="请填写权限字符" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item v-if="item.type != 'F'" label="资源图标:" prop="icon" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.icon" placeholder="请填写资源图标" autocomplete="off" />
                        <a class="zr-link-p useline" target="_blank" href="https://fontawesome.com.cn/v4/icons">
                            图标资源填写参考网站
                        </a>
                        <span style="color: #999999;margin-left:20px;">
                            示例: 填写 fa fa-bell-o 则展示<i class="fa fa-bell-o" />
                        </span>
                    </div>
                    <div>
                        预览:
                        <i :class="[item.icon]" />
                    </div>
                </el-form-item>
                <el-form-item v-if="item.type == 'C' || item.type == 'M'" label="资源链接:" prop="link" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.link" placeholder="请填写资源链接" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item v-if="item.type == 'C'" label="路由参数:" prop="query" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-input v-model="item.query" placeholder="请填写路由参数" autocomplete="off" />
                    </div>
                </el-form-item>
                <el-form-item label="是否显示:" prop="visible" :label-width="formLabelWidth">
                    <div style="width:500px">
                        <el-switch
                            v-model="item.visible"
                            :active-value="0"
                            :inactive-value="1"
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
import { createResource, editResource } from '@/api/personal'
import { number } from 'echarts/lib/export'
export default {
    props: {
        show: {
            type: Boolean,
            default: false
        },
        useType: {
            type: String,
            default: 'add' // add, edit
        },
        platform: {
            type: number,
            default: 1 // add, edit
        }
    },
    data() {
        return {
            upLoading: false,
            parentName: '',
            rules: {
                name: [
                    { required: true, message: '请输入资源名称', trigger: 'blur' }
                ],
                type: [
                    { required: true, message: '请选择资源类型', trigger: 'blur' }
                ],
                perms: [
                    { required: true, message: '请填写权限字符', trigger: 'blur' }
                ],
                link: [
                    { required: true, message: '请填写资源链接', trigger: 'blur' }
                ]
            },
            item: {
                id: null,
                name: '',
                icon: '',
                link: '',
                parentId: '0',
                visible: 0,
                orderNum: 10,
                type: 'C' // 1 菜单 2 功能
            },
            formLabelWidth: '130px'
        }
    },
    methods: {
        cancel() {
            this.$emit('cancel')
        },
        initParentTypeId(pid, name) {
            this.item.parentId = pid
            this.parentName = name
        },
        initItem(item) {
            this.item = JSON.parse(JSON.stringify(item))
            this.parentName = this.item.parentName
        },
        submitForm(formName) {
            this.item.name = this.item.name && this.item.name.trim() || ''
            this.item.icon = this.item.icon && this.item.icon.trim() || ''
            this.item.link = this.item.link && this.item.link.trim() || ''
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.useType == 'add') {
                        this.addType()
                    }
                    if (this.useType == 'edit') {
                        this.editType()
                    }
                } else {
                    return false
                }
            })
        },
        addType() {
            this.item.platform = this.platform
            let _this = this
            if (this.upLoading) return
            this.upLoading = true
            createResource(this.item)
            .then(d => {
                _this.$emit('submitForm')
            })
            .finally(e => {
                _this.upLoading = false
            })
        },
        editType() {
            this.item.platform = this.platform
            let _this = this
            if (this.upLoading) return
            this.upLoading = true
            editResource(this.item)
            .then(d => {
                _this.$emit('submitForm')
            })
            .finally(e => {
                _this.upLoading = false
            })
        }
    }
}
</script>
