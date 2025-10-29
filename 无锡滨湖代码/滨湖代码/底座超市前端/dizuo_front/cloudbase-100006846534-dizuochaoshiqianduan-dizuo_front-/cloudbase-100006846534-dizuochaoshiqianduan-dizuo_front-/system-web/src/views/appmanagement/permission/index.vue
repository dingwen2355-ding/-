<template>
    <!-- 权限申请 -->
    <div class="appwrap">
        <!-- 头部筛选条件 -->
        <el-row>
            <el-col :span="6">
                平台类别:
                <el-select v-model="sAppSort" placeholder="请选择平台类别" @change="fChangeAppSort">
                    <el-option v-for="item in aAppSort" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="6">
                审核状态:
                <el-select v-model="sShStutas" placeholder="请选择是否上架" @change="fChangeIsSj">
                    <el-option v-for="item in aShStatus" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-col>
            <el-col :span="8">
                申请时间内:
                <el-date-picker style="width: 70%;" v-model="sSqTime" type="daterange" range-separator="至"
                    start-placeholder="开始日期" end-placeholder="结束日期">
                </el-date-picker>
            </el-col>
            <el-col :span="4">
                <el-button type="primary">查询</el-button>
                <el-button>重置</el-button>
            </el-col>
        </el-row>
        <br />
        <br />
        <el-table ref="multipleTable" :data="aTableData" stripe tooltip-effect="dark" style="width: 100%"
            @selection-change="fSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="日期" width="120">
                <template slot-scope="scope">{{ scope.row.date }}</template>
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="120">
            </el-table-column>
            <el-table-column prop="address" label="地址" show-overflow-tooltip>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination @current-change="fCurrentChange" :current-page="nCurrent" :page-size="15"
            layout="total, prev, pager, next, jumper" :total="nTotal">
        </el-pagination>

        <!-- 新增弹框 -->
        <el-dialog title="申请上架审核" :visible.sync="bDialogVisible" width="30%">
            <el-form ref="form" :model="oAddForm" label-width="80px">
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="应用编号">
                            <el-input v-model="oAddForm.bh"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="应用名称">
                            <el-input v-model="oAddForm.name"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <!-- <el-row>
                    <el-col :span="12">
                        <el-form-item label="所属模块">
                            <el-input v-model="oAddForm.name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="是否上架">
                            <el-radio v-model="oAddForm.isSj" label="1">是</el-radio>
                            <el-radio v-model="oAddForm.isSj" label="2">否</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row> -->
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="应用类别">
                            <el-input v-model="oAddForm.appType"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="申请理由">
                            <el-input type="textarea" :rows="3" maxlength="200" v-model="oAddForm.sqLy"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="申请日期">
                            <el-input v-model="oAddForm.appUnit"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="处理时间">
                            <el-input v-model="oAddForm.clDate"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="审核状态">
                            <el-radio v-model="oAddForm.shStatus" label="1">通过</el-radio>
                            <el-radio v-model="oAddForm.shStatus" label="2">驳回</el-radio>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="24">
                        <el-form-item label="审核意见">
                            <el-input type="textarea" :rows="3" maxlength="200" v-model="oAddForm.shyj"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item>
                    <el-button type="primary" @click="fOnSubmit">新增</el-button>
                    <el-button>取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!-- 详情弹框 -->
        <!-- <el-dialog title="新增应用类别" :visible.sync="bDialogVisible" width="50%">
            <span>这是一段信息</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="bDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="bDialogVisible = false">确 定</el-button>
            </span>
        </el-dialog> -->
        <el-dialog :visible.sync="bImageUrlVisible">
            <img width="100%" :src="sDialogImageUrl" alt="">
        </el-dialog>
    </div>
</template>

<script>

export default {
    name: '',
    components: {},
    props: [''],
    data() {
        return {
            sAppName: '',// 应用名称
            // 平台类别
            sAppSort: '',
            aAppSort: [
                { value: '1', label: '1' },
                { value: '2', label: '2' },
            ],
            // 审核状态
            sShStutas: '',
            aShStatus: [
                { value: '1', label: '通过' },
                { value: '2', label: '驳回' },
                { value: '3', label: '待审核' },
            ],
            // 申请时间
            sSqTime: '',
            // 表格数据
            aTableData: [{
                date: '2016-05-03',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-02',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-04',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-01',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-08',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-06',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            }, {
                date: '2016-05-07',
                name: '王小虎',
                address: '上海市普陀区金沙江路 1518 弄'
            },],
            aMultipleSelection: [],
            nCurrent: 1, // 分页页码
            nTotal: 400, // 总条数
            bDialogVisible: false,
            oAddForm: {
                bh: '',// 编号
                name: '',// 应用名字
                sx: '', // 显示顺序
                shStatus: '1',// 是否上架
                isTj: '1',// 是否推荐
                appType: '',// 应用类别
                sqDate: '',// 申请日期
                clDate: '',// 处理时间
                sqLy: '',// 申请理由
                shyj:'',// 审核意见
            },
            sDialogImageUrl: '',
            bImageUrlVisible: false

        }
    },
    computed: {},
    watch: {},
    created() {

    },
    mounted() {

    },
    beforeDestroy() {

    },
    methods: {
        // 平台类别切换
        fChangeAppSort(val) {
            console.log(val)
        },
        // 是否上架切换
        fChangeIsSj(val) {
            console.log(val)
        },
        // 是否推荐切换
        fChangeIsTj(val) {
            console.log(val)
        },
        // 表格多选框
        fSelectionChange(val) {
            this.aMultipleSelection = val;
        },
        // 分页切换
        fCurrentChange() {
            console.log(`当前页: ${val}`);
        },
        // 删除图片
        fRemoveImg(file, fileList) {
            console.log(file, fileList);
        },
        // 预览图片 
        fPictureCardPreview(file) {
            this.sDialogImageUrl = file.url;
            this.bImageUrlVisible = true;
        },
        // 新增弹框确定按钮
        fOnSubmit() {
            console.log('submit!');
        }
    }
}
</script>
<style lang='scss' scoped>
.appwrap {
    height: 100%;
    width: 100%;
    padding: 30px;
}

// ::v-deep .el-col {
//     display: flex;
//     align-items: center;
//     justify-content: center;
// }

::v-deep .el-select {
    width: 70%;
}

::v-deep .el-pagination {
    display: flex;
    align-items: center;
    justify-content: end;
    margin-top: 20px;
}
</style>