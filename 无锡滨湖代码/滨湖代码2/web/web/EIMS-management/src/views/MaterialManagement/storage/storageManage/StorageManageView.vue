<template>
  <el-dialog
    title="物资仓库详情"
    :visible.sync="dialogVisible"
    width="1200"
    :modal-append-to-body="false"
    :append-to-body="false"
    :before-close="handleClose"
  >
    <div class="storage-detail">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="物资仓库基本信息" name="first">
          <div class="container">
            <div class="list">
              <div class="item" v-for="(v, i) in list" :key="i">
                <span class="name">{{ v.name }}: </span>
                <span class="value">{{ v.value }}</span>
              </div>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="储存物资信息" name="second">
          <el-table :data="tableData" height="500">
            <el-table-column prop="stuffName" label="物资名称"></el-table-column>
            <el-table-column prop="stuffUnit" label="计量单位"></el-table-column>
            <el-table-column prop="stuffNumber" label="数量"></el-table-column>
            <el-table-column prop="isSpecial" label="是否特种物资"></el-table-column>
          </el-table>
          <!-- <div class="data-title">共<span class="blue">{{ total }}</span>条数据 | 最近更新时间：{{ updateTime }}</div> -->
          <el-pagination
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            layout="prev, pager, next, jumper"
            :total="total"
          >
          </el-pagination>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-dialog>
</template>

<script>
import axios from 'axios'
import moment from 'moment'
import qs from 'qs'

export default {
  props: ['dialogVisible', 'storageInfo'],
  watch: {
    dialogVisible(v) {
      this.dialogVisible = v
      this.query()
      if (v) {
        this.list.forEach((v) => {
          for (let key in this.storageInfo) {
            if (v.key === key) {
              v.value = this.storageInfo[key]
              return
            }
          }
        })
      }
    }
  },
  data() {
    return {
      activeName: 'first',
      tableData: [],
      currentPage: 1,
      total: 0,
      pageSize: 10,
      updateTime: moment().format('YYYY-MM-DD HH:mm:ss'),
      list: [
        {
          name: '仓库名称',
          key: 'stockName',
          value: ''
        },
        {
          name: '所属辖区',
          key: 'region',
          value: ''
        },
        {
          name: '管理单位',
          key: 'belongUnit',
          value: ''
        },
        {
          name: '管理类型',
          key: 'beManagedType',
          value: ''
        },
        {
          name: '管理级别',
          key: 'storageLevel',
          value: ''
        },
        {
          name: '存储类型',
          key: 'storageType',
          value: ''
        },
        // {
        //   name: '所属行业',
        //   key: 'industry',
        //   value: ''
        // },
        {
          name: '救援类别',
          key: 'helpType',
          value: ''
        },
        {
          name: '详细地址',
          key: 'address',
          value: ''
        },
        {
          name: '经度',
          key: 'longitude',
          value: ''
        },
        {
          name: '纬度',
          key: 'latitude',
          value: ''
        },
        {
          name: '仓库类型',
          key: 'stockType',
          value: ''
        },
        {
          name: '主要责任人',
          key: 'manager',
          value: ''
        },
        {
          name: '联系电话',
          key: 'managerPhone',
          value: ''
        },
        {
          name: '值班人',
          key: 'mainPeople',
          value: ''
        },
        {
          name: '值班电话',
          key: 'dutyPhone',
          value: ''
        },
        {
          name: '队员人数',
          key: 'memberNumber',
          value: ''
        },
        {
          name: '仓库介绍',
          key: 'introduction',
          value: ''
        }
        // {
        //   name: '物资规格',
        //   key: 'model',
        //   value: ''
        // }, {
        //   name: '物资品牌',
        //   key: 'brand',
        //   value: ''
        // }
      ]
    }
  },
  methods: {
    handleClose(done) {
      this.$emit('update:dialogVisible', false)
    },
    reset() {
      this.currentPage = 0
    },
    handleClick(tab, event) {
      // console.log(tab, event)
    },
    query(param = {}) {
      let data = {
        ...param,
        ...{
          id: this.storageInfo.id,
          pageStart: this.currentPage,
          pageLen: this.pageSize
        }
      }
      let url = '/gtw/EIMS-management/stockInfoData/getStuffInfoList?' + qs.stringify(data, { encode: false })
      axios.get(url).then((r) => {
        let data = r.data.data
        this.tableData = data.data
        this.total = data.count
      })
    },
    handleCurrentChange(val) {
      this.query()
    }
  }
}
</script>
<style lang="scss">
.storage-detail {
  .container {
    height: 500px;
    overflow: auto;

    .list {
      line-height: 3;
      display: flex;
      flex-wrap: wrap;

      .item {
        width: 50%;

        .name {
          width: 100px;
          color: #8b93a0;
        }

        .value {
          color: #4e5a6e;
        }
      }
    }
  }

  .data-title {
    float: left;
    margin-top: 20px;
  }
}
</style>
