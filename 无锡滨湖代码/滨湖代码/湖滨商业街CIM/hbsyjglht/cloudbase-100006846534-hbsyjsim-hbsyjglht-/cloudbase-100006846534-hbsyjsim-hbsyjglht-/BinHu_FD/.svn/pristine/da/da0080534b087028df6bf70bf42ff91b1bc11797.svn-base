<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="4" :sm="8">
            <a-form-item label="房间编号">
              <a-input placeholder="请输入编号" v-model="queryParam.room"></a-input>
            </a-form-item>
          </a-col>
          <!-- <a-col :md="4" :sm="8">
            <a-form-item label="设备类型">
              <j-dict-select-tag
                placeholder="请选择"
                dictCode="td_type,name,id,type='2'"
                v-model="queryParam.deviceType"
              />
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="设备系统">
              <j-dict-select-tag
                placeholder="请选择"
                dictCode="td_type,name,id,type='1'"
                v-model="queryParam.systemType"
              />
            </a-form-item>
          </a-col> -->
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
    <!--    <div class="table-operator">-->
    <!--      <a-button @click="handleAdd" v-show="show" type="primary" icon="plus">新增</a-button>-->
    <!--      <a-button type="primary" v-show="show" icon="download" @click="handleExportXls('消息')">导出</a-button>-->
    <!--      <a-upload v-show="show" name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"-->
    <!--                @change="handleImportExcel">-->
    <!--        <a-button type="primary" icon="import">导入</a-button>-->
    <!--      </a-upload>-->
    <!--      <a-dropdown v-if="selectedRowKeys.length > 0">-->
    <!--        <a-menu slot="overlay">-->
    <!--          <a-menu-item key="1" @click="batchDel">-->
    <!--            <a-icon type="delete"/>-->
    <!--            删除-->
    <!--          </a-menu-item>-->
    <!--        </a-menu>-->
    <!--        <a-button style="margin-left: 8px"> 批量操作-->
    <!--          <a-icon type="down"/>-->
    <!--        </a-button>-->
    <!--      </a-dropdown>-->
    <!--    </div>-->

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
          <a-tag :color="record.status ? 'red' : 'grey'">{{ record.status | status }}</a-tag>
        </span>
        <span slot="action" slot-scope="text, record">
          <a-switch
            v-model="record.status"
            v-if="record.function === 'EIS1'"
            checked-children="开"
            un-checked-children="关"
            :default-checked="false"
            @click="(value) => onSwitchMode(value, record)"
          />
          <a-dropdown v-if="record.function === 'EIS61'">
            <a-menu slot="overlay" @click="(value) => handleChangePattern(value, record)">
              <a-menu-item key="1"> 制冷 </a-menu-item>
              <a-menu-item key="2"> 除湿 </a-menu-item>
              <a-menu-item key="3"> 送风 </a-menu-item>
              <a-menu-item key="4"> 制热 </a-menu-item>
            </a-menu>
            <a-button type="primary" size="small"> 空调模式 <a-icon type="down" /> </a-button>
          </a-dropdown>
          <a-dropdown v-if="record.function === 'EIS62'">
            <a-menu slot="overlay" @click="(value) => handleChangeSpeed(value, record)">
              <a-menu-item key="1"> 低 </a-menu-item>
              <a-menu-item key="4"> 中 </a-menu-item>
              <a-menu-item key="6"> 高 </a-menu-item>
              <a-menu-item key="10"> 自动 </a-menu-item>
            </a-menu>
            <a-button type="primary" size="small"> 风速模式 <a-icon type="down" /> </a-button>
          </a-dropdown>
          <a-button v-if="record.function === 'EIS5'" type="primary" size="small" @click="onChangeTemp(record)">
            温度调节
          </a-button>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <temperature-regulation-modal ref="tempModalForm"></temperature-regulation-modal>
  </a-card>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
import TemperatureRegulationModal from './modules/TemperatureRegulationModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'

export default {
  name: 'AirConditionerControl',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    JEllipsis,
    JDictSelectTag,
    TemperatureRegulationModal,
  },
  created() {},
  mounted() {},
  watch: {},
  filters: {
    status(val) {
      if (val) {
        return '在线'
      }
      return '离线'
    },
  },
  data() {
    return {
      description: '空调列表页面',
      // 表头
      columns: [
        // {
        //   title: '设备编码',
        //   dataIndex: 'indexCode',
        //   key: 'indexCode',
        //   // width: 320,
        //   align: 'center',
        // },
        {
          title: '名称',
          dataIndex: 'relname',
          align: 'center',
        },
        {
          title: '模式',
          align: 'center',
          dataIndex: 'function',
        },
        {
          title: '开关地址',
          align: 'center',
          dataIndex: 'switchAddress',
        },
        {
          title: '状态地址',
          align: 'center',
          dataIndex: 'statusAddress',
        },
        {
          title: '房间编号',
          align: 'center',
          dataIndex: 'room',
        },
        {
          title: '设备状态',
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
      url: {
        list: '/device/smartHome/page',
        // delete: "/message/sysMessage/delete",
        // deleteBatch: "/message/sysMessage/deleteBatch",
        // exportXlsUrl: "message/sysMessage/exportXls",
        // importExcelUrl: "message/sysMessage/importExcel",
      },
      queryParam: {
        type: '1',
      },
      /* 排序参数 */
      isorter: {
        column: 'room',
        order: 'desc',
      },
      kpi: {
        sn: 'BC00D076508230F3',
        cmd: 'control',
        data: '',
        room: '',
        val: '',
      },
      showDrawer: false,
    }
  },
  methods: {
    handleChangePattern(value, record) {
      const kpi = this.kpi
      kpi.data = record.function
      kpi.room = record.room
      kpi.val = value.key
      postAction('/device/smartHome/kpi', kpi).then((res) => {})
    },
    handleChangeSpeed(value, record) {
      const kpi = this.kpi
      kpi.data = record.function
      kpi.room = record.room
      kpi.val = value.key
      postAction('/device/smartHome/kpi', kpi).then((res) => {})
    },
    onChangeTemp(value) {
      this.$refs.tempModalForm.edit(value)
      this.$refs.tempModalForm.title = '温度调节'
    },
    onSwitchMode(value, record) {
      const kpi = this.kpi
      kpi.data = record.function
      kpi.room = record.room
      kpi.val = value ? '1' : '0'
      postAction('/device/smartHome/kpi', kpi).then((res) => {})
    },
    websocketOnmessage(event) {
      const message = JSON.parse(event.data)
      this.dataSource.map((item) => {
        if (item.room === message.room) {
          item.status = message.val === '开启'
          return item
        }
      })
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
