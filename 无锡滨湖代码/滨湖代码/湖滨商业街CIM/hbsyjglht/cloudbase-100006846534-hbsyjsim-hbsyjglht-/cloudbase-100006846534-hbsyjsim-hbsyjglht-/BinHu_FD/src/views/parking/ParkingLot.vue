<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="4" :sm="8">
            <a-form-item label="停车场编号">
              <a-input placeholder="请输入停车场编号" v-model="queryParam.parknumber"></a-input>
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
<!--      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">-->
<!--        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择-->
<!--        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a-->
<!--        >项-->
<!--        <a style="margin-left: 24px" @click="onClearSelected">清空</a>-->
<!--      </div>-->

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="sjhm"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        @change="handleTableChange"
      >
      </a-table>
    </div>
    <!-- table区域-end -->
    <role-modal ref="modalForm" @ok="modalFormOk"></role-modal>
  </a-card>
</template>

<script>

import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import { WebsocketMixin } from '@/mixins/WebsocketMixin'
import JDictSelectTag from '@/components/dict/JDictSelectTag'
import JEllipsis from '@/components/jeecg/JEllipsis'
import JInput from '@comp/jeecg/JInput.vue'
import RoleModal from '@views/system/modules/RoleModal.vue'


export default {
  name: 'ParkingLot',
  mixins: [JeecgListMixin, WebsocketMixin],
  components: {
    RoleModal,
    JInput,
    JEllipsis,
    JDictSelectTag,
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
      ageRange: [0, 100], // 默认年龄段范围
      description: '停车场页面',
      // 表头
      columns: [
        {
          title: '停车场编号',
          dataIndex: 'parknumber',
          align: 'center',
          customRender: (text, record, index) => {
            // 如果纵向的text相同，则合并为一个单元格
            // 计算合并行数的逻辑
            let rowSpan = 1; // 默认行数为1

            // 判断当前行是否与上一行的 parkname 相同
            if (index > 0 && text === this.dataSource[index - 1].parknumber) {
              // 如果相同，需要合并单元格，将当前行的 parkname 设置为空字符串
              rowSpan = 0;
            } else {
              // 如果不同，需要找到相同值的连续行数，以确定合并的行数
              for (let i = index + 1; i < this.dataSource.length; i++) {
                if (text === this.dataSource[i].parknumber) {
                  rowSpan++;
                } else {
                  break; // 遇到不同值就停止循环
                }
              }
            }

            return {
              children: text,
              attrs: {
                rowSpan: rowSpan, // 设置合并的行数
              },
            };
          }
        },
        {
          title: '停车场名称',
          dataIndex: 'parkname',
          align: 'center',
          customRender: (text, record, index) => {
            // 如果纵向的text相同，则合并为一个单元格
            // 计算合并行数的逻辑
            let rowSpan = 1; // 默认行数为1

            // 判断当前行是否与上一行的 parkname 相同
            if (index > 0 && text === this.dataSource[index - 1].parkname) {
              // 如果相同，需要合并单元格，将当前行的 parkname 设置为空字符串
              rowSpan = 0;
            } else {
              // 如果不同，需要找到相同值的连续行数，以确定合并的行数
              for (let i = index + 1; i < this.dataSource.length; i++) {
                if (text === this.dataSource[i].parkname) {
                  rowSpan++;
                } else {
                  break; // 遇到不同值就停止循环
                }
              }
            }

            return {
              children: text,
              attrs: {
                rowSpan: rowSpan, // 设置合并的行数
              },
            };
          }
        },
        {
          title: '区域名称',
          dataIndex: 'areaname',
          align: 'center'
        },
        {
          title: '场库总数',
          align: 'center',
          dataIndex: 'spacecount',
          customRender: (text, record, index) => {
            // 如果纵向的text相同，则合并为一个单元格
            // 计算合并行数的逻辑
            let rowSpan = 1; // 默认行数为1

            // 判断当前行是否与上一行的 parkname 相同
            if (index > 0 && text === this.dataSource[index - 1].spacecount) {
              // 如果相同，需要合并单元格，将当前行的 parkname 设置为空字符串
              rowSpan = 0;
            } else {
              // 如果不同，需要找到相同值的连续行数，以确定合并的行数
              for (let i = index + 1; i < this.dataSource.length; i++) {
                if (text === this.dataSource[i].spacecount) {
                  rowSpan++;
                } else {
                  break; // 遇到不同值就停止循环
                }
              }
            }

            return {
              children: text,
              attrs: {
                rowSpan: rowSpan, // 设置合并的行数
              },
            };
          }
        },
        {
          title: '场库使用数',
          align: 'center',
          dataIndex: 'usedcount',
          customRender: (text, record, index) => {
            // 如果纵向的text相同，则合并为一个单元格
            // 计算合并行数的逻辑
            let rowSpan = 1; // 默认行数为1

            // 判断当前行是否与上一行的 usedcount 相同
            if (index > 0 && text === this.dataSource[index - 1].usedcount) {
              // 如果相同，需要合并单元格，将当前行的 usedcount 设置为空字符串
              rowSpan = 0;
            } else {
              // 如果不同，需要找到相同值的连续行数，以确定合并的行数
              for (let i = index + 1; i < this.dataSource.length; i++) {
                if (text === this.dataSource[i].usedcount && this.dataSource[i].parknumber === this.dataSource[i - 1].parknumber) {
                  rowSpan++;
                } else {
                  break; // 遇到不同值就停止循环
                }
              }
            }

            return {
              children: text,
              attrs: {
                rowSpan: rowSpan, // 设置合并的行数
              },
            };
          }
        },
        {
          title: '场库空位数',
          align: 'center',
          dataIndex: 'freespacecount',
          customRender: (text, record, index) => {
            // 如果纵向的text相同，则合并为一个单元格
            // 计算合并行数的逻辑
            let rowSpan = 1; // 默认行数为1

            // 判断当前行是否与上一行的 parkname 相同
            if (index > 0 && text === this.dataSource[index - 1].freespacecount) {
              // 如果相同，需要合并单元格，将当前行的 parkname 设置为空字符串
              rowSpan = 0;
            } else {
              // 如果不同，需要找到相同值的连续行数，以确定合并的行数
              for (let i = index + 1; i < this.dataSource.length; i++) {
                if (text === this.dataSource[i].freespacecount
                  && this.dataSource[i].parknumber === this.dataSource[i - 1].parknumber) { //
                  rowSpan++;
                } else {
                  break; // 遇到不同值就停止循环
                }
              }
            }

            return {
              children: text,
              attrs: {
                rowSpan: rowSpan, // 设置合并的行数
              },
            };
          }
        },
        {
          title: '区域总数',
          align: 'center',
          dataIndex: 'allspcace'
        },
        {
          title: '区域使用数',
          align: 'center',
          dataIndex: 'areausedcount'
        },
        {
          title: '区域空位数',
          align: 'center',
          dataIndex: 'lastspacecount'
        },
        {
          title: '最新时间',
          align: 'center',
          dataIndex: 'syncDate'
        }
      ],
      url: {
        list: '/parking/list',
        // delete: "/message/sysMessage/delete",
        deleteBatch: '/party/deleteBatch',
        exportXlsUrl: "party/exportXls",
        importExcelUrl: "party/importExcel",
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
      this.dataSource.map((item) => {
        if (item.room === message.room) {
          item.status = message.val === '开启'
          return item
        }
      })
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
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
