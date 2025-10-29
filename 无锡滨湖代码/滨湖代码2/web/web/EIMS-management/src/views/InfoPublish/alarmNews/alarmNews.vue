<template>
  <div class="alarmNews page">
    <div class="content" style="width:100%">
      <div class="topArea" style="display:flex">
        <el-form class="addForm" ref="form" :model="form" inline label-width="80px">
          <el-form-item label="预警标题" prop="title">
            <el-input v-model="form.title" placeholder="请输入预警标题" clearable></el-input>
          </el-form-item>
          <el-form-item label="发布时间" prop="date">
            <el-date-picker
              style="width: 290px"
              v-model="form.date"
              type="datetimerange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="预警类别" prop="type">
            <el-cascader v-model="form.type" :options="typeList" :props="{ checkStrictly: true }" clearable></el-cascader>
          </el-form-item>
          <el-form-item label="预警状态" prop="type">
            <el-select v-model="form.dealType" clearable placeholder="预警状态">
              <el-option v-for="item in dealTypeList" :value="item.value" :label="item.label" :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="预警来源" prop="type">
            <el-select v-model="form.flag" clearable placeholder="预警来源">
              <el-option v-for="item in flagList" :value="item.value" :label="item.label" :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-button @click="getData" type="primary">查询</el-button>
          <el-button @click="handleAdd" icon="el-icon-circle-plus-outline" type="primary">新增</el-button>
        </el-form>
        <!-- <div class="formBtn">
          <el-button @click="getData" type="primary">查询</el-button>
          <el-button @click="handleAdd" icon="el-icon-circle-plus-outline" type="primary">新增</el-button>
        </div>-->
      </div>
      <div class="tableArea">
        <el-table :data="showTable" height="700" border @selection-change="handleSelectionChange" style="width: 100%">
          <el-table-column type="selection" width="55"></el-table-column>
          <el-table-column label="序号" type="index" :index="indexMethod" width="60"></el-table-column>
          <el-table-column prop="warningTitle" label="预警标题" width="100"></el-table-column>
          <el-table-column prop="type" label="预警类别" width="100">
            <template
              slot-scope="scope"
            >{{scope.row.warningType2!==''?scope.row.warningType1 + '/' + scope.row.warningType2:scope.row.warningType1}}</template>
          </el-table-column>
          <el-table-column prop="warningTitle" label="预警状态" width="80">
            <template slot-scope="scope">
              <!-- {{scope.row.deal ==='1'?'已处置':'未处置'}} -->
              {{scope.row.deal ==='1'?'有效':scope.row.deal ==='2'?'待确定':'无效'}}
            </template>
          </el-table-column>
          <el-table-column prop="flag" label="预警来源" width="80">
            <template slot-scope="scope">
              <!-- {{scope.row.deal ==='1'?'已处置':'未处置'}} -->
              {{scope.row.flag ==='1'?'AI分析':scope.row.flag ==='2'?'遥感卫星':scope.row.flag ==='3'?'监测站':''}}
            </template>
          </el-table-column>
          <el-table-column prop="releaseTime" label="发布时间" width="170"></el-table-column>
          <el-table-column prop="warningContent" label="预警内容"></el-table-column>
          <el-table-column prop="defenseAdvice" label="防御建议"></el-table-column>
          <el-table-column prop="warningTitle" label="处置结果" width="105">
            <template slot-scope="scope">
              <!-- {{scope.row.deal==='1'?scope.row.dealContent:'-'}}-->
              {{scope.row.deal ==='1'?'有效':scope.row.deal ==='2'?'待确定':'无效'}}
            </template>
          </el-table-column>
          <el-table-column prop="dealContent" label="处置意见" width="105"></el-table-column>
          <el-table-column prop label="操作" width="130">
            <template slot-scope="scope">
              <el-button @click="checkAlarm(scope.row)" type="text">{{scope.row.deal === '1'?'查看详情':'处置'}}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="footer" style="margin-top:40px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage"
          :page-sizes="[15,50,100,500]"
          :page-size="pageSize"
          layout="total, prev,sizes, pager, next"
          :total="total"
        ></el-pagination>
      </div>
    </div>
    <addDialogVue :dialogFormVisible.sync="showAdd" @success="addFinish" />
    <checkDialogVue :dialogFormVisible.sync="showCheck" @success="addFinish" :formInfo="checkForm" :isCode="isCode" />
  </div>
</template>

