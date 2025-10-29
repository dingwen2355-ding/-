<template>
  <div>
    <a-card :bordered="false">
      <!-- <div class="meta-card-info">
        <a-icon type="warning" style="margin-bottom: 2px" />
        <div style="margin-left: 10px">今日预警：200次</div>
        <div style="margin-left: 20px">累计预警：3200次</div>
      </div> -->
      <!-- 查询区域 -->
      <div class="table-page-search-wrapper">
        <a-form layout="inline" @keyup.enter.native="searchQuery">
          <a-row :gutter="24">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="企业名称">
                <a-input placeholder="请输入企业名称" v-model="queryParam.companyName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="统一社会信用代码">
                <a-input placeholder="请输入统一社会信用代码" v-model="queryParam.code"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="纳税识别号">
                <a-input placeholder="请输入纳税识别号" v-model="queryParam.taxCode"></a-input>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="企业类型">
                <a-select  showSearch v-model="queryParam.type" placeholder="请选择企业类型">
                  <a-select-option value="工商个体户">工商个体户</a-select-option>
                  <a-select-option value="工商企业">工商企业</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="所属行业">
                <a-select showSearch v-model="queryParam.industry" placeholder="请选择所属行业">
                  <a-select-option value="休闲娱乐">休闲娱乐</a-select-option>
                  <a-select-option value="其他类型">其他类型</a-select-option>
                  <a-select-option value="科技服务">科技服务</a-select-option>
                  <a-select-option value="零售批发">零售批发</a-select-option>
                  <a-select-option value="餐饮住宿">餐饮住宿</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="企业状态">
                <a-select showSearch v-model="queryParam.status" placeholder="请选择企业状态">
                  <a-select-option value="吊销，已注销">吊销，已注销</a-select-option>
                  <a-select-option value="吊销，未注销">吊销，未注销</a-select-option>
                  <a-select-option value="存续(在营、开业、在册)">存续(在营、开业、在册)</a-select-option>
                  <a-select-option value="撤销">撤销</a-select-option>
                  <a-select-option value="注销">注销</a-select-option>
                  <a-select-option value="迁出">迁出</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <!-- <a-col :md="4" :sm="8">
              <a-form-item label="工单类型">
                <j-dict-select-tag placeholder="请选择" v-model="queryParam.orgCode" />
              </a-form-item>
            </a-col>
            <a-col :md="4" :sm="8">
              <a-form-item label="预警来源">
                <j-dict-select-tag placeholder="请选择" v-model="queryParam.orgCode" />
              </a-form-item>
            </a-col> -->

            <a-col :md="4" :sm="8">
              <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
                <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
                <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
                <a-button @click="handleAdd" type="primary" icon="plus" style="margin-left: 8px">添加</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <!-- 操作按钮区域 -->
      <!-- <div class="table-operator" style="margin-top: 5px">
        <a-button type="primary" icon="download" @click="handleExportXls('工单信息')">导出</a-button>

        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /> </a-button>
        </a-dropdown>
      </div> -->

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
            <a-tag :color="record.status === 1 ? 'red' : 'grey'">{{ record.status | status }}</a-tag>
          </span>
          <span slot="action" slot-scope="text, record">
              <a @click="handleEdit(record)">编辑</a>
            <a-divider type="vertical"/>
             <a @click="editShareholder(record)"> 股东</a>
             <a-divider type="vertical"/>
             <a @click="editTax(record)"> 税务</a>
             <a-divider type="vertical"/>
             <a @click="editAcc(record)"> 公积金</a>
             <a-divider type="vertical"/>
             <a @click="editSoc(record)"> 社保</a>
             <a-divider type="vertical"/>
             <a @click="editElec(record)"> 用电</a>
             <a-divider type="vertical"/>
             <a @click="editWater(record)"> 用水</a>
             <a-divider type="vertical"/>
             <a @click="editYearReport(record)"> 年报</a>
             <a-divider type="vertical"/>
          <a-popconfirm title="确定删除吗?" @confirm="() =>handleDelete(record.id)">
            <a>删除</a>
          </a-popconfirm>
          </span>
        </a-table>
      </div>
      <!-- table区域-end -->

      <company-modal ref="modalForm" @ok="modalFormOk"></company-modal>
      <shareholder-list ref="shareholderList"></shareholder-list>
      <tax-list ref="taxList"></tax-list>
      <social-insurance-list ref="socialinsurancelist"></social-insurance-list>
      <accumulation-fund-list ref="accumulationfundlist"></accumulation-fund-list>
      <water-elec-list ref="watereleclist"></water-elec-list>
      <report-list ref="reportlist"></report-list>
    </a-card>
  </div>
