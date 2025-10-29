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
            <a-form-item label="设备编码">
              <a-input placeholder="请输入编码" v-model="queryParam.indexCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
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
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 进出记录抽屉区域 -->
    <!-- <access-list-drawer :show-drawer="showDrawer" @close="onCloseDrawer"></access-list-drawer> -->
    <!-- 表单区域 -->
    <!-- <device-modal ref="deviceModalForm"></device-modal> -->
    <!-- <environment-modal ref="environmentModalForm"></environment-modal> -->
  </a-card>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
// import DeviceModal from './modules/DeviceModal'
// import EnvironmentModal from './modules/EnvironmentModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'
// import AccessListDrawer from '../alarm/modules/AccessListDrawer'

export default {
  name: 'LightControl',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    JEllipsis,
    JDictSelectTag,
    // AccessListDrawer,
    // DeviceModal,
    // EnvironmentModal,
  },
  created() {},
  mounted() {},
  watch: {
    // $route(to, from) {
    //   //监听路由是否变化
    //   if (to.params.deviceType != from.params.deviceType) {
    //     //变化后的操作
    //     this.queryParam.deviceType = to.params.deviceType
    //     //重新加载数据
    //     this.loadData()
    //   }
    // },
  },
  // activated() {
  //   if (this.$route.params.deviceType) {
  //     this.queryParam.deviceType = this.$route.params.deviceType
  //     this.loadData()
  //   }
  //   if (this.$route.params.systemType) {
  //     this.queryParam.systemType = this.$route.params.systemType
  //     this.loadData()
  //   }
  // },
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
      description: '灯列表页面',
      // 表头
      columns: [
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
        type: '2',
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
    // handleDetail(record) {
    //   this.$refs.deviceModalForm.edit(record)
    //   this.$refs.deviceModalForm.title = '设备详情'
    // },
    // handlEenvironment(record){
    //   this.$refs.environmentModalForm.edit(record.environmentData)
    //   this.$refs.environmentModalForm.title = '环境数据'
    // },
    // handleAccess(record) {
    //   this.showDrawer = true
    //   console.log(record)
    // },
    // handleVideo(record) {},
    // onCloseDrawer() {
    //   this.showDrawer = false
    // },
    onSwitchMode(value, record) {
      const kpi = this.kpi
      kpi.data = record.function
      kpi.room = record.room
      kpi.val = value ? '1' : '0'
      postAction('/device/smartHome/kpi', kpi).then((res) => {})
    },
    websocketOnmessage(event) {
      const message = JSON.parse(event.data)
      // getAction('/device/smartHome/list/2').then((res) => {
      //   this.dataSource = res.result.map((item) => {
      //     if (item.room === message.room) {
      //       item.status = message.val === '开'
      //       return item
      //     }
      //   })
      // })
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