<script>
import addDialogVue from "./addDialog.vue"
import checkDialogVue from "./alarmCheckDialog.vue"
import axios from 'axios'
import qs from 'qs'
export default {
  components: {
    addDialogVue,
    checkDialogVue
  },
  data() {
    return {
      tableData: [],
      form: {
        title: '',
        date: '',
        type: '',
        dealType: '',
        flag: ''
      },
      dealTypeList: [
        // {
        //   value: '1',
        //   label: '已处置'
        // }, {
        //   value: '2',
        //   label: '未处置'
        // }
        {
          value: '1',
          label: '有效'
        }, {
          value: '2',
          label: '待确定'
        }, {
          value: '3',
          label: '无效'
        }
      ],
      flagList: [
        {
          value: '1',
          label: 'AI分析'
        }, {
          value: '2',
          label: '遥感卫星'
        }, {
          value: '3',
          label: '监测站'
        }
      ],
      typeList: [],
      multipleSelection: '',
      currentPage: 1,
      total: 20,
      pageSize: 15,
      showAdd: false,
      showSelect: false,
      showCheck: false,
      checkForm: {},
      isCode: 0
    }
  },
  computed: {
    showTable() {
      return (this.tableData || []).slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      )
    }
  },
  mounted() {
    this.getData()
    this.getTypeList()
  },
  methods: {
    getData() {
      let param = {
        title: this.form.title || null,
        type1: this.form.type[0] || null,
        type2: this.form.type[1] || null,
        deal: this.form.dealType || null,
        flag: this.form.flag || null,
        startDate: this.form.date ? this.changeDate(this.form.date[0]) : null,
        endDate: this.form.date ? this.changeDate(this.form.date[1]) : null
      }
      let url = '/gtw/EIMS-management/commandWarning/getWarning?' + qs.stringify(param, { skipNulls: true })
      axios.get(url).then(r => {
        this.tableData = r.data.data || []
        this.total = this.tableData.length
        this.currentPage = 1
      })
    },
    changeTree(data) {
      let arr = []
      if (typeof (data) === 'object') {
        for (let key in data) {
          let obj = {
            label: key,
            value: key,
            children: []
          }
          data[key].forEach(item => {
            let obj2 = {
              label: item,
              value: item
            }
            if (item) {
              obj.children.push(obj2)
            }
          })
          arr.push(obj)
        }
      }
      return arr
    },
    addFinish() {
      this.showCheck = false
      this.showAdd = false
      this.getData()
      this.$message.success('操作成功')
    },
    infoPush(row) {
      this.$message('推送')
    },
    sendMessage(row) {
      this.$message('发短信')
    },
    indexMethod(index) {
      return (index + 1) + (this.currentPage - 1) * this.pageSize
    },
    handleAdd() {
      this.showAdd = true
    },
    handleSelect() {
      this.showSelect = true
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    },
    checkAlarm(row) {
      this.showCheck = true
      this.checkForm = row
      // scope.row.deal === '1'?'查看详情':'处置'
      if (row.deal === '1') {
        this.isCode = 1
      } else {
        this.isCode = 2
      }
    },
    submit() {
      this.$emit('success', this.multipleSelection)
      this.$emit("update:dialogFormVisible", false)
    },
    handleClose() {
      this.$emit("update:dialogFormVisible", false)
    },
    handleSizeChange(v) {
      this.pageSize = v
    },
    handleCurrentChange() {

    },
    changeDate(time) {
      let year = time.getFullYear()
      let mounth = (time.getMonth() + 1) < 10 ? `0${(time.getMonth() + 1)}` : (time.getMonth() + 1)
      let day = time.getDate() < 10 ? `0${time.getDate()}` : time.getDate()
      let hour = time.getHours() < 10 ? `0${time.getHours()}` : time.getHours()
      let min = time.getMinutes() < 10 ? `0${time.getMinutes()}` : time.getMinutes()
      let second = time.getSeconds() < 10 ? `0${time.getSeconds()}` : time.getSeconds()
      let result = `${year}-${mounth}-${day} ${hour}:${min}:${second}`
      return result
    },
    getTypeList() {
      this.typeList = [
        {
          "label": "自然灾害",
          "value": "自然灾害",
          "children": [
            {
              "label": "防汛抗旱",
              "value": "防汛抗旱"
            },
            {
              "label": "森林防火",
              "value": "森林防火"
            },
            {
              "label": "抗震救灾",
              "value": "抗震救灾"
            },
            {
              "label": "其它",
              "value": "其它"
            }
          ]
        },
        {
          "label": "事故灾难",
          "value": "事故灾难",
          "children": [
            {
              "label": "矿山救援",
              "value": "矿山救援"
            },
            {
              "label": "危化品救援",
              "value": "危化品救援"
            },
            {
              "label": "消防",
              "value": "消防"
            },
            {
              "label": "其它",
              "value": "其它"
            }
          ]
        },
        {
          "label": "气象预警",
          "value": "气象预警"
        }
      ]
      // let url = '/gtw/EIMS-management/event/queryEventType'
      // axios.get(url).then(r => {
      //   let data = r.data.data || []
      //   this.typeList = this.changeTree(data)
      //   console.log(this.typeList)
      // })
    }
  }
}
</script>

<style lang="scss">
// .alarmNews{

// }
</style>