</template>

<script>
  import CompanyModal from './modules/CompanyModal'
  import ShareholderList from './shareholderlist'
  import TaxList from './taxlist'
  import SocialInsuranceList from './socialinsurancelist'
  import AccumulationFundList from './accumulationfundlist'
  import ReportList from './reportlist'

  import WaterElecList from './watereleclist'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDictSelectTag from '@/components/dict/JDictSelectTag'
  import moment from 'moment'

  export default {
    name: 'companylist',
    mixins: [JeecgListMixin],
    components: {
      JDictSelectTag,
      CompanyModal,
      ShareholderList,
      TaxList,
      SocialInsuranceList,
      AccumulationFundList,
      WaterElecList,
      ReportList
    },

    filters: {
      status(val) {
        if (val === 1) {
          return '已处理'
        }
        if (val === 0) {
          return '待处理'
        }
      },
    },
    data() {
      return {
        description: '企业基本信息页面',
        // 表头
        columns: [
          {
            title: '企业名称',
            dataIndex: 'jgmc',
            // key: 'rowIndex',
            align: 'center',
          },
          {
            title: '统一社会信用代码',
            align: 'center',
            dataIndex: 'tyshxydm',
          },
          {
            title: '法人代表',
            align: 'center',
            dataIndex: 'fddbrxm',
          },
          {
            title: '企业类型',
            align: 'center',
            dataIndex: 'jglx',
          },
          {
            title: '所属行业',
            align: 'center',
            dataIndex: 'sshy',
          },
          {
            title: '企业状态',
            align: 'center',
            dataIndex: 'qyzt',
          },


          {
            title: '操作',
            dataIndex: 'action',
            align: 'center',
            scopedSlots: { customRender: 'action' },
          },
        ],
        // 模拟数据
        // data: [
        //   {
        //     id: '001',
        //     description: '烟感预警',
        //     location: '食堂',
        //     type: '消防报警',
        //     source: '消防物联网系统',
        //     createTime: '2022-08-25 12:00',
        //     status: '待处理',
        //   },
        //   {
        //     id: '002',
        //     description: '012出入防区窃警',
        //     location: '办公楼27层',
        //     type: '安防入侵告警',
        //     source: '安防报警系统',
        //     createTime: '2022-08-25 12:00',
        //     status: '待处理',
        //   },
        //   {
        //     id: '003',
        //     description: 'C195摄像头离线',
        //     location: '办公楼28层',
        //     type: '设备工况告警',
        //     source: '安防监控系统',
        //     createTime: '2022-08-25 12:00',
        //     status: '已处理',
        //   },
        // ],
        url: {
          list: '/company/baseinfo/getlist',
          delete: '/company/baseinfo/delete',
        },
      }
    },
    methods: {
      moment,
      handleProcess(record,type) {
        this.$refs.modalForm.edit(record)
        this.$refs.modalForm.type=type
        this.$refs.modalForm.title =type //'工单处理'
      },
      //编辑字典数据
      editShareholder(record) {
        this.$refs.shareholderList.edit(record);
      },
      editTax(record) {
        this.$refs.taxList.edit(record);
      },
      editAcc(record) {
        this.$refs.accumulationfundlist.edit(record);
      },
      editSoc(record) {
        this.$refs.socialinsurancelist.edit(record);
      },
      editElec(record) {
        record.type = 0;
        this.$refs.watereleclist.edit(record);
      },
      editWater(record) {
        record.type = 1;
        this.$refs.watereleclist.edit(record);
      },
      editYearReport(record) {
        this.$refs.reportlist.edit(record);
      }
      // onRangeDateChange(value) {
      //   console.log(value)
      //   console.log(this.queryParam)
      //   this.queryParam = value
      //     ? {
      //         ...this.queryParam,
      //         createTime_begin: value[0],
      //         createTime_end: value[1],
      //       }
      //     : {
      //         ...this.queryParam,
      //         createTime_begin: null,
      //         createTime_end: null,
      //       }
      // },
    },
  }
</script>
<style lang="less" scoped>
  @import '~@assets/less/common.less';

  // .meta-card-info {
  //   display: flex;
  //   align-items: center;
  //   margin-bottom: 20px;

  //   > div {
  //     display: flex;
  //     align-items: center;
  //     // font-size: 18px;
  //     font-weight: 600;
  //     margin-left: 20px;
  //   }
  // }
</style>
