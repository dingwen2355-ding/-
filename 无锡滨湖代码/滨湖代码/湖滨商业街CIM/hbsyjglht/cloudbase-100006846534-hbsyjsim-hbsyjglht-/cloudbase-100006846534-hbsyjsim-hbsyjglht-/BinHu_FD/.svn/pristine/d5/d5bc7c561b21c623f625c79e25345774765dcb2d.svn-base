<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="12">
          <a-col :md="4" :sm="8">
            <a-form-item label="标题">
              <a-input placeholder="请输入标题" v-model="queryParam.gdTitle"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="12">
            <a-form-item label="事件类型">
              <a-cascader placeholder="请选择" :options="options" change-on-select @change="onChange" />
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


    <div class="table-operator" style="border-top: 5px">
      <!--      <a-button @click="handleAdd" type="primary" icon="plus">添加用户</a-button>-->
      <!--      <a-button type='primary' icon='download' @click="handleExportXls('企业信息')">导出</a-button>-->
      <!--      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"-->
      <!--                @change="handleImportExcel">-->
      <!--        <a-button type="primary" icon="import">导入</a-button>-->
      <!--      </a-upload>-->
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay" @click="handleMenuClick">
          <a-menu-item key="1">
            <a-icon type="delete" @click="batchDel" />
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px">
          批量操作
          <a-icon type="down" />
        </a-button>
      </a-dropdown>
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
        rowKey="qyid"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        @change="handleTableChange"
      >
        <span slot="action" slot-scope="text, record">
            <a-button type="primary" @click="handleDetail(record)">详情</a-button>
        </span>
        <span slot="status" slot-scope="text, record">
            {{ record.zbdw != null ? '已处理' : '未处理' }}
        </span>
        <span slot="type" slot-scope="text, record">
            {{ record.level3 }}
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <detail-modal ref="modalForm" @ok="modalFormOk"></detail-modal>
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
import DetailModal from '@views/business-district/modules/DetailModal.vue'

export default {
  name: 'Event12345',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    DetailModal,
    RoleModal,
    JInput,
    JEllipsis,
    JDictSelectTag
  },
  created() {
  },
  mounted() {
  },
  watch: {},
  filters: {},
  data() {
    return {
      options: [],
      sjlxOptions: [],
      cxztOptions: [],
      description: '12345事件列表页面',
      // 表头
      columns: [
        {
          title: 'id',
          dataIndex: 'id',
          align: 'center'
        },
        {
          title: '标题',
          align: 'center',
          dataIndex: 'gdTitle'
        },
        {
          title: '事件类型',
          dataIndex: 'type',
          align: 'center',
          scopedSlots: { customRender: 'type' }
        },
        {
          title: '完成状态',
          dataIndex: 'status',
          align: 'center',
          scopedSlots: { customRender: 'status' }
        },
        {
          title: '创建时间',
          align: 'center',
          dataIndex: 'xdsj'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/Event12345/list',
        // delete: "/message/sysMessage/delete",
        deleteBatch: '/enterprise/deleteBatch',
        exportXlsUrl: 'enterprise/exportXls',
        importExcelUrl: 'enterprise/importExcel',
        sjlx: '/Event12345/type'
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
    onChange(value, selectedOptions) {
      this.queryParam.level_1 = value[0]
      this.queryParam.level_2 = value[1]
      this.queryParam.level_3 = value[2]
      console.log(this.queryParam)
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    }
  },
  beforeMount() {
    getAction(this.url.sjlx, '').then((res) => {
      if (res.success) {
        this.options = res.result.options
      }
    })
  }
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
