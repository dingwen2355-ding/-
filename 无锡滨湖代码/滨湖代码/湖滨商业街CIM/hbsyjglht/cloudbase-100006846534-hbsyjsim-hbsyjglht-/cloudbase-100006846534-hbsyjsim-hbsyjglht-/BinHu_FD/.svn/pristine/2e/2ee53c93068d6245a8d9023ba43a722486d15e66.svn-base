<template>
  <div>
    <a-card :bordered="false">
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
            <a-col :md="4" :sm="8">
              <a-form-item label="分配预案">
                <j-dict-select-tag placeholder="请选择" dictCode="td_pre_plan,title,id" v-model="queryParam.plan" />
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
      <div class="table-operator" style="margin-top: 5px">
        <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
        <!-- <a-button type="primary" icon="download" @click="handleExportXls('工单信息')">导出</a-button> -->

        <!-- <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /> </a-button>
        </a-dropdown> -->
      </div>

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
          <span slot="action" slot-scope="text, record">
            <a href="javascript:;" @click="handleEdit(record)">编辑</a>
          </span>
        </a-table>
      </div>
      <!-- table区域-end -->

      <!-- 表单区域 -->
      <emergency-processing-modal ref="modalForm" @ok="modalFormOk"></emergency-processing-modal>
    </a-card>
  </div>
</template>

<script>
import EmergencyProcessingModal from './modules/EmergencyProcessingModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
// import moment from 'moment'

export default {
  name: 'EmergencyProcessing',
  mixins: [JeecgListMixin],
  components: {
    JDictSelectTag,
    // JDate,
    EmergencyProcessingModal,
  },
  data() {
    return {
      description: '应急处理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: 'index',
          key: 'index',
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '值班人员',
          align: 'center',
          dataIndex: 'username',
        },
        {
          title: '分配预案',
          align: 'center',
          dataIndex: 'plan_dictText',
        },
        // {
        //   title: '详细预案',
        //   align: 'center',
        //   dataIndex: 'detail',
        // },
        {
          title: '分配时间',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/alarm/emergencyProcessing/list',
      },
    }
  },
  methods: {
    // moment,
  },
}
</script>
<style lang="less" scoped>
@import '~@assets/less/common.less';
</style>
