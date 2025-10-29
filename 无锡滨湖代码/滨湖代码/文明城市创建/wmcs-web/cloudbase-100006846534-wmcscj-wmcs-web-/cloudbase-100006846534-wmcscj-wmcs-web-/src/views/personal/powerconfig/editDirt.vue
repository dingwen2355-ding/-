<template>
    <el-dialog :title="item.dictName || '字典维护'" :visible.sync="show" append-to-body width="1000px" :before-close="cancel">
        <div v-loading="add_loading" class="dia-bd-hei edit-dirt-page">
            <!-- draggable=".draggableicon" -->
            <draggable v-model="list" animation="300">
                <transition-group>
                    <template v-for="(line, index) in list">
                        <div :key="index" class="dirt-line" :class="{'danger': line.dangerInfo}">
                            <div class="show-flex-r a-c draggableicon">
                                <i class="el-icon-rank margin-right-10" />
                            </div>
                            <div class="flex-1 show-flex-r v-wapper">
                                <div class="item-wapper show-flex-r a-c margin-right-10">
                                    <div class="lb">名称</div>
                                    <div>
                                        <el-input v-model="line.dictLabel" placeholder="请填写名称" size="mini" :disabled="line.disabled" @pointerdown.stop.native />
                                    </div>
                                </div>
                                <div class="item-wapper show-flex-r a-c margin-right-10">
                                    <div class="lb">取值</div>
                                    <div>
                                        <el-input v-model="line.dictValue" placeholder="请填写取值" size="mini" :disabled="line.disabled" @pointerdown.stop.native />
                                    </div>
                                </div>
                                <div class="item-wapper show-flex-r a-c margin-right-10">
                                    <div class="lb">是否启用</div>
                                    <div>
                                        <el-switch
                                            v-model="line.status"
                                            :disabled="line.disabled"
                                            :active-value="0"
                                            :inactive-value="1"
                                            size="mini"
                                        />
                                    </div>
                                </div>
                                <div v-if="line.dangerInfo" class="item-wapper show-flex-r a-c margin-right-10">
                                    <div class="zr-link-d unline noc">{{ line.dangerInfo }}</div>
                                </div>
                            </div>
                            <div class="edit-wapper">
                                <div class="zr-link-h unline margin-right-10" @click="toEdit(index)">编辑</div>
                                <div class="zr-link-p unline margin-right-10" @click="toAddItem(index)">在下方添加</div>
                                <div class="zr-link-d unline" @click="toDelItem(index)">删除</div>
                            </div>
                        </div>
                    </template>
                </transition-group>
            </draggable>
            <div class="dirt-line-add show-flex-r d-c zr-link-p unline" @click="addNewItem()">
                <i class="el-icon-plus margin-right-10" />
                <span>添加</span>
            </div>
        </div>
        <div slot="footer" class="dialog-footer">
            <t-button ghost class="margin-right-20" @click="cancel">取消</t-button>
            <t-button theme="primary" @click="submitForm()">确定</t-button>
            <!-- <el-button size="mini" @click="cancel">取 消</el-button>
            <el-button size="mini" type="bi" @click="submitForm()">确 定</el-button> -->
        </div>
    </el-dialog>
