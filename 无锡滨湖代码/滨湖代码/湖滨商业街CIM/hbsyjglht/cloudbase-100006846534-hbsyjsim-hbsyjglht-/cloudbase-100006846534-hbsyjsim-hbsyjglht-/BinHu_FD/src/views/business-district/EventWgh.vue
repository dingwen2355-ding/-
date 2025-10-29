<template>
  <a-card :bordered='false'>
    <!-- 查询区域 -->
    <div class='table-page-search-wrapper'>
      <a-form layout='inline' @keyup.enter.native='searchQuery'>
        <a-row :gutter='12'>
          <a-col :md="4" :sm="8">
            <a-form-item label='标题'>
              <a-input placeholder='请输入标题' v-model='queryParam.gzrwbt'></a-input>
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
<!--      <a-button type='primary' icon='download' @click="handleExportXls('企业信息')">导出</a-button>-->
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"-->
      <!--                @change="handleImportExcel">-->
      <!--        <a-button type="primary" icon="import">导入</a-button>-->
      <!--      </a-upload>-->
<!--      <a-dropdown v-if='selectedRowKeys.length > 0'>-->
<!--        <a-menu slot='overlay' @click='handleMenuClick'>-->
<!--          <a-menu-item key='1'>-->
<!--            <a-icon type='delete' @click='batchDel' />-->
<!--            删除-->
<!--          </a-menu-item>-->
<!--        </a-menu>-->
<!--        <a-button style='margin-left: 8px'>-->
<!--          批量操作-->
<!--          <a-icon type='down' />-->
<!--        </a-button>-->
<!--      </a-dropdown>-->
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
        rowKey='id'
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

    <role-modal ref='modalForm' @ok='modalFormOk'></role-modal>
  </a-card>
</template>

<script>
import { getAction, postAction } from '@/api/manage'

import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'
import JInput from '@comp/jeecg/JInput.vue'
import RoleModal from '@views/system/modules/RoleModal.vue'


export default {
  name: 'EventWgh',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    RoleModal,
    JInput,
    JEllipsis,
    JDictSelectTag
  },
  created() {
  },
  mounted() {
  },
  watch: {
  },
  filters: {},
  data() {
    return {
      description: '网格化事件列表页面',
      // 表头
      columns: [
        {
          title: 'id',
          dataIndex: 'id',
          align: 'center'
        },
        {
          title: '工作任务标题',
          align: 'center',
          dataIndex: 'gzrwbt'
        },
        {
          title: '工作任务详细情况',
          dataIndex: 'gzrwxxqk',
          align: 'center',
        },
        {
          title: '开始日期',
          dataIndex: 'gzrwksrq',
          align: 'center',
        },
        {
          title: '结束日期',
          align: 'center',
          dataIndex: 'gzrwjsrq'
        },
        {
          title: '采集人姓名',
          align: 'center',
          dataIndex: 'cjrxm'
        }
      ],
      url: {
        list: '/EventWgh/list',
        // delete: "/message/sysMessage/delete",
      },
      queryParam: {},
      /* 排序参数 */
      isorter: {
        column: 'id',
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
    },
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
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
