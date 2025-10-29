<template>
    <!-- 视频设备选择弹框 -->
    <el-dialog :title="title" :visible.sync="show" append-to-body :width="rowWidth" :before-close="cancel">
        <div class="dia-bd-hei">
            <div class="show-flex-r" style="height: 58vh;">
                <div v-loading="tableListLoding_left" class="show-flex-c margin-right-10 h-100" style="width: 400px;border-right: 10px solid #eceef5;">
                    <!-- 组织搜索 -->
                    <div class="flex-1" style="background: #ffffff; overflow: auto; padding: 10px">
                        <div
                            class="show-flex-r margin-bottom-10 margin-top-5"
                            style="padding: 0 10px"
                        >
                            <el-input v-model="filterText" size="mini" clearable placeholder="请输入组织名称或ID" />
                        </div>
                        <el-tree
                            ref="treeId"
                            :props="props"
                            :data="deviceTree"
                            show-checkbox
                            check-strictly
                            node-key="id"
                            :filter-node-method="filterNode"
                            :default-expand-all="false"
                            :default-checked-keys="currentKey"
                            @check="changeCheckType"
                        />
                    </div>
                </div>
                <div class="flex-1 w-0 h-100" style="overflow: auto;">
                    <baseSearchForm ref="searchTable" @getList="getList" @search="startSearch" @reset="resetQuery">
                        <div slot="customQuery" class="listSearch">
                            <div class="search-line">
                                <normalSearch ref="channelId" labelName="通道ID" keyName="channelId" placName="请输入通道ID" />
                                <normalSearch ref="name" labelName="通道名称" keyName="name" placName="请输入通道名称" />
                            </div>
                        </div>
                        <div slot="table">
                            <!-- 已经选择的 -->
                            <div v-if="mode == 'many'" class="show-flex-r wrap margin-bottom-10">已选择:</div>
                            <div
                                v-if="mode == 'many'"
                                class="show-flex-r wrap margin-bottom-10 padding-top-10 padding-left-10"
                                style="min-height:50px;max-height: 100px;overflow: auto;border:1px solid #EBEEF5;border-radius: 5px;"
                            >
                                <el-tag
                                    v-for="(t, index) in selectList"
                                    :key="index"
                                    closable
                                    size="small"
                                    class="margin-right-10 margin-bottom-10"
                                    @close="removeTag(index)"
                                >
                                    {{ t.name }}
                                </el-tag>
                            </div>
                            <el-table v-loading="tableListLoding" stripe size="mini" :data="tableData">
                                <el-table-column label="通道id" prop="channelId" width="200" />
                                <el-table-column label="通道名称" prop="name" min-width="300" />
                                <el-table-column label="经度" prop="gpsX" width="180" />
                                <el-table-column label="纬度" prop="gpsY" width="180" />
                                <el-table-column label="操作" width="100" fixed="right">
                                    <template slot-scope="scope">
                                        <div class="show-flex-r d-c">
                                            <div v-if="mode == 'single'" class="zr-link-p unline m-5" @click="selectUserSingle(scope.row)">选择</div>
                                            <div v-if="mode == 'many'" class="zr-link-p unline m-5" @click="addUserSelect(scope.row)">添加</div>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </baseSearchForm>
                </div>
            </div>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
            <t-button v-if="mode == 'many'" theme="primary" @click="manySelect()">确定</t-button>
            <!-- <el-button size="mini" @click="cancel">取 消</el-button>
            <el-button v-if="mode == 'many'" size="mini" type="bi" @click="manySelect()">确定选择</el-button> -->
        </div>
    </el-dialog>
