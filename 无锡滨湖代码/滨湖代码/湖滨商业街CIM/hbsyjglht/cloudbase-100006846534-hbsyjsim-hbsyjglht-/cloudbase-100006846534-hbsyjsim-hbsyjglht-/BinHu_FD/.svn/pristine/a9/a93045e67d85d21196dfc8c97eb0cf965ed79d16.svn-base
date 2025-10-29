<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="4" :sm="8">
            <a-form-item label="姓名">
              <j-input placeholder="请输入模糊姓名" v-model="queryParam.name"></j-input>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="街道">
              <j-input placeholder="请输入模糊街道" v-model="queryParam.streetName"></j-input>
            </a-form-item>
          </a-col>
<!--          <a-col :md="4" :sm="8">-->
<!--            <a-form-item label="是否注销">-->
<!--              <a-select v-model="queryParam.cancelFlag" placeholder="请选择">-->
<!--                <a-select-option value="">请选择</a-select-option>-->
<!--                <a-select-option value="1">注销</a-select-option>-->
<!--                <a-select-option value="0">未注销</a-select-option>-->
<!--              </a-select>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
          <a-col :md="4" :sm="8">
            <a-form-item label="身份证">
              <a-input v-model="queryParam.idCard" placeholder="请输入" />
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="街道">
              <a-select v-model="queryParam.streetCode" placeholder="请选择">
                <a-select-option value="">请选择</a-select-option>
                <a-select-option v-for="(item, key) in streetOptions" :key="key" :value="item.value">
                <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                    {{ item.text || item.label }}
                 </span>
                </a-select-option>
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
        <a-row :gutter="24">
          <a-col :md="4" :sm="8">
            <a-form-item label="年龄">
              <!--              <a-slider range :default-value="[20, 50]"-->
              <!--                        @change="handleAgeRangeChange" />-->
              <j-dict-select-tag
                placeholder="请选择"
                dictCode="age_range"
                @input="handInput" v-model="queryParam.ageRange"></j-dict-select-tag>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="性别">
              <a-select v-model="queryParam.sex" placeholder="请选择">
                <a-select-option value="">请选择</a-select-option>
                <a-select-option value="男">男</a-select-option>
                <a-select-option value="女">女</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="人员标签">
              <j-multi-select-tag dict-code="PEOPLE_TAG" placeholder="请选择"
                                  v-model="queryParam.peopleTag1"></j-multi-select-tag>
            </a-form-item>
          </a-col>
          <a-col :md="4" :sm="8">
            <a-form-item label="社区">
              <a-select v-model="queryParam.communityCode" placeholder="请选择">
                <a-select-option value="">请选择</a-select-option>
                <a-select-option v-for="(item, key) in communityOptions" :key="key" :value="item.value">
                <span style="display: inline-block;width: 100%" :title=" item.text || item.label ">
                    {{ item.text || item.label }}
                 </span>
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </div>


    <div class="table-operator" style="border-top: 5px">
      <a-button @click="handleAdd" type="primary" icon="plus">添加人员</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('用户信息')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
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
          <a-button type="primary" size="small" @click="handleEdit(record)">编辑</a-button>
                    <a-divider type="vertical" />
                  <a-dropdown>
            <a class="ant-dropdown-link">
              更多 <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
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
    <!-- 表单区域 -->
    <people-modal ref="modalForm" @ok="modalFormOk"></people-modal>
  </a-card>
</template>

<script>
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'
import JInput from '@comp/jeecg/JInput.vue'
import PeopleModal from '@views/person-manage/modules/PeopleModal.vue'
import JMultiSelectTag from '@comp/dict/JMultiSelectTag.vue'
import { h } from '@vue/composition-api'
import { getAction } from '@api/manage'

export default {
  name: 'PersonInfo',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    JMultiSelectTag,
    PeopleModal,
    JInput,
    JEllipsis,
    JDictSelectTag
  },
  created() {
  },
  mounted() {
  },
  filters: {},
  data() {
    return {
      ageRange: [0, 100], // 默认年龄段范围
      streetOptions: [],
      communityOptions: [],
      description: '人员列表页面',
      // 表头
      columns: [
        {
          title: '序号',
          align: 'center',
          customRender: (text, record, index) => {
            return index + 1
          }
        },
        {
          title: '姓名',
          dataIndex: 'name',
          align: 'center'
        },
        {
          title: '身份证',
          align: 'center',
          dataIndex: 'idCard',
          // 身份证只显示开头三位和最后三位
          customRender: (text, record, index) => {
            return text.substring(0, 3) + '****' + text.substring(text.length - 3)
          }
        },
        {
          title: '性别',
          align: 'center',
          dataIndex: 'sex'
        },
        {
          title: '手机号',
          align: 'center',
          dataIndex: 'phone',
          // 手机号只显示开头三位和最后三位,如果为空则显示空字符串
          customRender: (text, record, index) => {
            if (text) {
              return text.substring(0, 3) + '****' + text.substring(text.length - 3)
            } else {
              return ''
            }
          }
        },
        {
          title: '居住地址',
          align: 'center',
          dataIndex: 'livePlaceAddress'
        },
        {
          title: '户籍详细地址',
          align: 'center',
          dataIndex: 'domicileAddress'
        },
        {
          title: '人员状态',
          align: 'center',
          dataIndex: 'peopleStatus'
        },
        {
          title: '人员标签',
          align: 'center',
          dataIndex: 'peopleTag_dictText',
          customRender: (text, record, index) => {
            if (text) {
              return h('div', text.split(',').map(item => {
                return h('a-tag', { props: { color: 'blue' } }, item)
              }))
            } else {
              return ''
            }
          }
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
        list: '/person/list',
        delete: '/person/delete',
        deleteBatch: '/person/deleteBatch',
        exportXlsUrl: 'person/exportXls',
        importExcelUrl: 'person/importExcel',
        street: 'person/getStreet',
        community: 'person/getCommunitySelect'
      },
      queryParam: {},
      /* 排序参数 */
      isorter: {
        column: 'name',
        order: 'desc'
      },
      showDrawer: false
    }
  },
  methods: {
    handleAgeRangeChange(val) {
      // 在这里处理年龄段变化，可以将其存储在组件的数据属性中或发送到后端进行筛选
      console.log('选择的年龄段：', val)
      const min = val[0]
      const max = val[1]
      this.ageRange = val
      this.queryParam.minAge = min
      this.queryParam.maxAge = max

    },
    handInput(val, options) {
      // 重置年龄段
      this.queryParam.minAge = ''
      this.queryParam.maxAge = ''
      if (options.key === undefined) {
        return
      }
      const range = options.componentOptions.children[0].data.attrs.title
      if (range === '90+') {
        this.queryParam.minAge = 90
        return
      }
      const ageRange = range.split('-')
      this.queryParam.minAge = ageRange[0]
      this.queryParam.maxAge = ageRange[1]
    },
    handleMenuClick(e) {
      if (e.key == 1) {
        this.batchDel()
      }
    }
    // handleDetail(record) {
    //   this.$refs.deviceModalForm.edit(record)
    //   this.$refs.deviceModalForm.title = '设备详情'
    // },
  },
  computed: {
    importExcelUrl: function() {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
    calcTableHeight() {
      return this.columns.reduce((total, item) => {
        return total + (item.height || 0)
      }, 0)
    }
  },
  beforeMount() {
    getAction(this.url.street, '').then((res) => {
      if (res.success) {
        this.streetOptions = res.result
      }
    })
    getAction(this.url.community, '').then((res) => {
      if (res.success) {
        this.communityOptions = res.result
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
