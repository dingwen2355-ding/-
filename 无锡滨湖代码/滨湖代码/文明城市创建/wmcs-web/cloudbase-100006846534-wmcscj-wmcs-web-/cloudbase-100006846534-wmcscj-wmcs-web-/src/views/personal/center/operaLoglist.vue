<template>
    <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
        <div slot="customQuery" class="listSearch padding-left-10">
            <div class="search-line">
                <dateMSelectM
                    ref="acc"
                    key1="beginTime"
                    key2="endTime"
                    labelName="时间选择"
                />
            </div>
        </div>
        <div slot="table">
            <el-table v-loading="tableListLoding" :data="tableData" style="width: 100%" stripe>
                <el-table-column label="模块名" prop="title" width="200" />
                <el-table-column label="操作类型" prop="businessType" width="100">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <el-tag v-if="scope.row.businessType === 0" type="danger" size="small">授权</el-tag>
                            <el-tag v-if="scope.row.businessType === 1" type="primary" size="small">新增</el-tag>
                            <el-tag v-if="scope.row.businessType === 2" type="success" size="small">修改</el-tag>
                            <el-tag v-if="scope.row.businessType === 3" type="danger" size="small">删除</el-tag>
                            <el-tag v-if="scope.row.businessType === 4" type="bi" size="small">审核</el-tag>
                            <el-tag v-if="scope.row.businessType === 5" type="warning" size="small">导出</el-tag>
                            <el-tag v-if="scope.row.businessType === 6" type="warning" size="small">导入</el-tag>
                            <el-tag v-if="scope.row.businessType === 7" type="warning" size="small">上传</el-tag>
                            <el-tag v-if="scope.row.businessType === 8" type="info" size="small">下载</el-tag>
                            <el-tag v-if="scope.row.businessType === 9" type="info" size="small">其他</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="操作人" prop="operName" width="200" />
                <el-table-column label="操作时间" prop="createTime" width="170" />
                <el-table-column label="IP" prop="operIp" width="150" />
                <el-table-column label="执行情况" width="200">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <el-tag v-if="scope.row.status != '0'" type="danger" size="small">失败</el-tag>
                            <el-tag v-if="scope.row.status == '0'" type="success" size="small">成功</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="错误信息" prop="errorMsg" min-width="300"/>
                <el-table-column label="查看" fixed="right" width="100">
                    <template slot-scope="scope">
                        <div class="zr-link-p unline" @click="checkInfo(scope.row)">查看</div>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog title="日志详情" :visible.sync="show_info_dia" append-to-body width="800px" :before-close="() => { show_info_dia = false }">
                <div class="dia-bd-hei">
                    <el-form ref="ruleForm" :model="nowItem">
                        <el-form-item label="方法:" :label-width="formLabelWidth">
                            <div class="w-100">
                                <div class="form-page-info-val">{{ nowItem.method }}</div>
                            </div>
                        </el-form-item>
                        <el-form-item label="接口:" :label-width="formLabelWidth">
                            <div class="w-100">
                                <div class="form-page-info-val">{{ nowItem.operUrl }}</div>
                            </div>
                        </el-form-item>
                        <el-form-item label="入参:" :label-width="formLabelWidth">
                            <div class="w-100">
                                <div class="form-page-info-val">{{ nowItem.operParam }}</div>
                            </div>
                        </el-form-item>
                        <el-form-item label="出参:" :label-width="formLabelWidth">
                            <div class="w-100">
                                <div class="form-page-info-val">{{ nowItem.jsonResult }}</div>
                            </div>
                        </el-form-item>
                        <el-form-item v-if="nowItem.errorMsg" label="出错信息:" :label-width="formLabelWidth">
                            <div class="w-100">
                                <div class="form-page-info-val">{{ nowItem.errorMsg }}</div>
                            </div>
                        </el-form-item>
                    </el-form>
                </div>
            </el-dialog>
        </div>
    </baseSearchForm>
</template>
<script>
import { getOperationLogList } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
export default {
    mixins: [reportMixins],
    data() {
        return {
            nowItem: {},
            show_info_dia: false,
            formLabelWidth: '80px'
        }
    },
    methods: {
        getList(search) {
            this.getTableData(getOperationLogList, search)
        },
        checkInfo(item) {
            console.log(item)
            this.nowItem = item
            this.show_info_dia = true
        }
    }
}
</script>