</template>
<script>
import { reportMixins } from '@/utils/mixins'
import { pageDevice } from "@/api/data/device"
import { pageDeviceChannel } from "@/api/data/deviceChannel"
export default {
    mixins: [reportMixins],
    props: {
        usenotice: {
            // 是否通知
            type: Boolean,
            default: false
        },
        title: {
            type: String,
            default: '选择设备'
        },
        rowWidth: {
            // 整体宽度设置
            type: String,
            default: '95%'
        },
        show: {
            type: Boolean,
            default: false
        },
        selecFunc: {
            type: Function,
            // 外部执行方法
            default: () => {
                return new Promise((res, rej) => {
                    res()
                })
            }
        },
        getMoreAttr: {
            type: Function,
            // 使调用方可以添加额外搜索参数的能力
            default: () => { return {} }
        },
        mode: {
            type: String,
            default: 'single' // single 单选 many 多选
        },
        backType: {
            type: String,
            default: 'all'
        }
    },
    data() {
        return {
            noStart: true,
            tableListLoding_left: false,
            filterText: "",
            props: {
                label: "name",
                children: "children"
            },
            includeChildren: false,
            deviceTree: [],
            currentKey: [],
            // noStart: true,
            selectList: [] // 多选模式下选择的内容
        }
    },
    watch: {
      filterText(val) {
        this.$refs.treeId.filter(val);
      }
    },
    mounted() {
        let _this = this
        // 这里看是否需要初始化一些条件:如果不想等组件,可以直接去调用getList方法,组件后台自己装显示
        // 看后续需要扩展,这里先这样
        this.$nextTick(() => {
            // dia里面的内容会在mounted之后,延迟一下
            // setTimeout(() => {
            //     _this.startSearch()
            // }, 500)
            _this.getAllType();
        })
    },
    methods: {
        getAllType() {
            let _this = this;
            this.tableListLoding_left = true
            pageDevice({}).then(data => {
                _this.deviceTree = data.data.records || [];
            })
            .finally(e => {
                _this.tableListLoding_left = false
            })
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        changeCheckType(node, list) {
            let arr = [];
            if (list.checkedKeys.length > 0) {
                arr = [node.id];
            }
            this.$nextTick(() => {
                this.currentKey = arr;
                this.$refs.treeId.setCheckedKeys(arr);
                this.startSearch();
            });
        },
        getList(searchData) {
            if (this.currentKey.length > 0) {
                searchData["treeId"] = this.currentKey[0];
            }
            // 合并外部使用参数
            let moreAttr = this.getMoreAttr()
            Object.assign(searchData, moreAttr)
            this.getTableData(pageDeviceChannel, searchData)
        },
        cancel() {
            this.$emit('cancel')
        },
        addUserSelect(item) {
            // 已经选择的就不能重复选择了
            if (this.selectList.find(i => { return i.id == item.id })) {
                return
            }
            this.selectList.push(JSON.parse(JSON.stringify(item)))
        },
        removeTag(index) {
            this.selectList.splice(index, 1)
        },
        manySelect() {
            if (this.tableListLoding) return
            this.tableListLoding = true
            let id = []
            if (this.backType == 'id') {
                id = this.selectList.map(i => { return i.id })
            } else {
                id = JSON.parse(JSON.stringify(this.selectList))
            }
            this.outerEmit(id)
        },
        selectUserSingle(user) {
            if (this.tableListLoding) return
            this.tableListLoding = true
            if (this.backType == 'id') {
                this.outerEmit(user.customerId)
            } else {
                this.outerEmit(user)
            }
        },
        outerEmit(id) {
            let _this = this
            // 执行外部操作: id 可能是单选 或者多选数组
            // 这里可以考虑几种模式:
            // 1 直接关掉,如果遇到错误,重新打开重新选
            // 2 外面执行报错了,单独调用组件内部还原转圈
            // 3(选择) 外部把执行方法作为参数传进来,内部执行完处理,成功就通知外面刷新并关闭
            this.selecFunc(id)
            .then(data => {
                if (this.usenotice) {
                    _this.$successMessage('操作成功!')
                }
                _this.$emit('refush')
                _this.$emit('cancel')
            })
            .finally(e => {
                _this.tableListLoding = false
            })
        }
    }
}
</script>
