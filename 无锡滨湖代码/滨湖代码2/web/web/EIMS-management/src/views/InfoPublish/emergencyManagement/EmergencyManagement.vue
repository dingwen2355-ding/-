<template>
  <div class="EmergencyManagement blank">
       <!-- <div class="left-container">
          <div class="search-box">&ndash;&gt;
        <el-input placeholder="请输入关键字" suffix-icon="el-icon-search" v-model="searchInput"></el-input>
      </div>
      <div class="event-list">
        <div class="event-list-ctrl">
          <i class="iconfont icon-shu_shouqi ctrl-i" v-if="isExpand" @click="isExpand = !isExpand"></i>
          <i class="iconfont icon-shu_zhankai ctrl-i" v-else @click="isExpand = !isExpand"></i>
          <span><i class="iconfont icon-shu_yiji"></i> 事件列表</span>
          <i class="iconfont icon-xinzeng add-i" @click="handleAdd"></i>
        </div>
        <el-collapse-transition>
          <div class="event-list-content" v-show="isExpand">
            <div class="event-item" v-for="item in eventList" :class="{'isActive': item.isActive}" :key="item.id" @click="eventItemClick(item)">{{item.name}}</div>
          </div>
        </el-collapse-transition>
      </div>
    </div>-->
    <div class="top-container">
      <el-form class="znv-el-form" ref="form" :inline="true" :model="form">
        <el-form-item label="事件标题" style="margin-bottom: 0">
          <el-input clearable v-model="form.search" placeholder="请输入关键字"></el-input>
        </el-form-item>
        <!--<el-form-item label="事件类别" style="margin-bottom: 0">
          <el-select v-model="form.eventType" placeholder="" clearable>
            <el-option v-for="item in eventList" :key="item.id" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="事件类别" prop="typeSelect">
          <el-cascader
            v-model="form.typeSelect"
            :options="typeEmergencyList"
            :props="{ checkStrictly: true }" clearable></el-cascader>
        </el-form-item>
        <el-form-item label="上报人">
          <el-input
            clearable
            v-model="form.reportPeople"
            placeholder="请输入上报人"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-bottom: 0">
          <el-button type="primary" @click="onSubmit()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div class="emergency-container">
      <el-row class="znv-table-area">
        <header>
          <span class="title">查询表格</span>
          <span>
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">新增</el-button>
              <!--<el-button type="primary" icon="el-icon-plus" @click="handleAdd1">新增领导批示</el-button>-->
          </span>
        </header>
        <el-table :data="tableData" :height="670" v-loading="loading" highlight-current-row @current-change="handleChange">
          <el-table-column type="index" label="序号" width="50"></el-table-column>
          <el-table-column prop="status" label="事件状态" width="80"></el-table-column>
          <el-table-column prop="title" label="事发标题" width="160" show-overflow-tooltip></el-table-column>
          <el-table-column  label="事件类型" width="150">
            <template slot-scope="scope">
              <span>{{scope.row.type}}/{{scope.row.type2}}</span>
            </template>
          </el-table-column>
          <el-table-column prop="eventLevel"  label="事件等级" width="80"></el-table-column>
          <el-table-column prop="happenTime" label="事发时间" width="160"></el-table-column>
          <el-table-column prop="address" label="事发地点" width="190" show-overflow-tooltip></el-table-column>
          <el-table-column prop="details" label="事件描述" width="190" show-overflow-tooltip></el-table-column>
          <el-table-column prop="reportPeople" label="上报人" width="90"></el-table-column>
          <el-table-column prop="reportPeoplePhone" label="联系方式" width="120"></el-table-column>
          <el-table-column prop="handleUnitName" label="办理单位" width="100"></el-table-column>
          <el-table-column fixed="right" label="操作" width="160px">
            <template slot-scope="scope">
              <el-button @click="handleView(scope.row)" type="text" size="medium">详情</el-button>
              <el-button @click="handleEdit(scope.row)" type="text" size="medium" v-if="scope.row.dealResult != 3 && scope.row.dealResult != 4">编辑</el-button>
              <el-button @click="handleAdd2(scope.row)" type="text" size="medium" v-if="scope.row.dealResult != 3 && scope.row.dealResult != 4">续报</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          @current-change="handleCurrentChange"
          :current-page.sync="pageStart"
          :page-size.sync="pageLen"
          layout="total, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-row>
    </div>

    <LeadershipInstructionsAdd :dialogVisible.sync="showAdd1" :rowInfo="rowInfo" @success="handleSuccess"/>
    <EventsProgressAdd :dialogVisible.sync="showAdd2" :rowInfo="rowInfo" @success="handleSuccess"/>
    <EmergencyManagementAdd :dialogVisible.sync="showAdd" @success="handleSuccess"/>
    <EmergencyManagementEdit :dialogVisible.sync="showEdit" :eventId="eventId" @success="handleSuccess"/>
    <EmergencyManagementView :dialogVisible.sync="showView" :eventId="eventId"/>
  </div>
</template>

<script>
// 突发事件管理
import Axios from "axios";
import LeadershipInstructionsAdd from "./LeadershipInstructionsAdd";
import EventsProgressAdd from "./EventsProgressAdd";
import EmergencyManagementAdd from "./EmergencyManagementAdd";
import EmergencyManagementEdit from "./EmergencyManagementEdit";
import EmergencyManagementView from "./EmergencyManagementView";