</template>
<script>
// 拖拽组件引入
import draggable from 'vuedraggable'
import { apiDirtDataList } from '@/api/personal'
export default {
    components: { draggable },
    props: {
        show: {
            type: Boolean,
            default: false
        },
        item: {
            type: Object,
            default: () => {
                return {}
            }
        }
    },
    data() {
        return {
            add_loading: false,
            list: [],
            delList: []
        }
    },
    mounted() {
        this.getDetail()
    },
    methods: {
        getDetail() {
            let _this = this
            _this.add_loading = true
            // 按照类型搜索列表
            apiDirtDataList({
                dictType: this.item.dictType,
                pageNum: 1,
                pageSize: 100
            })
            .then(data => {
                let list = data.data.records
                _this.list = JSON.parse(JSON.stringify(list)).map(i => {
                    i.disabled = true // 已经存在的默认不编辑
                    // 原始值保存一份, 计算是否有修改
                    i.origindictLabel = i.dictLabel
                    i.origindictValue = i.dictValue
                    i.originOrderNum = i.orderNum
                    i.originStatus = i.status
                    i.dangerInfo = ''
                    return i
                })
            })
            .finally(e => {
                _this.add_loading = false
            })
        },
        toEdit(index) {
            this.list[index].disabled = false
        },
        toAddItem(index) {
            // 下方添加
            this.list.splice(index + 1, 0, {
                id: 0,
                dictType: this.item.dictType,
                dictLabel: '', dictValue: '', orderNum: 0, disabled: false,
                status: 0, dangerInfo: ''
            })
        },
        addNewItem() {
            // 最后添加
            this.list.push({
                id: 0,
                dictType: this.item.dictType,
                dictLabel: '', dictValue: '', orderNum: 0, disabled: false,
                status: 0, dangerInfo: ''
            })
        },
        toDelItem(index) {
            // 这里删除是真的删除
            let _this = this
            let item = this.list[index]
            new Promise((res, rej) => {
                _this.useDia = this.$dialog({
                    header: '系统提示',
                    body: `是否确认删除名称为"${item.dictLabel}"的数据项？`,
                    style: 'color: rgba(0, 0, 0, 0.6)',
                    onConfirm: ({ e }) => {
                        res()
                    }
                })
            })
            .then(d => {
                _this.useDia.hide()
                _this.list.splice(index, 1)
                if (item.id) {
                    _this.delList.push(item)
                }
            })
            // this.$confirm('确定要删除吗?', '提示', {
            //     confirmButtonText: '确定',
            //     cancelButtonText: '取消',
            //     type: 'warning'
            // })
            // .then(d => {
            //     let item = this.list.splice(index, 1)[0]
            //     if (item.id) {
            //         this.delList.push(item)
            //     }
            // })
        },
        cancel() {
            this.$emit('cancel')
        },
        submitForm() {
            this.list.forEach((i, index) => {
                i.dictLabel = i.dictLabel.trim()
                i.dictValue = i.dictValue.trim()
                i.dangerInfo = ''
                i.operate = 0 // 先重置
                i.orderNum = index + 1
                if (i.id) {
                    // 任意有变动,则为修改
                    if (i.dictLabel !== i.origindictLabel) i.operate = 2
                    if (i.dictValue !== i.origindictValue) i.operate = 2
                    if (i.status !== i.originStatus) i.operate = 2
                    if (i.orderNum !== i.originOrderNum) i.operate = 2
                } else {
                    // 新增
                    i.operate = 1
                }
            })
            let nocheckPass = false // 验证是否通过
            this.list.forEach(i => {
                // 判断是否有重复name, code
                let namelist = this.list.filter(j => { return j.dictLabel == i.dictLabel })
                let vallist = this.list.filter(j => { return j.dictValue == i.dictValue })
                if (namelist.length > 1 || vallist.length > 1) {
                    nocheckPass = true
                    i.dangerInfo = '名称或者取值存在重复值'
                }
            })
            this.list.forEach(i => {
                if (!i.dictLabel || !i.dictValue) {
                    nocheckPass = true
                    i.dangerInfo = '名称和取值必须填写'
                }
            })
            if (nocheckPass) return
            // 增加删除标志
            this.delList.forEach(i => { i.operate = 3 })
            let upList = [...this.list, ...this.delList].filter(i => {
                return i.operate
            })
            upList.forEach(i => {
                i.dictType = this.item.dictType
            })
            this.$emit('submitForm', upList)
        }
    }
}
</script>
<style lang="scss" scoped>
.edit-dirt-page{
    .dirt-line-add{
        user-select: none;
        cursor: pointer;
        width: 100%;
        padding: 20px;
        margin-bottom: 20px;
        border-radius: 5px;
        border: 1px dashed #409EFF;
        display: flex;
        flex-direction: row;
        &:hover{
            background: #f7f7f7;
        }
    }
    .dirt-line{
        cursor: grab;
        width: 100%;
        padding: 10px;
        margin-bottom: 20px;
        border-radius: 5px;
        border: 1px dashed #409EFF;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        display: flex;
        flex-direction: row;
        &.danger{
            border-color: #F56C6C;
        }
        &:hover{
            background: #f7f7f7;
        }
        .edit-wapper{
            display: flex;
            justify-content: center;
            align-items: center;
            margin:0 10px;
        }
        .v-wapper{
            flex-wrap: wrap;
            .item-wapper{
                height: 44px;
            }
            .lb{
                width: fit-content;
                margin-right:10px;
            }
        }
    }
}
</style>
