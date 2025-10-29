<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="模型名称">
              <a-input placeholder="请输入搜索关键词" v-model="queryParam.keyWord"></a-input>
            </a-form-item>
          </a-col>

          <!--<a-col :md="6" :sm="10">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-range-picker
                style="width: 210px"
                v-model="queryParam.createTimeRange"
                format="YYYY-MM-DD"
                :placeholder="['开始时间', '结束时间']"
                @change="onDateChange"
                @ok="onDateOk"
              />
            </a-form-item>
          </a-col>-->

          <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
            <a-col :md="6" :sm="12">
              <a-button type="primary" style="left: 10px" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px; left: 10px">重置
              </a-button>
            </a-col>
          </span>
          <span style="float: right; overflow: hidden" class="table-page-search-submitButtons">
            <a-col :md="12" :sm="12">
              <a-button type="primary" style="left: 10px; margin-right: 10px" @click="createProcess" icon="plus">创建流程模型
              </a-button>
            </a-col>
          </span>
        </a-row>
      </a-form>
    </div>

    <!-- table区域-begin -->
    <a-table bordered ref="table" size="middle" rowKey="id" :columns="columns" :dataSource="dataSource"
      :pagination="ipagination" :loading="loading" @change="handleTableChange">
      <span slot="modelId" slot-scope="text, record">
        <j-ellipsis :value="text" :length="13" />
      </span>
      <span slot="revision" slot-scope="text, record"> v.{{ text }} </span>
      <span slot="metaInfo" slot-scope="text, record">
        <j-ellipsis :value="JSON.parse(text).description" :length="15" />
      </span>
      <!-- 字符串超长截取省略号显示-->
      <span slot="logContent" slot-scope="text, record">
        <j-ellipsis :value="text" :length="10" />
      </span>
      <span slot="make" slot-scope="text, record">
        <a href="javascript:void(0);" @click="updateProcess(record.id)">流程设计</a>
        <a-divider type="vertical" />
        <a href="javascript:void(0);" @click="deployment(record)">发布</a>
        <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">更多
            <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a-popconfirm title="是否确认删除?" @confirm="deletelc(1, record)" @cancel="deletelc(0)" okText="Yes"
                cancelText="No">
                <a href="javascript:void(0);" style="color: red">删除</a>
              </a-popconfirm>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:void(0);" @click="defList(record.key)">版本监控</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </a-table>
    <!-- table区域-end -->

    <progress-designer-modal ref="pdModal"></progress-designer-modal>

    <progress-definition-list-modal ref="dflModal"></progress-definition-list-modal>
  </a-card>
</template>

<script>
import { filterObj } from '@/utils/util'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import JEllipsis from '@/components/jeecg/JEllipsis'
import { deleteAction, getAction, putAction, downFile } from '@/api/manage'

import ProgressDesignerModal from '@/views/bpmn/ProgressDesignerModal'
import ProgressDefinitionListModal from './modules/ProgressDefinitionListModal'

