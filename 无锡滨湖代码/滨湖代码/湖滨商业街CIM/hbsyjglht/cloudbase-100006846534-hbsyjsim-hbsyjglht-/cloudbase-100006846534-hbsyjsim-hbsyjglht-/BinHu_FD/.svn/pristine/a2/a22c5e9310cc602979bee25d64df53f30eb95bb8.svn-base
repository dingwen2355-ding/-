<template>
  <div>
    <a-card :bordered="false">
      <!-- <div class="meta-card-info">
        <a-icon type="warning" style="margin-bottom: 2px" />
        <div style="margin-left: 10px">今日预警：200次</div>
        <div style="margin-left: 20px">累计预警：3200次</div>
      </div> -->
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <!-- <a-col :md="4" :sm="8">
            <a-form-item label="提交时间">、
              <a-date-picker
                v-model="queryParam.createTime"
                placeholder="请选择日期"
                format="YYYY-MM-DD HH:mm:ss"
                valueFormat="YYYY-MM-DD HH:mm:ss"
                :show-time="{ defaultValue: moment('00:00:00', 'HH:mm:ss') }"
              ></a-date-picker>
            </a-form-item>
          </a-col> -->
            <!-- <a-col :md="4" :sm="8">
              <a-form-item label="工单类型">
                <j-dict-select-tag placeholder="请选择" v-model="queryParam.orgCode" />
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="8">
              <a-form-item label="预警来源">
                <j-dict-select-tag placeholder="请选择" v-model="queryParam.orgCode" />
              </a-form-item>
            </a-col> -->
            <a-col :md="4" :sm="8">
              <a-form-item label="工单状态">
                <a-select v-model="queryParam.status" placeholder="请选择状态">
                  <a-select-option value="0">待处理</a-select-option>
                  <a-select-option value="1">已处理</a-select-option>
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
      <!-- <div class="table-operator" style="margin-top: 5px">
        <a-button type="primary" icon="download" @click="handleExportXls('工单信息')">导出</a-button>

        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /> </a-button>
        </a-dropdown>
      </div> -->

      <!-- table区域-begin -->
      <div>
        <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
          <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
          <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
          >项
          <a style="margin-left: 24px" @click="onClearSelected">清空</a>
        </div>

        <a-table
          ref="table"
          size="middle"
          bordered
          rowKey="id"
          :columns="columns"
          :dataSource="dataSource"
          :pagination="ipagination"
          :loading="loading"
          :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
          @change="handleTableChange"
        >
          <span slot="status" slot-scope="text, record">
            <a-tag :color="record.status === 1 ? 'green' : 'grey'">{{ record.status | status }}</a-tag>
          </span>
          <span slot="action" slot-scope="text, record">
            <a-button type="primary" size="small" style="margin-right:5px" @click="handleProcess(record,'处理')">处理</a-button>
            <a-button type="primary" size="small" style="margin-right:5px" @click="handleProcess(record,'上报')">上报</a-button>
            <a-button type="primary" size="small" style="margin-right:5px" @click="handleProcess(record,'移交')">移交</a-button>
           <!--  <a-button type="primary" size="small" @click="handleProcess(record,'下发')">下发</a-button> -->
          </span>
        </a-table>
      </div>
      <!-- table区域-end -->

      <!-- 表单区域 -->
      <my-alarm-processing-modal ref="modalForm" @ok="modalFormOk"></my-alarm-processing-modal>
    </a-card>
  </div>
</template>

<script>
import MyAlarmProcessingModal from './modules/MyAlarmProcessingModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import moment from 'moment'

export default {
  name: 'MyUnifiedAlarmHandling',
  mixins: [JeecgListMixin],
  components: {
    JDictSelectTag,
    // JDate,
    MyAlarmProcessingModal,
  },

  filters: {
    status(val) {
      if (val === 1) {
        return '已处理'
      }
      if (val === 0) {
        return '待处理'
      }
    },
  },
  data() {
    return {
      description: '统一报警处理页面',
      // 表头
      columns: [
        {
          title: '设备编码',
          dataIndex: 'indexCode',
          // key: 'rowIndex',
          align: 'center',
        },
        {
          title: '问题描述',
          align: 'center',
          dataIndex: 'description',
        },
        {
          title: '报修位置',
          align: 'center',
          dataIndex: 'location',
        },
        {
          title: '工单类型',
          align: 'center',
          dataIndex: 'type',
        },
        {
          title: '预警来源',
          align: 'center',
          dataIndex: 'source',
        },
        {
          title: '提交人',
          align: 'center',
          dataIndex: 'tjr',
        },
        {
          title: '提交日期',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '状态',
          align: 'center',
          dataIndex: 'status',
          scopedSlots: { customRender: 'status' },
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      // 模拟数据
      // data: [
      //   {
      //     id: '001',
      //     description: '烟感预警',
      //     location: '食堂',
      //     type: '消防报警',
      //     source: '消防物联网系统',
      //     createTime: '2022-08-25 12:00',
      //     status: '待处理',
      //   },
      //   {
      //     id: '002',
      //     description: '012出入防区窃警',
      //     location: '办公楼27层',
      //     type: '安防入侵告警',
      //     source: '安防报警系统',
      //     createTime: '2022-08-25 12:00',
      //     status: '待处理',
      //   },
      //   {
      //     id: '003',
      //     description: 'C195摄像头离线',
      //     location: '办公楼28层',
      //     type: '设备工况告警',
      //     source: '安防监控系统',
      //     createTime: '2022-08-25 12:00',
      //     status: '已处理',
      //   },
      // ],
      url: {
        list: '/alarm/myHandleAlarm',
      },
    }
  },
  methods: {
    moment,
    handleProcess(record,type) {
      this.$refs.modalForm.edit(record)
      this.$refs.modalForm.type=type
      this.$refs.modalForm.title =type //'工单处理'
    },
    // onRangeDateChange(value) {
    //   console.log(value)
    //   console.log(this.queryParam)
    //   this.queryParam = value
    //     ? {
    //         ...this.queryParam,
    //         createTime_begin: value[0],
    //         createTime_end: value[1],
    //       }
    //     : {
    //         ...this.queryParam,
    //         createTime_begin: null,
    //         createTime_end: null,
    //       }
    // },
  },
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';

// .meta-card-info {
//   display: flex;
//   align-items: center;
//   margin-bottom: 20px;

//   > div {
//     display: flex;
//     align-items: center;
//     // font-size: 18px;
//     font-weight: 600;
//     margin-left: 20px;
//   }
// }
</style>
