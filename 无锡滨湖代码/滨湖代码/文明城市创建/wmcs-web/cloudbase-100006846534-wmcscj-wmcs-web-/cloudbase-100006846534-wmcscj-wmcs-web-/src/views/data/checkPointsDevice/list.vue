<template>
    <baseKanbanTd title="督察点位">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <searchRegionDept ref="regionCode" labelName="所属街镇" keyName="regionCode"/>
                    <normalSearch ref="orgName" labelName="所属组织" keyName="orgName" placName="请输入所属组织" />
                    <normalSearch ref="channelName" labelName="关联设备" keyName="channelName" placName="请输入设备名称" />
                    <!-- <normalSearch ref="channelId" labelName="设备编号" keyName="channelId" placName="请输入设备编号" /> -->
                    <normalSearch ref="pointsName" labelName="点位名称" keyName="pointsName" placName="请输入点位名称" />
                    <!-- <searchCheckPointsType ref="typeId" labelName="点位类型" keyName="typeId" placName="请选择点位类型" /> -->
                </div>
            </div>
            <div slot="table">
                <!-- <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                </div> -->
                <div>
                    <el-table
                        ref="listtable"
                        v-loading="tableListLoding"
                        stripe
                        :data="tableData"
                    >
                        <el-table-column label="街道/社区" prop="regionName" width="220" />
                        <el-table-column label="所属组织" prop="orgName" min-width="200" />
                        <el-table-column label="设备通道" prop="channelId" width="200" />
                        <el-table-column label="设备名称" prop="channelName" min-width="220" />
                        <el-table-column label="绑定点位" prop="pointsName" min-width="200" />
                        <el-table-column label="经度" prop="gpsX" width="150" />
                        <el-table-column label="纬度" prop="gpsY" width="150" />
                        <el-table-column label="操作" width="150" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-p unline m-5" v-if="scope.row.checkChannel == null" @click="toSetPoints(scope.row)">修改</div>
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">解除绑定</div> 
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
                <!-- 设置区划 -->
                <updateDialog
                    v-if="show_update"
                    :detail="row"
                    :show="show_update"
                    @cancel="() => { show_update = false }"
                    @sure="updatePointsDevice"
                ></updateDialog>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listCheckPointsDevice, saveAi, updateAI,deleteAi } from '@/api/data/checkPointsDevice'
import updateDialog from './updatePoints.vue'
export default {
    mixins: [reportMixins],
    components: { updateDialog },
    data() {
        return {
            show_update: false,
            deviceCount: 0,
            row: {}
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(listCheckPointsDevice, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `解除设备绑定后，该点位无法通过该设备生成相关工单数据。是否确认解绑？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return deleteAi(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        },
        toSetPoints(item) {
            this.row = item;
            this.show_update = true
        },
        updatePointsDevice(result) {
            let _this = this
            updateAI({
                "id": result.id,
                "channelId": result.channelId,
                "regionCode": result.regionCode,
                "pointsId": result.pointsId,
                "alarmTypeRemark": result.remark
            }).then(d => {
                _this.show_update = false
                _this.$successMessage("操作成功!")
                _this.refushPage()
            })
        }
    }
}
</script>
