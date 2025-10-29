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
        <div slot="table" class="personal-table-notice">
            <!-- <div class="t-header">
                <div>
                    <sfChangeButton
                        ref="read"
                        keyName="read"
                        :lists="readList"
                        noradis
                        @BtnClick="startSearch"
                    />
                </div>
                <div>
                    <span class="mark-all zr-link-p unline" @click="markAll">标记为全部已读</span>
                </div>
            </div> -->
            <el-table v-loading="tableListLoding" :data="tableData" style="width: 100%" stripe>
                <el-table-column label="标题" prop="title" />
                <el-table-column label="时间" prop="createTime" />
                <el-table-column label="类型" prop="type" />
                <el-table-column label="状态">
                    <template slot-scope="scope">
                        <span>{{ scope.row.readFlag ? '已读' : '未读' }}</span>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </baseSearchForm>
</template>
<script>
import { getNoticeList } from '@/api/personal'
import { reportMixins } from '@/utils/mixins'
export default {
    mixins: [reportMixins],
    data() {
        return {
            readList: [
                { label: '全部', value: 0 },
                { label: '未读', value: 1 }
            ],
            noticeList: []
        }
    },
    methods: {
        getList(search) {
            this.getTableData(getNoticeList, search).then((data) => {
                this.noticeList = data.data
            })
        }
    }
}
</script>
<style lang="scss" scoped>
.personal-table-notice{
    .t-header{
        width: 100%;
        margin-bottom: 10px;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        .mark-all{
            font-size: 14px;
        }
    }
}
</style>