export default {
  name: "EmergencyManagement",
  components: {
    EmergencyManagementView,
    EmergencyManagementEdit,
    EmergencyManagementAdd,
    EventsProgressAdd,
    LeadershipInstructionsAdd
  },
  data() {
    return {
      searchInput: '',
      isExpand: true,
      eventList0: [
        {
          id: '1',
          name: '自然灾害事件',
          isActive: false
        },
        {
          id: '2',
          name: '事故灾害事件',
          isActive: false
        },
        {
          id: '3',
          name: '公共卫生事件',
          isActive: true
        },
        {
          id: '4',
          name: '社会安全事件',
          isActive: false
        },
        {
          id: '5',
          name: '防汛抗旱事件',
          isActive: false
        },
        {
          id: '6',
          name: '森林草原灾害事件',
          isActive: false
        },
        {
          id: '7',
          name: '气象灾害事件',
          isActive: false
        },
        {
          id: '8',
          name: '地震灾害事件',
          isActive: false
        },
        {
          id: '9',
          name: '非煤矿山事故事件',
          isActive: false
        },
        {
          id: '10',
          name: '药品安全事件',
          isActive: false
        },
        {
          id: '11',
          name: '恐怖袭击事件',
          isActive: false
        }
      ],
      eventList: [],
      selectEventItem: {},
      form: {
        search: '',
        eventType: null,
        typeSelect: [],
        reportPeople: ''
      },
      pageStart: 1,
      pageLen: 10,
      total: 0,
      tableData: [{}],
      loading: false,
      showAdd: false, // 新增事件
      showEdit: false,
      showView: false,
      showAdd1: false, // 新增领导批示
      showAdd2: false, // 新增事件进展
      eventId: '',
      typeEmergencyList: [],
      rowInfo: null
    }
  },
  mounted() {
    this.getTypeList()
    // this.getEventTypeList()
    this.getTableData()
  },
  methods: {
    getTypeList() {
      let url = '/gtw/EIMS-management/event/queryEventType'
      Axios.get(url).then(r => {
        let data = r.data.data || []
        this.typeEmergencyList = this.changeTree(data)
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
    getEventTypeList() {
      this.eventList = []
      let url = '/gtw/EIMS-management/event/queryEventType2'
      Axios.get(url).then(res => {
        let data = res.data.data || []
        data.forEach((item, index) => {
          let eventItem = {
            id: index,
            name: item,
            isActive: index === 0
          }
          if (index === 0) this.selectEventItem = eventItem
          this.eventList.push(eventItem)
        })
        this.getTableData()
      })
    },
    eventItemClick(eventItem) {
      this.eventList.forEach(item => {
        item.isActive = false
      })
      eventItem.isActive = true
      this.selectEventItem = eventItem
      this.getTableData()
    },
    onSubmit() {
      this.total = 0
      this.pageStart = 1
      this.getTableData()
    },
    handleCurrentChange(val) {
      this.getTableData()
    },
    handleChange(val) {
      this.rowInfo = JSON.parse(JSON.stringify(val))
    },
    getTableData() {
      this.tableData = []
      this.loading = true
      let url = '/gtw/EIMS-management/event/queryEventList?pageStart=' + this.pageStart + '&pageLen=' + this.pageLen
      if (this.form.search) {
        url = url + '&text=' + this.form.search
      }
      if (this.form.typeSelect.length > 0) {
        url = url + '&eventType=' + this.form.typeSelect[0]
      }
      if (this.form.typeSelect.length > 1) {
        url = url + '&eventType2=' + this.form.typeSelect[1]
      }
      // 上报人 参数
      if (this.form.reportPeople) {
        url = url + '&reportPeople=' + this.form.reportPeople
      }
      Axios.get(url).then(res => {
        this.loading = false
        let data = res.data.data.list || []
        this.total = res.data.data.total
        this.tableData = data
      }).catch(() => {
        this.loading = false
      })
    },
    reset() {
      this.form.search = ''
      this.$refs['form'].resetFields()
    },
    handleView(row) {
      this.showView = true
      this.eventId = row.id
    },
    handleAdd() {
      this.showAdd = true
      this.eventId = ''
    },
    handleSuccess() {
      this.pageStart = 1
      this.getTableData()
    },
    handleEdit(row) {
      this.showEdit = true
      this.eventId = row.id
    },
    handleAdd1() {
      if (this.rowInfo) {
        this.showAdd1 = true
      } else {
        this.$message.warning('请先选择一条事件，再进行新增领导指示！')
      }
    },
    handleAdd2(row) {
      this.showAdd2 = true
    }
  }
}
</script>

<style lang="scss">
.EmergencyManagement {
  position: relative;

  .left-container {
    position: relative;
    float: left;
    width: 270px;
    background-color: #fff;
    height: 100%;

    .search-box {
      position: relative;
      padding: 20px 22px;
    }

    .event-list-ctrl {
      position: relative;
      margin-left: 22px;
      margin-top: 20px;
      color: #0093FF;

      i {
        font-size: 20px;
        cursor: pointer;
      }

      .add-i {
        float: right;
        margin-right: 22px;
      }
    }

    .event-list-content {
      position: relative;
      margin-top: 15px;

      .event-item {
        position: relative;
        line-height: 44px;
        height: 44px;
        cursor: pointer;
        text-indent: 80px;

        &.isActive {
          color: #249BFF;
          background-color: #E4F4FF;
        }

        &:hover {
          color: #249BFF;
        }
      }
    }
  }

  .top-container {
    position: relative;
    float: left;
    width: 100%;
    background-color: #fff;
    height: 76px;
    margin-bottom: 20px;
  }

  .emergency-container {
    position: relative;
    float: left;
    width: 100%;
    background-color: #fff;
    height: calc(100% - 96px);
  }
}
</style>
