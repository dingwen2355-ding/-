<template>
    <!-- 企业列表 -->
    <div class="appwrap">
        <!-- 头部筛选条件 -->
        <el-row>
            <el-col :span="20">
                企业名称:
                <el-input v-model="sFirmName" placeholder="请输入企业名称/APP ID" style="width: 70%;"></el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary">搜索</el-button>
                <el-button>清空</el-button>
            </el-col>
        </el-row>
        <br />
        <br />
        <!--  -->
        <el-row>
            <el-button type="primary" @click="bDialogVisible = true">新增</el-button>
            <el-button type="danger">删除</el-button>
        </el-row>
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
        <el-dialog title="新增应用类别" :visible.sync="bDialogVisible" width="50%">
            <span>这是一段信息</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="bDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="bDialogVisible = false">确 定</el-button>
            </span>
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
            sFirmName:'',// 企业名称
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
            bDialogVisible:false,
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
        // 应用类别切换
        fChangeAppSort(val) {
            console.log(val)
        },
        // 应用状态切换
        fChangeAppStatus(val) {
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