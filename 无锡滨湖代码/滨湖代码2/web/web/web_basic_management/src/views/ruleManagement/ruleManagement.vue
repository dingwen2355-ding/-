<template>
  <div class="ruleManagement">
    <!-- 面包屑 -->

    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item>事件监管</el-breadcrumb-item>
      <el-breadcrumb-item>超期提醒</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 消息框 -->
    <el-col :span="24" class="descriptions">
      <div class="descriptionsBox" v-for="(item, index) in descriptionsList" :key="index">
        <div class="tips">
          <span>提示：</span>
          {{ item.name }}
        </div>
        <el-descriptions :column="4" :contentStyle="CS" :label-style="LS">
          <el-descriptions-item label="预警名称">{{ item.name }}</el-descriptions-item>
          <el-descriptions-item label="预警提醒" :span="3">{{ item.remindTemplate }}</el-descriptions-item>
          <el-descriptions-item label="触发频率">
            <div style="margin-top: -5px">
              提前
              <span class="day">
                <el-input v-if="item.isShow" v-model="item.threshold"></el-input>
                <span v-else>{{ item.threshold }}</span>
              </span>天
              <el-button v-if="item.isShow" type="primary" @click="searchData(item)" style="margin-left: 5px">确定</el-button>
              <el-button v-else type="primary" @click="handleSearchData(item)" style="margin-left: 5px">编辑</el-button>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="规则状态">
            <span>
              <el-switch
                v-model="item.rulesState"
                @change="handleSwitch(item)"
                active-color="#1989FA"
                inactive-color="#ff4949"
              ></el-switch>
            </span>
          </el-descriptions-item>
          <el-descriptions-item label="修改人员">{{ item.updatePerson }}</el-descriptions-item>
          <el-descriptions-item label="修改时间">{{ item.updateTime }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-col>
  </div>
</template>

<script>
import axios from '@/utils/request'
// import qs from 'qs'
export default {
  name: 'PopulationTable',
  components: {},
  data() {
    return {
      CS: {
        'text-align': 'center',
        'min-width': '250px',
        'word-break': 'break-all',
        'font-size': '18px',
        'margin-top': '20px'
      },
      LS: {
        'text-align': 'center',
        'font-size': '18px',
        'font-weight': '600',
        'margin-top': '20px',
        'padding-left': '40px',
        'min-width': '110px',
        'word-break': 'keep-all'
      },
      rulesState: true,
      descriptionsList: [],
      tableData: []
    }
  },
  computed: {},
  watch: {},
  mounted() {
    this.getInformation()
  },
  methods: {
    getInformation() {
      let url = '/binhuapis/alarmInstall/selAlarmInstall'
      axios.get(url).then((r) => {
        if (r.data.code === 200) {
          this.descriptionsList =
            r.data.data.map((e) => {
              e.isShow = false
              return e
            }) || []
        }
      })
    },
    handleSearchData(item) {
      this.descriptionsList.forEach((v) => {
        if (item.id === v.id) {
          v.isShow = true
        }
      })
    },
    searchData(item) {
      if (item.threshold) {
        let url = ''
        url = '/binhuapis/alarmInstall/updateAlarmInstall?id=' + item.id + '&threshold=' + item.threshold
        axios.post(url).then((r) => {
          if (r.data.code === 200) {
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 1000
            })
            this.getInformation()
          } else {
            this.$notify.error({
              title: '错误',
              message: r.data.message,
              duration: 1000
            })
          }
        })
      } else {
        this.$notify({
          title: '警告',
          message: '请填写天数！',
          type: 'warning',
          duration: 1000
        })
      }
    },
    handleSwitch(item) {
      let url = ''
      url = '/binhuapis/alarmInstall/updateAlarmInstall?id=' + item.id + '&rulesState=' + item.rulesState
      axios.post(url).then((r) => {
        if (r.data.code === 200) {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success',
            duration: 1000
          })
          this.getInformation()
        } else {
          this.$notify.error({
            title: '错误',
            message: r.data.message,
            duration: 1000
          })
        }
      })
    }
  }
}
</script>
<style lang="scss">
.ruleManagement {
  width: 100%;
  height: 100%;
  .descriptions {
    height: calc(100% - 50px);
    overflow-y: auto;
    .descriptionsBox {
      margin-top: 10px;
      .day {
        .el-input {
          width: 60px;
        }
      }
      .tips {
        height: 50px;
        line-height: 50px;
        background: #fbd5a2;
        color: #f59a48;
        padding-left: 10px;
      }
    }
  }
}
</style>
