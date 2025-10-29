<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="4" :sm="8">
            <a-form-item label="楼层">
              <a-select v-model="queryParam.position" placeholder="请选择" allowClear>
                <a-select-option value="27"> 27层 </a-select-option>
                <a-select-option value="28"> 28层 </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleEdit" type="primary" icon="edit">警戒值配置</a-button>
    </div>

    <!-- table区域-begin -->
    <div>
      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="indexCode"
        :columns="columns"
        :dataSource="dataSource"
        :loading="loading"
        :pagination="false"
      >
        <span slot="today" slot-scope="text, record">
          <div v-if="record.today">
            <span style="margin-right: 20px">一氧化碳：{{ record.today.co }}</span>
            <span style="margin-right: 20px">温度：{{ record.today.temp }}</span>
            <span style="margin-right: 20px">pm2.5：{{ record.today.pm25 }}</span>
            <span>pm10：{{ record.today.pm10 }}</span>
          </div>
        </span>
        <span slot="yesterday" slot-scope="text, record">
          <div v-if="record.yesterday">
            <span style="margin-right: 20px">一氧化碳：{{ record.yesterday.co }}</span>
            <span style="margin-right: 20px">温度：{{ record.yesterday.temp }}</span>
            <span style="margin-right: 20px">pm2.5：{{ record.yesterday.pm25 }}</span>
            <span>pm10：{{ record.yesterday.pm10 }}</span>
          </div>
        </span>
        <span slot="week" slot-scope="text, record">
          <div v-if="record.week">
            <span style="margin-right: 20px">一氧化碳：{{ record.week.co }}</span>
            <span style="margin-right: 20px">温度：{{ record.week.temp }}</span>
            <span style="margin-right: 20px">pm2.5：{{ record.week.pm25 }}</span>
            <span>pm10：{{ record.week.pm10 }}</span>
          </div>
        </span>
        <span slot="alarm" slot-scope="text, record">
          <a-tag :color="record.alarm ? 'red' : 'grey'">{{ record.alarm | alarm }}</a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <a-button type="primary" size="small" @click="handleDetail(record)">详情</a-button>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <!-- <device-modal ref="deviceModalForm"></device-modal> -->
    <environment-modal ref="environmentModalForm"></environment-modal>
  </a-card>
</template>

<script>
import { getAction } from '@/api/manage'
// import DeviceModal from './modules/DeviceModal'
import EnvironmentModal from './modules/EnvironmentModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'

export default {
  name: 'DeviceList',
  mixins: [JeecgListMixin],
  components: {
    JEllipsis,
    JDictSelectTag,
    // DeviceModal,
    EnvironmentModal,
  },
  created() {},
  mounted() {},
  watch: {},
  filters: {
    alarm(val) {
      return val ? '是' : '否'
    },
  },
  data() {
    return {
      description: '环境监控页面',
      // 表头
      columns: [
        {
          title: '设备编码',
          dataIndex: 'indexCode',
          key: 'indexCode',
          // width: 320,
          align: 'center',
        },
        {
          title: '设备名称',
          align: 'center',
          dataIndex: 'name',
        },
        // {
        //   title: '系统名称',
        //   align: 'center',
        //   dataIndex: 'systemType',
        // },
        {
          title: '设备类型',
          dataIndex: 'deviceType',
          align: 'center',
        },
        {
          title: '楼层',
          align: 'center',
          dataIndex: 'position',
        },
        {
          title: '今日警戒最高值',
          align: 'center',
          dataIndex: 'today',
          scopedSlots: { customRender: 'today' },
        },
        {
          title: '昨日警戒最高值',
          align: 'center',
          dataIndex: 'yesterday',
          scopedSlots: { customRender: 'yesterday' },
        },
        {
          title: '七日警戒最高值',
          align: 'center',
          dataIndex: 'week',
          scopedSlots: { customRender: 'week' },
        },
        {
          title: '阈值报警',
          align: 'center',
          dataIndex: 'alarm',
          scopedSlots: { customRender: 'alarm' },
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/monitor/environment/list',
        // delete: "/message/sysMessage/delete",
        // deleteBatch: "/message/sysMessage/deleteBatch",
        // exportXlsUrl: "message/sysMessage/exportXls",
        // importExcelUrl: "message/sysMessage/importExcel",
      },
    }
  },
  methods: {
    loadData() {
      const params = this.getQueryParams()
      this.loading = true
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          this.dataSource = res.result
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    handleDetail(record) {
      getAction('/monitor/queryByIndexCode', {
        indexCode: record.indexCode,
      }).then((res) => {
        if (res.success) {
          this.$refs.environmentModalForm.edit(res.result)
          this.$refs.environmentModalForm.title = '详情'
          this.$refs.environmentModalForm.disableSubmit = true
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
      })
    },
    handleEdit(record) {
      this.$refs.environmentModalForm.edit(record)
      this.$refs.environmentModalForm.title = '警戒值配置'
    },
  },
}
</script>
<style lang="less" scoped>
/** Button按钮间距 */
.ant-btn {
  margin-left: 3px;
}

.ant-table-tbody .ant-table-row td {
  padding-top: 15px;
  padding-bottom: 15px;
}

.anty-row-operator button {
  margin: 0 5px;
}

.ant-btn-danger {
  background-color: #ffffff;
}

.ant-modal-cust-warp {
  height: 100%;
}

.ant-modal-cust-warp .ant-modal-body {
  height: calc(100% - 110px) !important;
  overflow-y: auto;
}

.ant-modal-cust-warp .ant-modal-content {
  height: 90% !important;
  overflow-y: hidden;
}
</style>
