<template>
  <a-modal title="流程版本监控" :maskClosable="false" :footer="null" width="100%" height="100%" destroyOnClose centered
    v-bind="$attrs" :visible="visible" v-on="$listeners" @cancel="handleModalCancle">
    <a-card :bordered="false">
      <s-table ref="table" size="default" :columns="columns" :data="loadData">
        <span slot="action" slot-scope="text, record">
          <a href="javascript:;" @click="showProcessDefImage(record.id)">流程图</a>
          <a-divider type="vertical" />
          <a-popconfirm :title="`确认${record.suspensionState === 1 ? '挂起' : '激活'}吗?'`"
            @confirm="suspendOrActivateProcess(record.id, record.suspensionState)" okText="确定" cancelText="取消">
            <a href="javascript:;">
              <span v-if="record.suspensionState === 1">挂起</span>
              <span v-if="record.suspensionState === 2">激活</span>
            </a>
          </a-popconfirm>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">
              更多
              <a-icon type="down" />
            </a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a href="javascript:;">删除</a>
              </a-menu-item>
              <a-menu-item>
                <a href="javascript:;">下载</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </s-table>
    </a-card>

    <j-modal title="流程图" :width="modelStyle.width" :visible="processDefImageModalVisible" :bodyStyle="bodyStyle"
      :switchFullscreen="true" @cancel="handleImageModalCancel" :footer="null">
      <a-card :bordered="false">
        <img :src="processDefImageUrl" />
      </a-card>
    </j-modal>

  </a-modal>
</template>

<script>
import { ACCESS_TOKEN } from "@/store/mutation-types"
import Vue from 'vue'
import STable from '@/components/table'
import { postAction, getAction } from '@/api/manage'
export default {
  components: {
    STable,
  },
  data() {
    return {
      visible: false,
      processDefImageModalVisible: false,
      processDefId: '',
      modelStyle: {
        width: '80%',
        style: { top: '20px' },
        fullScreen: false
      },
      bodyStyle: {
        padding: "0",
        height: (window.innerHeight * 0.8) + "px",
        "overflow-y": "auto",

      },
      // 查询参数
      queryParam: {
        key: ''
      },
      // 表头
      columns: [
        {
          title: '名称',
          dataIndex: 'name'
        },
        {
          title: '流程KEY',
          dataIndex: 'key',
        },
        {
          title: '版本',
          dataIndex: 'version'
        },
        {
          title: '发布时间',
          dataIndex: 'deploymentTime'
        },
        {
          title: '状态',
          dataIndex: 'suspensionState',
          // sorter: true,
          customRender: function (text, record, index) {
            if (text === 1) {
              return '已激活'
            } else if (text === 2) {
              return '挂起'
            }
          }
        }, {
          title: '操作',
          width: '250px',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },
        }
      ],
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return this.$http.get('/processDefinition/list', {
          params: Object.assign(parameter, this.queryParam)
        }).then(res => {
          return res.result
        })
      },
    }
  },
  computed: {
    processDefImageUrl() {
      const token = Vue.ls.get(ACCESS_TOKEN);
      return `${window._CONFIG['domianURL']}/processDefinition/getProcessDefImg/${this.processDefId}?X-Access-Token=${token}`

    }
  },
  methods: {
    show(key) {
      this.visible = true
      this.queryParam.key = key
    },
    showProcessDefImage(id) {
      this.processDefId = id
      this.processDefImageModalVisible = true
    },
    handleModalCancle() {
      this.visible = false
    },
    handleImageModalCancel() {
      this.processDefImageModalVisible = false
    },
    suspendOrActivateProcess(id, suspendState) {
      postAction('/processDefinition/suspendOrActivate', { id: id, suspensionState: suspendState }).then(res => {
        if (res.success) {
          this.$message.success("操作成功")
          this.$refs.table.refresh()
        }
      })
    },

  },
  watch: {},
}
</script>
<style lang="less" scoped>
/deep/.ant-modal {
  padding: 0;

  .ant-modal-content {
    height: 100%;

    .ant-modal-body {
      padding: 0;
      height: calc(100% - 55px);
    }
  }
}
</style>