export default {
  name: 'ModelList',
  mixins: [JeecgListMixin],
  components: {
    JEllipsis,
    ProgressDesignerModal,
    ProgressDefinitionListModal
  },
  data() {
    return {
      /*流程设计器连接*/
      iframUrl: '',
      /*设计流程框参数*/

      description: '这是流程模型列表页面',
      // 查询条件
      queryParam: {
        createTimeRange: [],
        keyWord: '',
      },
      // 表头
      columns: [
        // {
        //   title: '#',
        //   width: 50,
        //   dataIndex: '',
        //   key: 'rowIndex',
        //   align: 'center',
        //   customRender: function (t, r, index) {
        //     return parseInt(index) + 1
        //   },
        // },
        {
          title: '流程名称',
          width: 100,
          align: 'center',
          dataIndex: 'name',
          scopedSlots: { customRender: 'logContent' },
        },
        // {
        //   title: '模型ID',
        //   width: 120,
        //   align: 'center',
        //   dataIndex: 'id',
        //   scopedSlots: { customRender: 'modelId' },
        // },

        {
          title: '流程编码',
          width: 80,
          dataIndex: 'key',
          align: 'center',
        },
        {
          title: '版本',
          width: 40,
          dataIndex: 'revision',
          align: 'center',
          scopedSlots: { customRender: 'revision' },
        },
        // {
        //   title: '备注描述',
        //   width: 120,
        //   dataIndex: 'metaInfo',
        //   align: 'center',
        //   scopedSlots: { customRender: 'metaInfo' },
        // },
        {
          title: '创建时间',
          width: 100,
          dataIndex: 'createTime',
          align: 'center',
          // sorter:true
        },
        {
          title: '最后更新时间',
          width: 100,
          dataIndex: 'lastUpdateTime',
          align: 'center',
        },
        {
          title: '操作',
          width: 120,
          dataIndex: '',
          scopedSlots: { customRender: 'make' },
          align: 'center',
        },
      ],
      labelCol: {
        xs: { span: 1 },
        sm: { span: 2 },
      },
      wrapperCol: {
        xs: { span: 10 },
        sm: { span: 16 },
      },
      url: {
        list: '/processModel/list',
        delete: '/processModel/delete/',
        deployment: '/processModel/deployment',
        create: '/activiti/models/create',
        update: '/activiti/modeler.html?modelId=',
      },
    }
  },
  computed: {
    scroll: function () {
      var width = window.innerWidth
      //ant-table
      let $antTable = window.document.getElementsByClassName('ant-table')
      if ($antTable[0]) {
        width = $antTable[0].clientWidth
      }
      console.log('$antTable', $antTable)
      return {
        // x:'max-content',
        x: width,
        y: window.innerHeight / 2,
      }
    },
    innerHeight: function () {
      var innerHeight = window.innerHeight
      return innerHeight
    },
  },
  methods: {
    createProcess() {
      this.$refs.pdModal.add()
    },
    /*修改流程*/
    updateProcess(id) {
      this.$nextTick(() => {
        this.$refs.pdModal.edit(id)
      })
    },
    /*修改流程*/
    defList(key) {
      this.$nextTick(() => {
        this.$refs.dflModal.show(key)
      })
    },
    /*发布流程*/
    deployment(row) {
      var _this = this
      var id = row.id
      var name = row.name
      this.$confirm({
        title: '确认部署流程?',
        content: `确认部署流程：${name}`,
        onOk() {
          putAction(_this.url.deployment, { id }).then((res) => {
            if (res.success) {
              _this.$message.success(res.message)
            } else {
              _this.$message.error(res.message)
            }
            this.loadData()
          })
        },
        onCancel() { },
      })
    },
    /*删除模型*/
    deletelc(y, row) {
      console.log(y, row)
      if (y) {
        deleteAction(this.url.delete, { modelId: row.id }).then((res) => {
          if (res.success) {
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
          this.loadData()
        })
      }
    },
    handleTableChange(pagination, filters, sorter) {
      //分页、排序、筛选变化时触发
      //TODO 筛选
      if (Object.keys(sorter).length > 0) {
        this.isorter.column = sorter.field
        this.isorter.order = 'ascend' == sorter.order ? 'asc' : 'desc'
      }
      this.ipagination = pagination
      // this.loadData();
    },
    loadData(arg) {
      if (!this.url.list) {
        this.$message.error('请设置url.list属性!')
        return
      }
      //加载数据 若传入参数1则加载第一页的内容
      if (arg === 1) {
        this.ipagination.current = 1
      }
      var params = this.getQueryParams() //查询条件
      this.loading = true
      getAction(this.url.list, params).then((res) => {
        if (res.success) {
          let records = res.result || []
          this.dataSource = records
          this.ipagination.total = records.length
        }
        if (res.code === 510) {
          this.$message.warning(res.message)
        }
        this.loading = false
      })
    },
    getQueryParams() {
      var param = Object.assign({}, this.queryParam, this.isorter)
      delete param.createTimeRange // 时间参数不传递后台
      return filterObj(param)
    },

    // 重置
    searchReset() {
      var that = this
      var logType = that.queryParam.logType
      that.queryParam = {} //清空查询区域参数
      that.queryParam.logType = logType
      that.loadData(this.ipagination.current)
    },
    onDateChange: function (value, dateString) {
      console.log(dateString[0], dateString[1])
      this.queryParam.createTime_begin = dateString[0]
      this.queryParam.createTime_end = dateString[1]
    },
    onDateOk(value) {
      console.log(value)
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>