<template>
    <div class="project-version">
        <baseKanban title="系统配置管理" noTitle nopadding>
            <div class="form-wapper">
                <div v-loading="projectLoading" class="app-item">
                    <div class="title">系统配置</div>
                    <div class="kanban-wapper">
                        <el-form ref="project" :model="project" :rules="rules" label-width="100px" size="small">
                            <el-form-item label="系统名称" prop="name">
                                <el-input v-model="project.name" />
                            </el-form-item>
                            <el-form-item label="自动派发" prop="autoGive">
                                <div>
                                    <el-radio-group v-model="project.autoGive">
                                        <el-radio :label="0">否</el-radio>
                                        <el-radio :label="1">是</el-radio>
                                    </el-radio-group>
                                </div>
                            </el-form-item>
                            <el-form-item label="处置时限" prop="limitTime">
                                <div style="width: 200px;">
                                    <el-input v-model="project.limitTime" />
                                </div>
                            </el-form-item>
                            <el-form-item label="处置时限单位" prop="limitTimeUnit">
                                <div>
                                    <el-select v-model="project.limitTimeUnit" placeholder="请选择">
                                        <el-option label="天" value="1">天</el-option>
                                        <el-option label="小时" value="2">小时</el-option>
                                    </el-select>
                                </div>
                            </el-form-item>
                            <div class="btn-wapper">
                                <t-button @click="pushProject()">提交</t-button>
                            </div>
                        </el-form>
                    </div>
                </div>
            </div>
        </baseKanban>
    </div>
</template>

<script>
import { getPlaInfo, setPlaInfo } from '@/api/basic'
export default {
    data() {
        return {
            projectLoading: false,
            project: {
                name: '',
                autoGive: 1,
                limitTime: 1,
                limitTimeUnit: 1
            },
            rules: {
                name: [
                    { required: true, message: '请输入系统名称', trigger: 'blur' }
                ]
            }
        }
    },
    mounted() {
        this.getNowVersionInfo()
    },
    methods: {
        getNowVersionInfo() {
            let _this = this
            this.projectLoading = true
            getPlaInfo()
            .then(d => {
                if (d.data) {
                    let v = d.data
                    _this.project = v
                }
            })
            .finally(e => {
                _this.projectLoading = false
            })
        },
        pushProject() {
            let _this = this
            let upData = JSON.parse(JSON.stringify(this.project))
            this.$refs.project.validate((valid) => {
                if (valid) {
                    _this.projectLoading = true
                    setPlaInfo(upData)
                    .then(d => {
                        _this.$message({
                            message: '更新成功',
                            type: 'success'
                        })
                        _this.getNowVersionInfo()
                    })
                    .finally(e => {
                        _this.projectLoading = false
                    })
                } else {
                    return false
                }
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.project-version{
    .form-wapper{
        width: 100%;
        display: flex;
        flex-direction: row;
        padding-top:20px;
        .app-item{
            flex: 1;
            max-width: 800px;
            display: flex;
            flex-direction: column;
            margin: 0 20px;
            .title{
                width: 100%;
                text-align: center;
                background: var(--td-brand-color);
                // background-color: #42b19f;
                color: #fff;
                height: 40px;
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .kanban-wapper{
                padding: 20px;
                border: 1px solid #EBEEF5;
                border-top: 0px;
                .btn-wapper{
                    display: flex;
                    justify-content: center;
                    align-items: center;
                }
            }
        }
    }
}
</style>
