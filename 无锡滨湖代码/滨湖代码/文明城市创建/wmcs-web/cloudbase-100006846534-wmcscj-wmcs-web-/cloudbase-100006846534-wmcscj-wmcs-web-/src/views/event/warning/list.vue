<template>
    <baseKanbanTd title="工单总览">
        <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
            <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <dirtWorkAlarmType ref="type" labelName="告警类型" keyName="type" placName="请选择告警类型" />
                    <normalSearch ref="channelId" labelName="点位ID" keyName="channelId" placName="请输入点位ID" />
                    <normalSearch ref="content" labelName="告警描述" keyName="info" placName="请输入告警描述" />
                </div>
            </div>
            <div slot="table">
                <!-- <div class="show-flex-r j-e-2 margin-bottom-10">
                    <t-button @click="toAddNew()">+ 新增</t-button>
                </div> -->
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="告警类型" prop="type" width="150">
                            <template slot-scope="scope">
                                <dirtWorkAlarmType noLabel useType="form" allWidth="100%" disabled onlyShow
                                    :defaultVal="scope.row.type" />
                            </template>
                        </el-table-column>
                        <el-table-column label="告警设备编号" prop="channelId" min-width="200" />
                        <el-table-column label="告警设备组织" prop="treeName" min-width="200" />
                        <el-table-column label="设备名称" prop="channelName" min-width="250" />
                        <el-table-column label="告警描述" prop="content" min-width="300" />
                        <el-table-column label="告警时间" prop="createTime" width="170" />
                        <el-table-column label="图片" width="80" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <el-image :src="getImage(scope.row)" :preview-src-list="[getImage(scope.row)]" alt=""
                                        srcset=""></el-image>
                                    <!-- <div class="zr-link-p unline m-5" @click="row = scope.row; showVideo = true">监控</div> -->
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
        <video-dialog v-if="showVideo" :show="showVideo" :row="row" @cancel="showVideo = false"></video-dialog>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { listWorkWarn, getImg } from '@/api/work/workWarn'
import axios from 'axios'
import videoDialog from "@/components/util/videoDialog.vue"

export default {
    mixins: [reportMixins],
    components: { videoDialog },
    data() {
        return {
            showVideo: false,
            row: {}
        }
    },
    methods: {
        getList(searchData) {
            this.getTableData(listWorkWarn, searchData)
        },
        getImage(row) {
            return getImg(row.images)
        }
    }
}
</script>
