<template>
  <a-card :bordered='false'>
    <!-- 查询区域 -->
    <div class='table-page-search-wrapper'>
      <a-form layout='inline' @keyup.enter.native='searchQuery'>
        <a-row :gutter='24'>
          <a-col :md='4' :sm='8'>
            <a-form-item label='企业名称'>
              <a-input placeholder='请输入企业名称' v-model='queryParam.qymc'></a-input>
            </a-form-item>
          </a-col>
          <a-col :md='4' :sm='8'>
            <a-form-item label='法人'>
              <a-input placeholder='请输入法人' v-model='queryParam.fddbrxm'></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="地址">
              <a-input placeholder="请输入地址" v-model="queryParam.jydz"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md='4' :sm='8'>
            <a-form-item label='经营类别'>
              <a-select placeholder='请选择' v-model="queryParam.sshySc">
                <a-select-option value=''>请选择</a-select-option>
                <a-select-option v-for='(item, key) in jylbOptions' :key='key' :value='item'>
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md='4' :sm='8'>
            <a-form-item label='状态'>
              <a-select v-model='queryParam.qyztSc' placeholder='请选择'>
                <a-select-option value=''>请选择</a-select-option>
                <a-select-option v-for='(item, key) in cxztOptions' :key='key' :value='item'>
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md='4' :sm='8'>
            <span style='float: left; overflow: hidden' class='table-page-search-submitButtons'>
              <a-button type='primary' @click='searchQuery' icon='search'>查询</a-button>
              <a-button type='primary' @click='searchReset' icon='reload' style='margin-left: 8px'>重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>


    <div class='table-operator' style='border-top: 5px'>
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">添加用户</a-button>-->
      <a-button type='primary' icon='download' @click="handleExportXls('企业信息')">导出</a-button>
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"-->
      <!--                @change="handleImportExcel">-->
      <!--        <a-button type="primary" icon="import">导入</a-button>-->
      <!--      </a-upload>-->
      <a-dropdown v-if='selectedRowKeys.length > 0'>
        <a-menu slot='overlay' @click='handleMenuClick'>
          <a-menu-item key='1'>
            <a-icon type='delete' @click='batchDel' />
            删除
          </a-menu-item>
        </a-menu>
        <a-button style='margin-left: 8px'>
          批量操作
          <a-icon type='down' />
        </a-button>
      </a-dropdown>
    </div>


    <!-- table区域-begin -->
    <div>
      <div class='ant-alert ant-alert-info' style='margin-bottom: 16px'>
        <i class='anticon anticon-info-circle ant-alert-icon'></i> 已选择
        <a style='font-weight: 600'>{{ selectedRowKeys.length }}</a
        >项
        <a style='margin-left: 24px' @click='onClearSelected'>清空</a>
      </div>

      <a-table
        ref='table'
        size='middle'
        bordered
        rowKey='qyid'
        :columns='columns'
        :dataSource='dataSource'
        :pagination='ipagination'
        :loading='loading'
        :rowSelection='{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }'
        @change='handleTableChange'
      >
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 进出记录抽屉区域 -->
    <!-- <access-list-drawer :show-drawer="showDrawer" @close="onCloseDrawer"></access-list-drawer> -->
    <!-- 表单区域 -->
    <!-- <device-modal ref="deviceModalForm"></device-modal> -->
    <role-modal ref='modalForm' @ok='modalFormOk'></role-modal>
  </a-card>
</template>

<script>
import { getAction, postAction } from '@/api/manage'
// import EnvironmentModal from './modules/EnvironmentModal'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'
import JInput from '@comp/jeecg/JInput.vue'
import RoleModal from '@views/system/modules/RoleModal.vue'
// import AccessListDrawer from '../alarm/modules/AccessListDrawer'

export default {
  name: 'EnterpriseInfo',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    RoleModal,
    JInput,
    JEllipsis,
    JDictSelectTag
    // AccessListDrawer,
    // DeviceModal,
    // EnvironmentModal,
  },
  created() {
  },
  mounted() {
  },
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
  filters: {},
  data() {
    return {
      jylbOptions: [],
      cxztOptions: [],
      description: '企业信息列表页面',
      // 表头
      columns: [
        {
          title: '法人',
          dataIndex: 'fddbrxm',
          align: 'center'
        },
        {
          title: '企业名称',
          align: 'center',
          dataIndex: 'qymc'
        },
        {
          title: '企业地址',
          align: 'center',
          dataIndex: 'jydz'
        },
        {
          title: '经营类别',
          align: 'center',
          dataIndex: 'sshySc'
        },
        {
          title: '存续状态',
          align: 'center',
          dataIndex: 'qyztSc'
        }
      ],
      url: {
        list: '/enterprise/list',
        // delete: "/message/sysMessage/delete",
        deleteBatch: '/enterprise/deleteBatch',
        exportXlsUrl: 'enterprise/exportXls',
        importExcelUrl: 'enterprise/importExcel',
        jylb: '/enterprise/jylb',
        cxzt: '/enterprise/cxzt',
      },
      queryParam: {},
      /* 排序参数 */
      isorter: {
        column: '',
        order: 'desc'
      },
      showDrawer: false
    }
  },
  methods: {

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
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  beforeMount() {
    getAction(this.url.jylb, '').then((res) => {
      if (res.success) {
        this.jylbOptions = res.result
      }
    });
    getAction(this.url.cxzt, '').then((res) => {
      if (res.success) {
        this.cxztOptions = res.result
      }
    });
  }
}
</script>
<style lang='less' scoped>
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
