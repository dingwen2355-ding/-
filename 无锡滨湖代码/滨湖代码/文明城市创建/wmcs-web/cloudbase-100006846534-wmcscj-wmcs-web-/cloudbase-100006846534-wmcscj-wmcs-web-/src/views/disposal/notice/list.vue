<template>
    <baseKanbanTd title="消息通知">
        <baseSearchForm ref="searchTable" noSearch @getList="getList" @search="startSearch" @reset="resetQuery">
            <!-- <div slot="customQuery" class="listSearch padding-left-10">
                <div class="search-line">
                    <normalSearch ref="checkPoints" labelName="标题" keyName="checkPoints" placName="请输入标题" />
                    <dateMSelectM ref="sendTime" labelName="发送时间" key1="beginTime" key2="endTime" datetype="second" allWidth="450px" />
                </div>
            </div> -->
            <div slot="table">
                <div>
                    <el-table ref="listtable" v-loading="tableListLoding" stripe :data="tableData">
                        <el-table-column label="内容" prop="content" min-width="260" />
                        <el-table-column label="发送时间" prop="sendTime" width="200" />
                        <el-table-column label="操作" width="100" fixed="right">
                            <template slot-scope="scope">
                                <div class="show-flex-r a-c">
                                    <div class="zr-link-d unline m-5" @click="delItem(scope.row)">删除</div>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </div>
        </baseSearchForm>
    </baseKanbanTd>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { eventNoticeList, delEventNotice } from "@/api/workflow/workflow"
export default {
    mixins: [reportMixins],
    data() {
        return {
            noStart: true,
            userId: ''
        }
    },
    mounted() {
        let _this = this
        this.getMyUserDataOnlyWait()
        .then(info => {
            _this.userId = info.user.id
            _this.startSearch()
        })
    },
    methods: {
        getList(searchData) {
            if (!this.userId) {
                this.tableListLoding = false
                return
            }
            searchData['reviceUserId'] = this.userId
            this.getTableData(eventNoticeList, searchData)
        },
        delItem(item) {
            let _this = this
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除ID为"${item.id}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                return delEventNotice(item.id)
            })
            .then(d => {
                _this.$successMessage('操作成功!')
                _this.refushPage()
            })
        }
    }
}
</script>
