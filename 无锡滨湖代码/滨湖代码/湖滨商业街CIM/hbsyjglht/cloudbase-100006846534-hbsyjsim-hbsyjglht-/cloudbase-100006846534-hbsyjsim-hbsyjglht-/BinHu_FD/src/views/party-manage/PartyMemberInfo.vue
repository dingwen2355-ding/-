<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="4" :sm="8">
            <a-form-item label="姓名">
              <j-input placeholder="请输入姓名" v-model="queryParam.xm"></j-input>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="地址">
              <j-input placeholder="请输入模糊地址" v-model="queryParam.dz" :type="JINPUT_QUERY_LIKE"></j-input>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="性别">
              <a-select v-model="queryParam.xb" placeholder="请选择">
                <a-select-option value="">请选择</a-select-option>
                <a-select-option value="男">男</a-select-option>
                <a-select-option value="女">女</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="党组织名称">
              <j-input placeholder="请输入党组织名称" v-model="queryParam.dzzmc" :type="JINPUT_QUERY_LIKE"></j-input>
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
      <a-button @click="handleAdd" type="primary" icon="plus">添加用户</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('党员信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader"
                :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
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
        :scroll="{x:calcTableWidth,y:calcTableHeight}"
        ref="table"
        size="middle"
        bordered
        rowKey="sjhm"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        @change="handleTableChange"
      >
                <span slot="action" slot-scope="text, record">
          <a-button type="primary" size="small" @click="handleEdit(record)">编辑</a-button>
                    <a-divider type="vertical" />
                  <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.sjhm)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 进出记录抽屉区域 -->
    <!-- <access-list-drawer :show-drawer="showDrawer" @close="onCloseDrawer"></access-list-drawer> -->
    <!-- 表单区域 -->
    <party-member-modal ref="modalForm" @ok="modalFormOk"></party-member-modal>
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'
import JInput from '@comp/jeecg/JInput.vue'
import RoleModal from '@views/system/modules/RoleModal.vue'
import PartyMemberModal from '@views/party-manage/modules/PartyMemberModal.vue'

export default {
  name: 'PartyMemberInfo',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    PartyMemberModal,
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
      ageRange: [0, 100], // 默认年龄段范围
      description: '党员列表页面',
      // 表头
      columns: [
        {
          title: '姓名',
          dataIndex: 'xm',
          align: 'center'
        },
        {
          title: '党组织名称',
          align: 'center',
          dataIndex: 'dzzmc'
        },
        {
          title: '性别',
          align: 'center',
          dataIndex: 'xb'
        },
        {
          title: '手机号码',
          align: 'center',
          dataIndex: 'sjhm'
        },
        {
          title: '地址',
          align: 'center',
          dataIndex: 'dz'
        },
        {
          title: '经度',
          align: 'center',
          dataIndex: 'lng'
        },
        {
          title: '纬度',
          align: 'center',
          dataIndex: 'lat'
        },
        {
          title: '出生时间',
          align: 'center',
          dataIndex: 'cssj'
        },
        {
          title: '入党时间',
          align: 'center',
          dataIndex: 'rdsj'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 150,
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: '/party/list',
        // delete: "/message/sysMessage/delete",
        deleteBatch: '/party/deleteBatch',
        delete: '/party/delete',
        exportXlsUrl: 'party/exportXls',
        importExcelUrl: 'party/importExcel'
      },
      queryParam: {},
      /* 排序参数 */
      isorter: {
        column: 'xm',
        order: 'desc'
      },
      showDrawer: false
    }
  },
  methods: {
    handleMenuClick(e) {
      if (e.key == 1) {
        this.batchDel()
      }
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
    }
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
    calcTableWidth() {
      return this.columns.reduce((total, item) => {
        return total + (item.width || 0)
      }, 0)
    },
    calcTableHeight() {
      return this.columns.reduce((total, item) => {
        return total + (item.height || 0)
      }, 0)
    }
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

//.ant-btn-danger {
//  background-color: #ffffff;
//}

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
