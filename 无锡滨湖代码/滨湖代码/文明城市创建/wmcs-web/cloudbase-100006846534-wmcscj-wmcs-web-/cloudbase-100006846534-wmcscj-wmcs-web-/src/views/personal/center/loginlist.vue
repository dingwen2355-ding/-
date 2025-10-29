<template>
    <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
        <div slot="customQuery" class="listSearch">
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
                <el-table-column label="登录账号" prop="userName" min-width="200" />
                <el-table-column label="日志" prop="msg" min-width="200" />
                <el-table-column label="IP" prop="ipaddr" width="200" />
                <el-table-column label="登录状态" prop="status" width="100">
                    <template slot-scope="scope">
                        <div class="show-flex-r">
                            <el-tag v-if="scope.row.status == '0'" type="success" size="small">成功</el-tag>
                            <el-tag v-if="scope.row.status != '0'" type="error" size="small">失败</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column label="时间" prop="accessTime" width="170" />
            </el-table>
        </div>
    </baseSearchForm>
</template>
<script>
import { getLoginLogList } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
export default {
    mixins: [reportMixins],
    methods: {
        getList(search) {
            this.getTableData(getLoginLogList, search)
        }
    }
}
</script>
